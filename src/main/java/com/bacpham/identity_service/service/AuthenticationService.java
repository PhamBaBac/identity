package com.bacpham.identity_service.service;

import com.bacpham.identity_service.dto.request.AuthenticationRequest;
import com.bacpham.identity_service.dto.request.IntrospectRequest;
import com.bacpham.identity_service.dto.response.AuthenticationResponse;
import com.bacpham.identity_service.dto.response.IntrospectResponse;
import com.bacpham.identity_service.dto.response.UserResponse;
import com.bacpham.identity_service.exception.AppException;
import com.bacpham.identity_service.exception.ErrorCode;
import com.bacpham.identity_service.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    public IntrospectResponse introspect(IntrospectRequest request)
            throws JOSEException, ParseException {
        var token = request.getToken();

        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        return IntrospectResponse.builder()
                .valid(verified && expiryTime.after(new Date()))
                .build();
    }



    public AuthenticationResponse authentication(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated)
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        var token = generateToken(user.getUsername());
        return AuthenticationResponse.builder()
                .token(token)
                .isAuthenticated(true)
                .build();
    }
    private String generateToken(String userName){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet claims = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer("identity-service")
                .issueTime(new Date())
                .expirationTime(new Date(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()).getTime()))
                .claim(JWTClaimNames.AUDIENCE, "identity-service")
                .build();

        Payload payload = new Payload(claims.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing token", e);
            throw new RuntimeException(e);
        }

    }
}
