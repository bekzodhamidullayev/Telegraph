package uz.telegraph.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.telegraph.dto.AuthDto;
import uz.telegraph.dto.UserCreateDto;
import uz.telegraph.dto.responce.JwtResponse;
import uz.telegraph.entity.UserEntity;
import uz.telegraph.entity.UserRole;
import uz.telegraph.exception.DataAlreadyExistsException;
import uz.telegraph.exception.DataNotFoundException;
import uz.telegraph.repo.UserRepository;
import uz.telegraph.service.jwt.JwtService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public String add(UserCreateDto dto) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(dto.getUsername());
        if(userEntity.isPresent()) {
            throw  new DataAlreadyExistsException("User already exists");
        }
        UserEntity user = modelMapper.map(dto, UserEntity.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "Successfully";
    }


    public JwtResponse signIn(AuthDto dto) {
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new DataNotFoundException("user not found"));
        System.out.println(user.getRole());

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return new JwtResponse(jwtService.generateToken(user));
        }
        throw new AuthenticationCredentialsNotFoundException("password didn't match");
    }

    public List<UserEntity> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable).getContent();
    }

    public UserEntity update(UUID id, String role) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("user not found")
        );

        userEntity.setRole(UserRole.valueOf(role));
        userRepository.save(userEntity);
        return userEntity;
    }

    public String delete(UUID userId) {
        if(userRepository.existsById(userId)) {
            throw new DataNotFoundException("user not found");
        }
        userRepository.deleteById(userId);
        return "user delete";
    }

}
