package gg.furia.cs.chat.api.dto.user;

import gg.furia.cs.chat.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class UserRegisterDTO {
    private String userName;
    private String name;
    private String email;
    private String password;

    public User convertToEntity() {
        return User.builder()
                .userName(userName)
                .name(name)
                .email(email)
                .password(password)
                .build();
    }
}
