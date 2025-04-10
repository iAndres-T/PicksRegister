package gm.picks.Service;

import gm.picks.Models.Pick;
import gm.picks.Models.Usuario;
import gm.picks.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return false;
    }

    @Override
    public void updateSaldo(Pick pick) {
        Usuario usuario = findUsuario(pick.getUsuario().getId());
        if (pick.getResultado().equals("Acierto")) {
            usuario.setSaldoActual(usuario.getSaldoActual() + pick.getUtilidadPick());
        } else if (pick.getResultado().equals("Perdido")) {
            usuario.setSaldoActual(usuario.getSaldoActual() - pick.getValor());
        }
        addUsuario(usuario);
    }

    @Override
    public void addUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
