package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.components.JwtCore;
import com.roseny.logisticscrm.dtos.requests.LoginRequest;
import com.roseny.logisticscrm.dtos.requests.RegisterRequest;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.models.enums.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtCore jwtCore;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> registerUser(@Valid RegisterRequest registerRequest) {
        if (userService.existsByUsername(registerRequest.getUsername())
                || userService.existsByEmail(registerRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Пользователь уже существует!");
        }

        User user = new User();

        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setActive(true);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.getRoles().add(Role.ROLE_DEFAULT);

        userService.save(user);

        return ResponseEntity.ok("Вы успешно зарегистрировались!");
    }

    public ResponseEntity<?> loginUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtCore.generateToken(userService.loadUserByUsername(loginRequest.getUsername()));

        return ResponseEntity.ok(token);
    }
}
