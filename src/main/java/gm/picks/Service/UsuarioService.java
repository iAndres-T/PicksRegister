package gm.picks.Service;

import gm.picks.Models.Usuario;
import gm.picks.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findUsuario(String username) {
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean validateLogin(Usuario usuario) {
        return false;
    }

    @Override
    public void addUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
