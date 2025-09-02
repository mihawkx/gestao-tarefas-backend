package com.enzo.gestao_tarefas.controllers;

import com.enzo.gestao_tarefas.domain.Usuario;
import com.enzo.gestao_tarefas.dto.LoginRequestDTO;
import com.enzo.gestao_tarefas.dto.RegisterRequestDTO;
import com.enzo.gestao_tarefas.dto.ResponseDTO;
import com.enzo.gestao_tarefas.infra.security.TokenService;
import com.enzo.gestao_tarefas.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        Usuario usuario = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), usuario.getPassword())) {
            String token = this.tokenService.generateToken(usuario);
            return ResponseEntity.ok(new ResponseDTO(usuario.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequestDTO body){
        Optional<Usuario> usuario = this.repository.findByEmail(body.email());

        if(usuario.isEmpty()) {
            Usuario newUsuario = new Usuario();
            newUsuario.setPassword(passwordEncoder.encode(body.password()));
            newUsuario.setEmail(body.email());
            newUsuario.setName(body.name());
            this.repository.save(newUsuario);

            String token = this.tokenService.generateToken(newUsuario);
            return ResponseEntity.ok(new ResponseDTO(newUsuario.getName(), token));
        }
        return ResponseEntity.badRequest().build();
    }
}
