package com.pro.chamado.entity;

import com.pro.chamado.enums.PerfilEnum;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.Size;

@Document
public class Usuario {

    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank(message = "Email Obrigatorio")
    @Email(message = "Email Invalido")
    private String email;

    @NotBlank(message = "Senha Obrigatorio")
    @Size(min = 6)
    private String senha;

    private PerfilEnum perfil;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilEnum getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilEnum perfil) {
        this.perfil = perfil;
    }
}
