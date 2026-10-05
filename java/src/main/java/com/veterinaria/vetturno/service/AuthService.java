package com.veterinaria.vetturno.service;

import com.veterinaria.vetturno.dto.AuthResponse;
import com.veterinaria.vetturno.dto.LoginRequest;
import com.veterinaria.vetturno.dto.RegistroRequest;
import com.veterinaria.vetturno.exception.BadRequestException;
import com.veterinaria.vetturno.model.Rol;
import com.veterinaria.vetturno.model.Usuario;
import com.veterinaria.vetturno.repository.UsuarioRepository;
import com.veterinaria.vetturno.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public AuthResponse registrar(RegistroRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new BadRequestException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(request.email());
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setRol(Rol.USER);
        usuarioRepository.save(usuario);

        return new AuthResponse(jwtService.generateToken(usuario));
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new BadRequestException("Credenciales inválidas"));

        return new AuthResponse(jwtService.generateToken(usuario));
    }
}
