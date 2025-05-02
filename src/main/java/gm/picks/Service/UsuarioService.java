package gm.picks.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import gm.picks.Models.Usuario;
import gm.picks.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private Argon2 argon2 = Argon2Factory.create();

    @Override
    public Usuario findUsuarioById(int idUser) {
        return usuarioRepository.findById(idUser).orElse(null);
    }

    @Override
    public Usuario findUsuarioByName(String username){
        return usuarioRepository.findAll()
                .stream()
                .filter(u -> u.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String validateLogin(String userName, String password) {
        Usuario user = findUsuarioByName(userName);
        if (user != null) {
            if(argon2.verify(user.getPassword(), password.toCharArray())){
                if(consultarMes(user.getMesActual())){
                    return "redirect:/picks/Home";
                }
                return "redirect:/picks/Home-UpdateMes";
            }
            else{
                return "Invalid";
            }
        }
        else {
            return "Invalid";
        }
    }

    boolean consultarMes(String mes){
        String currentMonth = new java.text.SimpleDateFormat("MMMM").format(new java.util.Date());
        return mes.equals(currentMonth);
    }

    @Override
    public void updateSaldo(int idUser) {
        Usuario usuario = findUsuarioById(idUser);
        Object[] rendimientos = getRendimientos(usuario.getId(), 0, "");
        usuario.setSaldoActual((Double) rendimientos[1]);
        addUsuario(usuario);
    }

    @Override
    public void updateMesActual(String userName, String newMes){
        Usuario usuario = findUsuarioByName(userName);
        usuario.setMesActual(newMes);
        addUsuario(usuario);
    }

    @Override
    public Object[] getRendimientos(int userId, Integer sportId, String mes) {
        Object[] result = (Object[]) usuarioRepository.getRendimientos(userId, sportId, mes);
        return (result != null && result.length >= 2) ? result : null;
    }

    @Override
    public void addUsuario(Usuario usuario) {
        String hashedPassword = argon2.hash(2, 65536, 1, usuario.getPassword().toCharArray());
        usuario.setPassword(hashedPassword);
        usuarioRepository.save(usuario);
    }
}
