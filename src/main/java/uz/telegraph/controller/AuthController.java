package uz.telegraph.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.telegraph.dto.AuthDto;
import uz.telegraph.dto.UserCreateDto;
import uz.telegraph.dto.responce.JwtResponse;
import uz.telegraph.entity.UserRole;
import uz.telegraph.service.UserService;

import java.util.UUID;

import static uz.telegraph.entity.UserRole.ADMIN;
import static uz.telegraph.entity.UserRole.USER;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    @PostMapping("/admin/sign-up")
    public String authAdmin (@RequestBody UserCreateDto dto) {
        dto.setRole(String.valueOf(ADMIN));
        return userService.add(dto);
    }

    @PostMapping("/user/sign-up")
    public String authUser (@RequestBody UserCreateDto dto) {
        dto.setRole(String.valueOf(USER));
        return userService.add(dto);
    }

    @PostMapping("/sign-in")
    public JwtResponse signIn (@RequestBody AuthDto dto) {
        return userService.signIn(dto);
    }
}
