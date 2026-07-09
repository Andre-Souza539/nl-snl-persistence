package dev.nerlab.backend.controller;

import dev.nerlab.backend.dto.auth.LoginRequestDTO;
import dev.nerlab.backend.dto.auth.LoginResponseDTO;
import dev.nerlab.backend.model.User;
import dev.nerlab.backend.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO data) {
        UsernamePasswordAuthenticationToken usernamePassword =
                new UsernamePasswordAuthenticationToken(data.email(), data.password());
        Authentication authenticate = this.authenticationManager.authenticate(usernamePassword);
        String token = tokenService.generateToken((User) authenticate.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));

    }

}
