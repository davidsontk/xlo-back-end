package br.com.xlo.controller;

import br.com.xlo.model.Usuario;
import br.com.xlo.repository.UsuarioRepository;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

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

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String cadastroUsuario(@RequestBody Usuario usuario) {
        try {
            String hash = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt(10));
            usuario.setPassword(hash);
            usuarioRepository.save(usuario);

            return "Usuário cadastrado com sucesso " + usuario.getUsername();
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

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> logar(@RequestBody Usuario user) {

        Usuario c = usuarioRepository.findByEmail(user.getEmail());
        if (c == null) {
            return new ResponseEntity<>("Cliente não encontrado", HttpStatus.BAD_REQUEST);
        }
        if (checkPassword(user.getPassword(), c.getPassword())) {
            return new ResponseEntity<>(c, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    private boolean checkPassword(String senha, String senhaPersistida) {
        try {
            return BCrypt.checkpw(senha, senhaPersistida);//(senha, senhaPersistida);
        } catch (Exception e) {
            log.error("Erro ao comparar senha", e);
            return false;
        }
    }
}
