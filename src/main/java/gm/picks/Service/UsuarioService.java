package gm.picks.Service;

import gm.picks.Models.Usuario;
import gm.picks.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario findUsuario(int idUser) {
        return usuarioRepository.findById(idUser).orElse(null);
    }

    @Override
    public boolean validateLogin(Usuario usuario) {
        Usuario user = usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getUserName().equals(usuario.getUserName()))
                .findFirst()
                .orElse(null);
        if (user != null) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(usuario.getPassword(), user.getPassword());
        }
        return false;
    }

    @Override
    public void updateSaldo(int idUser) {
        Usuario usuario = findUsuario(idUser);
        Object[] rendimientos = getRendimientos(usuario.getId(), 0, "");
        usuario.setSaldoActual((Double) rendimientos[1]);
        addUsuario(usuario);
    }

    @Override
    public Object[] getRendimientos(int userId, Integer sportId, String mes) {
        Object[] result = (Object[]) usuarioRepository.getRendimientos(userId, sportId, mes);
        return (result != null && result.length >= 2) ? result : null;
    }

    @Override
    public void addUsuario(Usuario usuario) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
    }
}
