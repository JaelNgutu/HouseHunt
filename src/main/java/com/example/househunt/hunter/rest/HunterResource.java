package com.example.househunt.hunter.rest;

import com.example.househunt.dto.AuthenticationResponse;
import com.example.househunt.dto.LoginRequest;
import com.example.househunt.hunter.domain.Hunter;
import com.example.househunt.hunter.domain.HunterDTO;
import com.example.househunt.hunter.service.HunterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class HunterResource {

    final HunterService hunterService;

    @PostMapping("/signup")
    public ResponseEntity<Hunter> createDriverFromDto(@RequestBody HunterDTO hunterDTO) {
        var newHunter = hunterService.createNewHunterAndSave(hunterDTO);
        return ResponseEntity.ok(newHunter);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        hunterService.verifyAccount(token);
        return new ResponseEntity<>("Account activated successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return hunterService.login(loginRequest);
    }
}
