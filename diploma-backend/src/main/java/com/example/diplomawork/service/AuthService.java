package com.example.diplomawork.service;

import com.example.diplomawork.exception.SpringAppException;
import com.example.diplomawork.mapper.RoleMapper;
import com.example.diplomawork.model.User;
import com.example.diplomawork.model.VerificationToken;
import com.example.diplomawork.repository.RoleRepository;
import com.example.diplomawork.repository.UserRepository;
import com.example.diplomawork.repository.VerificationTokenRepository;
import com.example.diplomawork.security.JwtProvider;
import com.example.models.AuthenticationResponse;
import com.example.models.LoginRequest;
import com.example.models.RefreshTokenRequest;
import com.example.models.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    private final RoleMapper roleMapper;

    public void signup(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .username(request.getUsername())
                .email(request.getEmail())
                .birthDate(request.getBirthDate())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleRepository.findByName("ROLE_STUDENT"))
                .build();
        userRepository.save(user);
    }


    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringAppException("User not found with name - " + username));
        userRepository.save(user);
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        fetchUserAndEnable(verificationToken.orElseThrow(() -> new SpringAppException("Invalid Token")));
    }

    public AuthenticationResponse login(LoginRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),
                request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticationToken(token);
        authenticationResponse.setRefreshToken(refreshTokenService.generateRefreshToken().getToken());
        authenticationResponse.setExpiresAt(LocalDate.from(OffsetDateTime.now().plusSeconds((jwtProvider.getJwtExpirationInMillis()))));
        authenticationResponse.setUsername(request.getUsername());
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new EntityNotFoundException("User with username:" + request.getUsername() + "not found"));
        authenticationResponse.setRole(user.getRole().getName());
        return authenticationResponse;
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setAuthenticationToken(token);
        authenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
        authenticationResponse.setExpiresAt(LocalDate.from(OffsetDateTime.now().plusSeconds((jwtProvider.getJwtExpirationInMillis()))));
        authenticationResponse.setUsername(refreshTokenRequest.getUsername());
        return authenticationResponse;
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
}
