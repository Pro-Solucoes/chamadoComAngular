package com.pro.chamado;

import com.pro.chamado.entity.Usuario;
import com.pro.chamado.enums.PerfilEnum;
import com.pro.chamado.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class ChamadoApplication {


    public static void main(String[] args) {
        SpringApplication.run(ChamadoApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            initUsuarios(usuarioRepository, passwordEncoder);
        };
    }

    private void initUsuarios(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        Usuario admin = new Usuario();
        admin.setEmail("admin@chamado.com");
        admin.setSenha(passwordEncoder.encode("123456"));
        admin.setPerfil(PerfilEnum.ROLE_ADMIN);

        Usuario find = usuarioRepository.findByEmail("admin@chamado.com");
        if (find == null) {
            usuarioRepository.save(admin);
        }
    }

}

