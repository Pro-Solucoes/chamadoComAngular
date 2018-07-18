package com.pro.chamado.service;


import com.pro.chamado.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface UsuarioService {

    Usuario findByEmail(String email);
    Usuario createOrUpdate(Usuario usuario);
    Usuario findById(String id);
    void delete(String id);
    Page<Usuario>findAll(int page,int count);
}
