package gm.picks.Service;

import gm.picks.Models.Pick;
import gm.picks.Models.Usuario;

public interface IUsuarioService {

    public Usuario findUsuario(int idUser);

    public boolean validateLogin(Usuario usuario);

    public void updateSaldo(Pick pick);

    public void addUsuario(Usuario usuario);
}
