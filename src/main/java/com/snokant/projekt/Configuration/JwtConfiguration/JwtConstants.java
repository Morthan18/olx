package com.snokant.projekt.Configuration.JwtConfiguration;

import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

public class JwtConstants {
    public static String SECRET = "secretKeyToJwtAuth";
    public static final long EXPIRATION_TIME = 60000*60*6;
    public static final String HEADER_STRING = "Authorization";
}
