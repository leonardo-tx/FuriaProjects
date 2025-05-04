package gg.furia.cs.chat.api.controller;

import gg.furia.cs.chat.api.configuration.SecurityConfig;
import gg.furia.cs.chat.api.dto.ApiResponse;
import gg.furia.cs.chat.api.dto.user.UserDetailedViewDTO;
import gg.furia.cs.chat.api.dto.user.UserSimpleViewDTO;
import gg.furia.cs.chat.core.decorators.NeedsUserContext;
import gg.furia.cs.chat.core.entity.User;
import gg.furia.cs.chat.core.service.UserService;
import gg.furia.cs.chat.core.utils.exception.FuriaException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final SecurityConfig securityConfig;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserSimpleViewDTO>> getUserById(
            @PathVariable("id") Long id
    ) throws FuriaException {
        User targetUser = userService.getUserById(id);
        UserSimpleViewDTO userSimpleViewDTO = UserSimpleViewDTO.parse(targetUser);

        return ApiResponse.success(userSimpleViewDTO, HttpStatus.OK).createResponseEntity();
    }

    @GetMapping("/me")
    @NeedsUserContext
    public ResponseEntity<ApiResponse<UserDetailedViewDTO>> getSelf() throws FuriaException {
        User user = userService.getContext().getUserOrThrow();
        UserDetailedViewDTO userDetailedViewDTO = UserDetailedViewDTO.parse(user);

        return ApiResponse.success(userDetailedViewDTO, HttpStatus.OK).createResponseEntity();
    }
}
