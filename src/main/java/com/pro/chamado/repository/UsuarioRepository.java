package com.pro.chamado.repository;


import com.pro.chamado.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepository extends MongoRepository<Usuario,String> {

    Usuario findByEmail(String email);



}

