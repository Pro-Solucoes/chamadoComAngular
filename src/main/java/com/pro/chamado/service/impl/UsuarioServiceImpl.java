package com.pro.chamado.service.impl;

import com.pro.chamado.entity.Usuario;
import com.pro.chamado.repository.UsuarioRepository;
import com.pro.chamado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



@Service
public class UsuarioServiceImpl implements UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario createOrUpdate(Usuario usuario) {
        return this.usuarioRepository.save(usuario);
    }

    @Override
    public Usuario findById(String id) {
        return this.usuarioRepository.findOne(id);
    }

    @Override
    public void delete(String id) {
        this.usuarioRepository.delete(id);

    }

    @Override
    public Page<Usuario> findAll(int page, int count) {
        Pageable pages = new PageRequest(page,count);
        return this.usuarioRepository.findAll(pages);
    }
}
