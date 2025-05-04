package gg.furia.cs.chat.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthTokens {
    private String accessToken;
    private RefreshToken refreshToken;
}
