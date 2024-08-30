package com.rakbank.accountmanagement.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    // In-memory session store for demonstration
    private final Map<String, String> sessionStore = new ConcurrentHashMap<>();

    // Method to create a session and return a session ID
    public String createSession(String email) {
        String sessionId = generateSessionId();
        sessionStore.put(sessionId, email);
        return sessionId;
    }

    // Validate session ID
    public boolean isValidSession(String sessionId) {
        return sessionStore.containsKey(sessionId);
    }

    // Retrieve user email from session ID
    public Optional<String> getUserEmailFromSession(String sessionId) {
        return Optional.ofNullable(sessionStore.get(sessionId));
    }

    private String generateSessionId() {
        // For demonstration, generate a simple UUID-based session ID
        return java.util.UUID.randomUUID().toString();
    }
}
