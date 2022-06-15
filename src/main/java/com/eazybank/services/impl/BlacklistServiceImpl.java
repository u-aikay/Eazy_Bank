package com.eazybank.services.impl;

import com.eazybank.model.BlacklistToken;
import com.eazybank.repos.BlacklistRepo;
import com.eazybank.services.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {
    private final BlacklistRepo blacklistRepo;

    @Override
    public BlacklistToken blackListToken(String token, Date date) {
        BlacklistToken blacklistToken = BlacklistToken.builder()
                .token(token)
                .expiresAt(date)
                .build();
        return blacklistRepo.save(blacklistToken);
    }

    @Override
    public boolean isTokenBlackListed(String token) {
        return blacklistRepo.existsByToken(token);
    }
}
