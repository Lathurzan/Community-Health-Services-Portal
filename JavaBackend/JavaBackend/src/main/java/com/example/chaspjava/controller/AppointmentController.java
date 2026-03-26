package com.example.chaspjava.controller;

import com.example.chaspjava.dto.AppointmentDTO;
import com.example.chaspjava.entity.Appointment;
import com.example.chaspjava.service.AppointmentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    // Service layer handling appointment business logic
    private final AppointmentService service;

    // Constructor-based dependency injection of the service layer
    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    // Create a new appointment using request payload data
    @PostMapping("/create")
    public ResponseEntity<Appointment> create(@RequestBody AppointmentDTO dto) {
        try {
            Appointment created = service.create(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException ex) {
            // Handle invalid input or missing fields
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (DataIntegrityViolationException ex) {
            // Handle database constraint violations (e.g., duplicate entries)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Database constraint violated: " + ex.getMessage(), ex);
        } catch (RuntimeException ex) {
            // Handle unexpected runtime exceptions
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error creating appointment: " + ex.getMessage(), ex);
        }
    }

    // Retrieve a single appointment by its unique ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getById(@PathVariable String id) {
        try {
            Appointment appointment = service.getById(id);
            return ResponseEntity.ok(appointment);
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found with ID: " + id, ex);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving appointment: " + ex.getMessage(), ex);
        }
    }

    // Retrieve all appointments for a specific patient
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<Appointment>> getByPatient(@PathVariable String patientId) {
        try {
            List<Appointment> appointments = service.getByPatient(patientId);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching patient appointments: " + ex.getMessage(), ex);
        }
    }

    // Retrieve all appointments for a specific service provider
    @GetMapping("/provider/{providerId}")
    public ResponseEntity<List<Appointment>> getByProvider(@PathVariable String providerId) {
        try {
            List<Appointment> appointments = service.getByProvider(providerId);
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error fetching provider appointments: " + ex.getMessage(), ex);
        }
    }

    // Retrieve all appointments in the system
    @GetMapping
    public ResponseEntity<List<Appointment>> getAll() {
        try {
            List<Appointment> appointments = service.getAll();
            return ResponseEntity.ok(appointments);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving appointments: " + ex.getMessage(), ex);
        }
    }

    // Return the total number of appointments
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        try {
            long total = service.count();
            return ResponseEntity.ok(total);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error counting appointments: " + ex.getMessage(), ex);
        }
    }

    // Retrieve all appointments scheduled for today
    @GetMapping("/today")
    public ResponseEntity<List<Appointment>> getToday() {
        try {
            List<Appointment> todayAppointments = service.getTodayAppointments();
            return ResponseEntity.ok(todayAppointments);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving today's appointments: " + ex.getMessage(), ex);
        }
    }

    // Retrieve all appointments scheduled for tomorrow
    @GetMapping("/tomorrow")
    public ResponseEntity<List<Appointment>> getTomorrow() {
        try {
            List<Appointment> tomorrowAppointments = service.getTomorrowAppointments();
            return ResponseEntity.ok(tomorrowAppointments);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving tomorrow's appointments: " + ex.getMessage(), ex);
        }
    }

    // Retrieve upcoming appointments within a configurable number of days
    @GetMapping("/upcoming")
    public ResponseEntity<List<Appointment>> getUpcoming(@RequestParam(name = "days", defaultValue = "7") int days) {
        try {
            List<Appointment> upcoming = service.getUpcomingAppointments(days);
            return ResponseEntity.ok(upcoming);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid number of days: " + ex.getMessage(), ex);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving upcoming appointments: " + ex.getMessage(), ex);
        }
    }

    // Delete an appointment by ID and return appropriate HTTP status
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build(); // 204 No Content if successfully deleted
        } catch (EntityNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found for deletion: " + id, ex);
        } catch (RuntimeException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error deleting appointment: " + ex.getMessage(), ex);
        }
    }

    // Update an existing appointment using partial or full data
    @PutMapping("/update/{id}")
    public ResponseEntity<Appointment> update(@PathVariable String id, @RequestBody AppointmentDTO dto) {
        try {
            if (dto == null) {
                throw new IllegalArgumentException("Appointment data cannot be null.");
            }
            Appointment updated = service.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException ex) {
            // Appointment not found for the given ID
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment not found with ID: " + id, ex);
        } catch (IllegalArgumentException ex) {
            // Handle invalid input
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
        } catch (DataIntegrityViolationException ex) {
            // Handle data consistency issues
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Database constraint violation: " + ex.getMessage(), ex);
        } catch (RuntimeException ex) {
            // Generic fallback for unexpected runtime errors
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating appointment: " + ex.getMessage(), ex);
        }
    }
}
