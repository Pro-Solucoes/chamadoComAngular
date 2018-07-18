package com.pro.chamado.security.service;

import com.pro.chamado.entity.Usuario;
import com.pro.chamado.security.jwt.JwtUserFactory;
import com.pro.chamado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario = usuarioService.findByEmail(email);
        if (usuario == null) {
            throw new UsernameNotFoundException(String.format("Não tem usuario com esse login '%s',", email));
        } else {
            return JwtUserFactory.create(usuario);
        }

    }
}
