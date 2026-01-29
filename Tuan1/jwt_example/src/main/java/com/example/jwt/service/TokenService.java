package com.example.jwt.service;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TokenService {

    private final RSAPrivateKey privateKey;
    private final String kid;

    public TokenService(KeyPair keyPair) {
        this.privateKey = (RSAPrivateKey) keyPair.getPrivate();
        this.kid = UUID.randomUUID().toString();
    }

    public String mintDemoToken(List<String> scopes) throws Exception {
        Instant now = Instant.now();
        Date exp = Date.from(now.plusSeconds(3600));

        JWSSigner signer = new RSASSASigner(privateKey);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .issuer("demo-auth")
                .subject("user123")
                .audience("jwt-resource-server")
                .expirationTime(exp)
                .issueTime(Date.from(now))
                .claim("scope", scopes)
                .build();

        SignedJWT jwt = new SignedJWT(
                new com.nimbusds.jose.JWSHeader.Builder(JWSAlgorithm.RS256)
                        .keyID(kid)
                        .build(),
                claims);
        jwt.sign(signer);
        return jwt.serialize();
    }
}
