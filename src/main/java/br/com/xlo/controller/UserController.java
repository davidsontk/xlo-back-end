package br.com.xlo.controller;

import br.com.xlo.model.Usuario;
import br.com.xlo.repository.UsuarioRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Davidson
 */
@RestController
@RequestMapping("/users")
public class UserController {
private Logger log = LogManager.getLogger(UserController.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UsuarioRepository usuarioRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody Usuario user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
        usuarioRepository.save(user);
    }

    @PostMapping
    public String cadastroUsuario(@RequestBody Usuario usuario) {
        try {
            String hash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(10));
            usuario.setPassword(hash);
            usuarioRepository.save(usuario);
            return "Usuário cadastro com sucesso " + usuario.getUsername();
            
        } catch (Exception e) {
            log.error("Erro ao salvar usuario ", e);
            return "Erro ao cadastrar usuario" + usuario.getUsername();
        }
    }

    @PostMapping("/alterarUsurio")
    public void alterarUsuario(@RequestBody Usuario usuario) {
        Usuario user;
       // user = (Usuario) usuarioRepository.findById(usuario.getId().longValue());

    }

}
