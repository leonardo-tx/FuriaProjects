package gg.furia.cs.chat.api.dto.user;

import gg.furia.cs.chat.core.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleViewDTO {
    private Long id;
    private String userName;
    private String name;

    public static UserSimpleViewDTO parse(User entity) {
        return new UserSimpleViewDTO(
                entity.getId(),
                entity.getUserName(),
                entity.getName()
        );
    }
}
