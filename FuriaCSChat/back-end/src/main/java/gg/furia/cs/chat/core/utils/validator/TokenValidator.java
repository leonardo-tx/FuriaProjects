package gg.furia.cs.chat.core.utils.validator;

import gg.furia.cs.chat.core.entity.RefreshToken;
import gg.furia.cs.chat.core.utils.exception.FuriaErrorCode;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.time.Instant;

public final class TokenValidator {
    public static Claims validateAccessToken(String token, SecretKey secretKey) throws FuriaException {
        if (token == null) {
            throw new FuriaException(FuriaErrorCode.ACCESS_TOKEN_INVALID);
        }
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            if (claims.getExpiration() == null) {
                throw new FuriaException(FuriaErrorCode.ACCESS_TOKEN_INVALID);
            }
            validateCommonClaims(claims, FuriaErrorCode.ACCESS_TOKEN_INVALID);
            return claims;
        } catch (ExpiredJwtException e) {
            throw new FuriaException(FuriaErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (JwtException e) {
            throw new FuriaException(FuriaErrorCode.ACCESS_TOKEN_INVALID);
        }
    }

    public static Claims validateRefreshToken(RefreshToken refreshToken, SecretKey secretKey) throws FuriaException {
        if (refreshToken == null ||
                refreshToken.getId() == null ||
                refreshToken.getUser() == null ||
                refreshToken.getExpiration() == null) {
            throw new FuriaException(FuriaErrorCode.REFRESH_TOKEN_INVALID);
        }
        if (Instant.now().isAfter(refreshToken.getExpiration().toInstant())) {
            throw new FuriaException(FuriaErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        try {
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(refreshToken.getId())
                    .getPayload();
            validateCommonClaims(claims, FuriaErrorCode.REFRESH_TOKEN_INVALID);
            return claims;
        } catch (JwtException e) {
            throw new FuriaException(FuriaErrorCode.REFRESH_TOKEN_INVALID);
        }
    }

    private static void validateCommonClaims(Claims claims, FuriaErrorCode errorCode) throws FuriaException {
        try {
            Long.parseLong(claims.getSubject());
            if (claims.get("userName") == null ||
                    claims.get("name") == null ||
                    claims.get("email") == null ||
                    claims.getIssuedAt() == null) {
                throw new FuriaException(errorCode);
            }
        } catch (FuriaException e) {
            throw e;
        }
        catch (Exception e) {
            throw new FuriaException(errorCode);
        }
    }
}
