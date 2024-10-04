package com.eleganTime.elegantime.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SenhaService {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public String encriptarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    public boolean verificarSenha(String senha, String hashed) {
        return passwordEncoder.matches(senha, hashed);
    }
}