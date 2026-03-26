package com.example.chaspjava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host:}")
    private String host;

    @Value("${spring.mail.port:0}")
    private int port;

    @Value("${spring.mail.username:}")
    private String username;

    @Value("${spring.mail.password:}")
    private String password;

    @Value("${spring.mail.properties.mail.smtp.auth:false}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable:false}")
    private boolean startTls;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        if (host != null && !host.isBlank()) {
            sender.setHost(host);
        }
        if (port > 0) {
            sender.setPort(port);
        }
        if (username != null && !username.isBlank()) {
            sender.setUsername(username);
        }
        if (password != null && !password.isBlank()) {
            sender.setPassword(password);
        }

        Properties props = new Properties();
        props.put("mail.smtp.auth", Boolean.toString(smtpAuth));
        props.put("mail.smtp.starttls.enable", Boolean.toString(startTls));
        sender.setJavaMailProperties(props);

        return sender;
    }
}

/**
 * Minimal local stubs to avoid a hard dependency on Spring Mail.
 * These are intentionally lightweight and only implement the setters used above.
 * Replace with the real Spring classes by adding the proper dependency when available.
 */
interface JavaMailSender {
    // marker interface for compatibility with existing code
}

class JavaMailSenderImpl implements JavaMailSender {
    private String host;
    private int port;
    private String username;
    private String password;
    private Properties javaMailProperties = new Properties();

    public JavaMailSenderImpl() {
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setJavaMailProperties(Properties props) {
        if (props != null) {
            this.javaMailProperties = props;
        }
    }

    // Optional: getters if needed by other code
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Properties getJavaMailProperties() {
        return javaMailProperties;
    }
}
