package gm.picks.Service;

import gm.picks.Models.Usuario;

public interface IUsuarioService {

    public Usuario findUsuario(int idUser);

    public boolean validateLogin(Usuario usuario);

    public void updateSaldo(int idUser);

    public Object[] getRendimientos(int userId, Integer sportId, String mes);

    public void addUsuario(Usuario usuario);
}
