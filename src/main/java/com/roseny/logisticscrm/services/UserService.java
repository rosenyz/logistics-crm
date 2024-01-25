package com.roseny.logisticscrm.services;

import com.roseny.logisticscrm.dtos.response.InfoAboutUserResponse;
import com.roseny.logisticscrm.models.User;
import com.roseny.logisticscrm.repositories.UserRepository;
import com.roseny.logisticscrm.repositories.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public User findUserByPrincipal(Principal principal) {
        return userRepository.findByUsername(principal.getName()).orElse( userRepository.findByUsername(principal.getName()).orElse( null ));
    }

    public ResponseEntity<?> getInfoAboutUserById(Long userId) {
        User user = userRepository.findById(userId).orElse( user = null );

        if (user == null) { return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Такого пользователя не существует"); }

        InfoAboutUserResponse userInfo = new InfoAboutUserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getActive(),
                user.getAddress(),
                user.getOrders(),
                user.getDateOfCreate()
        );

        return ResponseEntity.ok(userInfo);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("Пользователь не найден!"));
        return UserDetailsImpl.build(user);
    }
}
