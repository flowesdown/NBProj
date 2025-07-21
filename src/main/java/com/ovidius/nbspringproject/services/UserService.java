package com.ovidius.nbspringproject.services;

import com.ovidius.nbspringproject.dto.UserRegistrationDto;
import com.ovidius.nbspringproject.exceptions.UserAlreadyExistsException;
import com.ovidius.nbspringproject.models.User;
import com.ovidius.nbspringproject.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User registerNewUser(UserRegistrationDto registrationDto, PasswordEncoder passwordEncoder) {
        userRepository.findByUsername(registrationDto.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(
                            String.format("Пользователь: %s уже существует", registrationDto.getUsername())
                    );
                });
        User newUser = new User();
        newUser.setUsername(registrationDto.getUsername());

        newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

        return userRepository.save(newUser);
    }
}
