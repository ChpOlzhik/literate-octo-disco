package com.example.diplomawork.service;

import com.example.diplomawork.exception.SpringAppException;
import com.example.diplomawork.model.PasswordResetToken;
import com.example.diplomawork.model.User;
import com.example.diplomawork.repository.PasswordResetTokenRepository;
import com.example.diplomawork.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    public String generatePasswordResetToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new EntityNotFoundException("No such user with email:" + email));
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(UUID.randomUUID().toString());
        resetToken.setExpireDate(Instant.now().plusSeconds(43200));
        resetToken.setUser(user);
        passwordResetTokenRepository.save(resetToken);
        return resetToken.getToken();
    }

    @SneakyThrows
    User getUserAndValidate(String token) {
        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringAppException("Invalid reset Token"));
        if (resetToken.getExpireDate().compareTo(Instant.now()) < 0) {
            this.deletePasswordResetToken(token);
            throw new SpringAppException("Token expired");
        }

        return resetToken.getUser();
    }

    public void deletePasswordResetToken(String token){
        passwordResetTokenRepository.deleteByToken(token);
    };

}
