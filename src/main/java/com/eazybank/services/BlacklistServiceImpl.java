package com.eazybank.services;

import com.eazybank.model.BlacklistToken;
import com.eazybank.repos.BlacklistRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;


@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService{
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
