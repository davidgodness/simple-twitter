package com.davidgodness.simpletwitter.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfig {
    @Value("${spring.security.oauth2.resourceserver.jwt.private-key-location}")
    private RSAPrivateKey rsaPrivateKey;

    @Value("${spring.security.oauth2.resourceserver.jwt.public-key-location}")
    private RSAPublicKey rsaPublicKey;

    @Bean
    public JWK jwk() {
        return new RSAKey.Builder(rsaPublicKey).privateKey(rsaPrivateKey).build();
    }

    @Bean
    public JWKSet jwkSet(JWK jwk) {
        return new JWKSet(jwk);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource(JWKSet jwkSet) {
        return new ImmutableJWKSet<>(jwkSet);
    }

    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> source) {
        return new NimbusJwtEncoder(source);
    }
}
