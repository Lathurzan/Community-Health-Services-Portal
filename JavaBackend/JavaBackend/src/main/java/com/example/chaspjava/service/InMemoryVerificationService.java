package com.example.chaspjava.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryVerificationService implements VerificationService {

    private static class Entry {
        final String code;
        final Instant expiresAt;

        Entry(String code, Instant expiresAt) {
            this.code = code;
            this.expiresAt = expiresAt;
        }
    }

    private final Map<String, Entry> store = new ConcurrentHashMap<>();
    private final Random rnd = new Random();

    @Override
    public String generateAndStore(String email) {
        int n = 100000 + rnd.nextInt(900000);
        String code = Integer.toString(n);
        store.put(email.toLowerCase(), new Entry(code, Instant.now().plusSeconds(5 * 60)));
        return code;
    }

    @Override
    public boolean verify(String email, String code) {
        Entry e = store.get(email.toLowerCase());
        if (e == null) return false;
        if (Instant.now().isAfter(e.expiresAt)) {
            store.remove(email.toLowerCase());
            return false;
        }
        boolean ok = e.code.equals(code);
        if (ok) store.remove(email.toLowerCase());
        return ok;
    }

    // cleanup task every minute
    @Scheduled(fixedRate = 60_000L)
    public void cleanup() {
        Instant now = Instant.now();
        store.entrySet().removeIf(e -> now.isAfter(e.getValue().expiresAt));
    }
}
