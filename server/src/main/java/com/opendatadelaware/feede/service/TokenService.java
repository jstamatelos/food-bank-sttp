package com.opendatadelaware.feede.service;

import com.opendatadelaware.feede.config.jwt.JwtAuthenticationProvider;
import com.opendatadelaware.feede.config.jwt.JwtSettings;
import com.opendatadelaware.feede.config.jwt.token.JwtToken;
import com.opendatadelaware.feede.dao.TokenDao;
import com.opendatadelaware.feede.model.Tokens;
import com.opendatadelaware.feede.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by aaronlong on 7/4/17.
 */
@Service
@Transactional
public class TokenService extends AbstractService<TokenDao> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenService.class);

    private final JwtSettings settings;

    @Autowired
    public TokenService(final JwtSettings theSettings) {
        settings = theSettings;
    }

    public EntityWrapper<Tokens> getTokenEntityFromJtiToken(String jtiToken) {
        Optional<Tokens> token = dao.getTokenEntityFromJTI(jtiToken);
        return EntityWrapper.makeWrapper(token);
    }

    public boolean validateUserFromToken(EntityWrapper<Tokens> token, EntityWrapper<Users> user) {
        if (token.isPopulated() && user.isPopulated()) {
            boolean result = token.getEntityObject().getUser().getUuid().equals(user.getEntityObject().getUuid());
            LOGGER.debug("Double check on token and user consistency: " + result);
            LOGGER.debug(token.getEntityObject().getUser().getUuid().toString());
            LOGGER.debug(user.getEntityObject().getUuid().toString());
            return result;
        }
        return false;
    }

    public JwtToken createToken(EntityWrapper<Users> user) {
        EntityWrapper<Tokens> token = EntityWrapper.makeWrapper(dao.createTokenEntry(user));
        return JwtToken.createTokenInstance(token, settings.getTokenSigningKey());
    }

}