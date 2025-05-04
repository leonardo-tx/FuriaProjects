package gg.furia.cs.chat.api.dto.user;

import gg.furia.cs.chat.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailedViewDTO {
    private Long id;
    private String userName;
    private String name;
    private String email;

    public static UserDetailedViewDTO parse(User entity) {
        return new UserDetailedViewDTO(
                entity.getId(),
                entity.getUserName(),
                entity.getName(),
                entity.getEmail()
        );
    }
}
