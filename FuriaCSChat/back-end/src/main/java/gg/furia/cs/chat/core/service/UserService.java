package gg.furia.cs.chat.core.service;

import gg.furia.cs.chat.core.entity.AuthTokens;
import gg.furia.cs.chat.core.entity.RefreshToken;
import gg.furia.cs.chat.core.entity.User;
import gg.furia.cs.chat.core.repository.UserRepository;
import gg.furia.cs.chat.core.utils.exception.FuriaErrorCode;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import gg.furia.cs.chat.core.utils.validator.UserValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Getter
    private final ContextService context;
    private final UserRepository userRepository;
    private final PasswordEncoderService passwordEncoderService;
    private final TokenService tokenService;

    public User register(User user) throws FuriaException {
        UserValidator.validateFields(user);
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new FuriaException(FuriaErrorCode.EMAIL_ALREADY_EXISTS);
        }
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new FuriaException(FuriaErrorCode.USER_NAME_ALREADY_EXISTS);
        }
        String encryptedPassword = passwordEncoderService.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        user.setId(null);

        return userRepository.save(user);
    }

    public AuthTokens login(String email, String password) throws FuriaException {
        if (context.isAuthenticated()) {
            throw new FuriaException(FuriaErrorCode.LOGIN_ALREADY_LOGGED);
        }

        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null || !passwordEncoderService.checkPassword(user, password)) {
            throw new FuriaException(FuriaErrorCode.LOGIN_UNAUTHORIZED);
        }
        RefreshToken refreshToken = tokenService.createRefreshToken(user);
        String accessToken = tokenService.createAccessToken(refreshToken);

        return new AuthTokens(accessToken, refreshToken);
    }

    public void logout() {
        String refreshToken = context.getCookieValueOrDefault("refresh_token", null);
        if (!context.isAuthenticated() || refreshToken == null) return;

        tokenService.deleteRefreshTokenById(refreshToken);
    }

    public User getUserById(long id) throws FuriaException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new FuriaException(FuriaErrorCode.USER_NOT_FOUND);
        }
        return optionalUser.get();
    }
}
