package gm.picks.Service;

import gm.picks.Models.Usuario;

public interface IUsuarioService {

    public Usuario findUsuario(String username);

    public boolean validateLogin(Usuario usuario);

    public void addUsuario(Usuario usuario);
}
