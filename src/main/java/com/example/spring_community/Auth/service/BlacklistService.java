package com.example.spring_community.Auth.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class BlacklistService {
    private final Map<String, Long> blacklist = new ConcurrentHashMap<>();

    public void blacklist(String accessToken, long accessExpiration) {
        blacklist.put(accessToken, accessExpiration);
    }

    public boolean isBlacklist(String accessToken) {
        Long expiration = blacklist.get(accessToken);
        if (expiration == null) return false;
        if (expiration < Instant.now().toEpochMilli()) {
            blacklist.remove(accessToken);
            return false;
        }
        return true;
    }

}
