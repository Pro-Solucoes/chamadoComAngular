package com.pro.chamado.controller;

import com.pro.chamado.entity.Usuario;
import com.pro.chamado.response.Response;
import com.pro.chamado.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Usuario>> create(HttpServletRequest request,
                                                    @RequestBody Usuario usuario,
                                                    BindingResult result) {
        Response<Usuario> response = new Response<>();
        try {
            validateCreateUsuario(usuario, result);
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
            Usuario usuarioPersisted = (Usuario) usuarioService.createOrUpdate(usuario);
            response.setData(usuarioPersisted);

        } catch (DuplicateKeyException dE) {
            response.getErrors().add("Email esta registrado !");
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);

    }

    private void validateCreateUsuario(Usuario usuario, BindingResult result) {
        if (usuario.getEmail() == null) {
            result.addError(new ObjectError("usuario", "Email nao informado"));

        }
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Usuario>> update(HttpServletRequest request,
                                                    @RequestBody Usuario usuario,
                                                    BindingResult result) {
        Response<Usuario> response = new Response<>();
        try {
            validateUpdateUsuario(usuario, result);
            if (result.hasErrors()) {
                result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.getErrors().add(e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario usuarioPersisted = (Usuario) usuarioService.createOrUpdate(usuario);
        response.setData(usuarioPersisted);
        return ResponseEntity.ok(response);
    }

    private void validateUpdateUsuario(Usuario usuario, BindingResult result) {
        if (usuario.getId() == null) {
            result.addError(new ObjectError("usuario", "Email nao informado"));

        }
        if (usuario.getEmail() == null) {
            result.addError(new ObjectError("usuario", "Email nao informado"));

        }
    }

    @GetMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Usuario>> findById(@PathVariable("id") String id) {
        Response<Usuario> response = new Response<>();
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            response.getErrors().add("Registro nao encontrado id:" + id);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(usuario);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") String id) {
        Response<String> response = new Response<String>();
        Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            response.getErrors().add("Registro nao existe com id: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        usuarioService.delete(id);

        return ResponseEntity.ok(new Response<>());
    }

    @GetMapping(value = "{page}/{count}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<Page<Usuario>>> findAll(@PathVariable int page, @PathVariable int count) {
        Response<Page<Usuario>> response = new Response<Page<Usuario>>();
        Page<Usuario> usuarios = usuarioService.findAll(page, count);
        response.setData(usuarios);
        return ResponseEntity.ok(response);
    }
}
