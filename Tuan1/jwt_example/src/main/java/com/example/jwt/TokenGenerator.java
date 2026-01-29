package com.example.jwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * Utility to mint a demo RS256 token using the bundled private key.
 */
public class TokenGenerator {

    public static void main(String[] args) throws Exception {
        Instant now = Instant.now();
        Date exp = Date.from(now.plusSeconds(3600)); // 1h

        // load private key (PKCS#1)
        try (InputStream is = TokenGenerator.class.getClassLoader().getResourceAsStream("keys/private.pem")) {
            if (is == null) {
                throw new IllegalStateException("keys/private.pem not found");
            }
            RSAPrivateKey privateKey = loadPrivateKey(is);
            RSAPublicKey publicKey = loadPublicKey();

            RSAKey rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID("demo-kid")
                    .build();

            JWSSigner signer = new RSASSASigner(privateKey);

            JWTClaimsSet claims = new JWTClaimsSet.Builder()
                    .issuer("demo-auth")
                    .subject("user123")
                    .audience("jwt-resource-server")
                    .expirationTime(exp)
                    .issueTime(Date.from(now))
                    .claim("scope", List.of("read"))
                    .build();

            SignedJWT jwt = new SignedJWT(
                    new com.nimbusds.jose.JWSHeader.Builder(JWSAlgorithm.RS256)
                            .keyID(rsaKey.getKeyID())
                            .build(),
                    claims);
            jwt.sign(signer);

            System.out.println("Token (RS256, valid 1h):\n" + jwt.serialize());
        }
    }

    private static RSAPrivateKey loadPrivateKey(InputStream pemStream) throws Exception {
        try (PEMParser parser = new PEMParser(new InputStreamReader(pemStream))) {
            Object object = parser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
            PrivateKey privateKey;
            if (object instanceof PEMKeyPair keyPair) {
                privateKey = converter.getPrivateKey(keyPair.getPrivateKeyInfo());
            } else if (object instanceof PrivateKeyInfo info) {
                privateKey = converter.getPrivateKey(info);
            } else {
                throw new IllegalArgumentException("Unsupported PEM object: " + object);
            }
            return (RSAPrivateKey) privateKey;
        }
    }

    private static RSAPublicKey loadPublicKey() throws Exception {
        try (InputStream is = TokenGenerator.class.getClassLoader().getResourceAsStream("keys/public.pem")) {
            if (is == null) {
                throw new IllegalStateException("keys/public.pem not found");
            }
            String pem = new String(is.readAllBytes(), StandardCharsets.UTF_8)
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "")
                    .replaceAll("\\s", "");
            byte[] der = Base64.getDecoder().decode(pem);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(der);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return (RSAPublicKey) kf.generatePublic(spec);
        }
    }
}
