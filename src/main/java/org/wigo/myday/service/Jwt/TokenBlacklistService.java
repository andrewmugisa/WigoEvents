package org.wigo.myday.service.Jwt;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistService {

    // token -> expiry time
    private final Map<String, Instant> blacklist = new ConcurrentHashMap<>();

    /**
     * Add token to blacklist until it naturally expires
     */
    public void blacklistToken(String token, Instant expiryTime) {
        blacklist.put(token, expiryTime);
    }

    /**
     * Check if token is blacklisted
     */
    public boolean isBlacklisted(String token) {
        Instant expiry = blacklist.get(token);

        if (expiry == null) return false;

        // auto-clean expired entries
        if (expiry.isBefore(Instant.now())) {
            blacklist.remove(token);
            return false;
        }

        return true;
    }

    /**
     * Cleanup method (optional future scheduler)
     */
    public void cleanup() {
        Instant now = Instant.now();
        blacklist.entrySet().removeIf(entry -> entry.getValue().isBefore(now));
    }
}