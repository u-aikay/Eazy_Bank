package com.eazybank.services;

import com.eazybank.model.BlacklistToken;
import java.util.Date;

public interface BlacklistService {
    BlacklistToken blackListToken(String token, Date date);
    boolean isTokenBlackListed(String token);
}
