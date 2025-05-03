package gm.picks.Service;

import gm.picks.Models.Usuario;

import java.util.Map;

public interface IUsuarioService {

    public Usuario findUsuarioById(int idUser);

    public Usuario findUsuarioByName(String username);

    public String validateLogin(String userName, String password);

    public void updateSaldo(int idUser);

    public boolean actualizarBankMes(Map<String, Object> data);

    public Object[] getRendimientos(int userId, Integer sportId, String mes);

    public void addUsuario(Usuario usuario);
}
