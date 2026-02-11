package com.gb.Viewhr.controller;


import com.gb.Viewhr.dto.auth.AuthRequest;
import com.gb.Viewhr.dto.auth.AuthResponse;
import com.gb.Viewhr.dto.auth.RegisterRequest;
import com.gb.Viewhr.entity.Role;
import com.gb.Viewhr.entity.User;
import com.gb.Viewhr.repository.UserRepository;
import com.gb.Viewhr.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          JwtService jwtService,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }

        Role role = request.getRole() != null
                ? Role.valueOf(request.getRole())
                : Role.EMPLOYEE;

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .status("ACTIVE")
                .build();

        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail()).get();
        String token = jwtService.generateToken(user.getEmail());

        return new AuthResponse(token, user.getEmail(), user.getRole().name());
    }

    @GetMapping("/me")
    public String me(@RequestParam String email) {
        return email;
    }
}