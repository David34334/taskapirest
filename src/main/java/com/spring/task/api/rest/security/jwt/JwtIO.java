package com.spring.task.api.rest.security.jwt;

import com.spring.task.api.rest.utils.ParseObjectsToJson;
import io.fusionauth.jwt.InvalidJWTSignatureException;
import io.fusionauth.jwt.JWTUtils;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.TimeZone;

@Component
@Slf4j
public class JwtIO {

    @Value("${jwt.token.secret}")
    private String SECRET_KEY;

    @Value("${timezone.date:UTC}")
    private String TIMEZONE;

    @Value("${jwt.token.expires-in}")
    private int TOKEN_EXPIRES_IN;

    @Value("${jwt.issuer}")
    private String ISSUER_TOKEN;

    /**
     * CREACIÓN DE TOKEN CON JWT
     */
    public String generateNewToken(Object src) {
        String subject = ParseObjectsToJson.convertObjectToJson(src);
        Signer signer = HMACSigner.newSHA256Signer(SECRET_KEY);
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE);
        ZonedDateTime zonedDateTime = ZonedDateTime.now(timeZone.toZoneId()).plusSeconds(TOKEN_EXPIRES_IN);
        JWT jwt = new JWT()
                .setIssuer(ISSUER_TOKEN)
                .setIssuedAt(ZonedDateTime.now(timeZone.toZoneId()))
                .setSubject(subject)
                .setExpiration(zonedDateTime);
        return JWT.getEncoder().encode(jwt, signer);
    }

    /**
     * Metodo para validar el token
     * */
    public boolean validateToken(String encodedJWT) {
        boolean tokenInvalid = true;
        try {
            Verifier verifier = HMACVerifier.newVerifier(SECRET_KEY);
            JWT jwt = JWT.getDecoder().decode(encodedJWT, verifier);
            if (jwt != null) tokenInvalid = false;
        } catch ( InvalidJWTSignatureException ise ) {
            log.info("Token invalid. User - Unauthorized");
        } catch ( Exception e ) {
            log.info("Error decoding and validate token: " + encodedJWT + "\n" + e);
        }
        return tokenInvalid;
    }

    /** Metodo para validar que el token este correcto y vigente */
    private JWT jwt(String encodedJWT) {
        try {
            return JWTUtils.decodePayload(encodedJWT);
        }catch (Exception e){
            return null;
        }
    }

}