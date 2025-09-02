package com.enzo.gestao_tarefas.controller;

import com.enzo.gestao_tarefas.model.Usuario;
import com.enzo.gestao_tarefas.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public AuthController(UsuarioRepository usuarioRepository,
                          PasswordEncoder passwordEncoder,
                          JwtEncoder jwtEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/register")
    public Map<String, String> register(@RequestBody Usuario usuario) {
        // criptografar senha
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        // salvar usuario
        Usuario savedUser = usuarioRepository.save(usuario);

        // gerar token
        String token = generateToken(savedUser.getEmail());

        return Map.of("token", token);
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest loginRequest) {
        // achar usuario
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // validar senha
        if (!passwordEncoder.matches(loginRequest.senha(), usuario.getSenha())) {
            throw new RuntimeException("Senha inválida");
        }

        // gerar token
        String token = generateToken(usuario.getEmail());

        return Map.of("token", token);
    }

    private String generateToken(String email) {
        Instant now = Instant.now();
        long expiry = 3600L; // 1 hora

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("http://localhost:8080")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiry))
                .subject(email)
                .claim("scope", "ROLE_USER")
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public record LoginRequest(String email, String senha) {}
}
