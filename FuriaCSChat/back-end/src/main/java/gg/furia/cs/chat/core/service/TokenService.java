package gg.furia.cs.chat.core.service;

import gg.furia.cs.chat.core.entity.RefreshToken;
import gg.furia.cs.chat.core.entity.TokenProperties;
import gg.furia.cs.chat.core.entity.User;
import gg.furia.cs.chat.core.repository.RefreshTokenRepository;
import gg.furia.cs.chat.core.utils.exception.FuriaErrorCode;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import gg.furia.cs.chat.core.utils.validator.TokenValidator;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
public class TokenService {
    private static final SecretKey accessSecretKey = Jwts.SIG.HS256.key().build();
    private final SecretKey refreshSecretKey;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProperties tokenProperties;

    public TokenService(RefreshTokenRepository refreshTokenRepository, TokenProperties tokenProperties) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenProperties = tokenProperties;

        byte[] keyBytes = tokenProperties.getRefreshTokenSecret().getBytes(StandardCharsets.UTF_8);
        refreshSecretKey = new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public String createAccessToken(RefreshToken refreshToken) throws FuriaException {
        Claims claims = TokenValidator.validateRefreshToken(refreshToken, refreshSecretKey);
        if (!refreshTokenRepository.existsById(refreshToken.getId())) {
            throw new FuriaException(FuriaErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        Date issuedAt = Date.from(Instant.now());
        Date expiration = Date.from(issuedAt.toInstant()
                .plusSeconds(tokenProperties.getAccessTokenMaxAge())
        );

        return Jwts.builder()
                .issuedAt(issuedAt)
                .expiration(expiration)
                .subject(claims.getSubject())
                .claim("userName", claims.get("userName"))
                .claim("name", claims.get("name"))
                .claim("email", claims.get("email"))
                .signWith(accessSecretKey)
                .compact();
    }

    public RefreshToken createRefreshToken(User user) {
        Date issuedAt = Date.from(Instant.now());
        Date expiration = Date.from(issuedAt.toInstant()
                .plusSeconds(tokenProperties.getRefreshTokenMaxAge())
        );

        String token = Jwts.builder()
                .issuedAt(issuedAt)
                .subject(user.getId().toString())
                .claim("userName", user.getUserName())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .signWith(refreshSecretKey)
                .compact();
        RefreshToken refreshToken = new RefreshToken(token, expiration, user);
        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken increaseRefreshTokenExpiration(String refreshToken) throws FuriaException {
        RefreshToken refreshTokenFromDatabase = refreshTokenRepository.findById(refreshToken).orElse(null);
        if (refreshTokenFromDatabase == null) {
            throw new FuriaException(FuriaErrorCode.REFRESH_TOKEN_EXPIRED);
        }
        TokenValidator.validateRefreshToken(refreshTokenFromDatabase, refreshSecretKey);

        Date expiration = Date.from(Instant.now()
                .plusSeconds(tokenProperties.getRefreshTokenMaxAge())
        );
        refreshTokenFromDatabase.setExpiration(expiration);

        return refreshTokenRepository.save(refreshTokenFromDatabase);
    }

    public Long getUserIdFromAccessToken(String token) throws FuriaException {
        String idAsString = TokenValidator.validateAccessToken(token, accessSecretKey).getSubject();
        return Long.parseLong(idAsString);
    }

    public void deleteRefreshTokenById(String token) {
        refreshTokenRepository.deleteById(token);
    }
}
