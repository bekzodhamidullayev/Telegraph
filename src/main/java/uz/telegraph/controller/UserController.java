package uz.telegraph.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.telegraph.entity.UserEntity;
import uz.telegraph.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get-all")
    public List<UserEntity> getAll(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return userService.getAll(page, size);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    public UserEntity update(
            @RequestParam UUID id,
            @RequestParam String role
    ) {
        return userService.update(id, role);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}")
    public String delete(@PathVariable UUID userId) {
        return userService.delete(userId);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/test-user")
    public String testUser() {
        return "Ishlayapti";
    }
}
