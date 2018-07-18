package com.pro.chamado.security.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {
    private static final long serialVersionUID = -7471177486146141709L;

    private final String id;
    private final String nomeUsuario;
    private final String senha;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUser(String id, String nomeUsuario, String senha, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.authorities = authorities;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return false;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
