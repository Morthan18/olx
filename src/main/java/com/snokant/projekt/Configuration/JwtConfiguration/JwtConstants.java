package com.snokant.projekt.Configuration.JwtConfiguration;

import io.jsonwebtoken.impl.crypto.MacProvider;

import java.security.Key;

public class JwtConstants {
    public static String SECRET = "secretKey";
    public static final long EXPIRATION_TIME = 1800000; // 10 days
    public static final String HEADER_STRING = "Authorization";
}
