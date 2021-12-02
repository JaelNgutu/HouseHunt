package com.example.househunt.hunter.service;

import com.example.househunt.dto.AuthenticationResponse;
import com.example.househunt.dto.LoginRequest;
import com.example.househunt.exceptions.HouseHuntException;
import com.example.househunt.hunter.domain.Hunter;
import com.example.househunt.hunter.domain.HunterDTO;
import com.example.househunt.hunter.repository.HunterRepository;
import com.example.househunt.notification.domain.NotificationEmail;
import com.example.househunt.notification.service.MailService;
import com.example.househunt.security.JwtProvider;
import com.example.househunt.verificationtoken.domain.VerificationToken;
import com.example.househunt.verificationtoken.repository.VerificationTokenRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Builder
public class HunterService {

    final PasswordEncoder passwordEncoder;
    final HunterRepository hunterRepository;
    final VerificationTokenRepository verificationTokenRepository;
    final MailService mailService;
    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;

    Hunter createHunterFromDTO(HunterDTO hunterDTO) {
        return Hunter
            .builder()
            .userName(hunterDTO.getUsername())
            .email(hunterDTO.getEmail())
            .password(passwordEncoder.encode(hunterDTO.getPassword()))
            .dateCreated(Instant.now())
            .active(false)
            .build();
    }

    public Hunter save(Hunter hunter) {
        return hunterRepository.save(hunter);
    }

    //generate verification token on sign up
    private String generateVerificationToken(Hunter hunter) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setHunter(hunter);

        verificationTokenRepository.save(verificationToken);
        return token;
    }

    //save new user and send verification email
    public Hunter createNewHunterAndSave(HunterDTO hunterDTO) {
        var newHunter = createHunterFromDTO(hunterDTO);
        newHunter = save(newHunter);
        String token = generateVerificationToken(newHunter);
        mailService.sendMail(
            new NotificationEmail(
                "Please Activate your Account",
                newHunter.getEmail(),
                "Thank you for signing up to House Hunt, " +
                "please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" +
                token
            )
        );
        return newHunter;
    }

    @Transactional(readOnly = true)
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new HouseHuntException("Invalid Token"));
        fetchUserandActivate(verificationToken.get());
    }

    @Transactional(readOnly = true)
    public Hunter getCurrentUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return hunterRepository
            .findByUserName(principal.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

    @Transactional(readOnly = true)
    public void fetchUserandActivate(VerificationToken verificationToken) {
        String username = verificationToken.getHunter().getUserName();
        Hunter hunter = hunterRepository
            .findByUserName(username)
            .orElseThrow(() -> new HouseHuntException("user not found with name " + username));
        hunter.setActive(true);
        hunterRepository.save(hunter);
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(token, loginRequest.getUsername());
    }
}
