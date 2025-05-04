package gm.picks.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import gm.picks.Models.RentabilidadMensual;
import gm.picks.Models.Usuario;
import gm.picks.Repository.RentabilidadMensualRepository;
import gm.picks.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
public class UsuarioService implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RentabilidadMensualRepository rentabilidadMensualRepository;
    private Argon2 argon2 = Argon2Factory.create();

    @Override
    public Usuario findUsuarioById(int idUser) {
        return usuarioRepository.findById(idUser).orElse(null);
    }

    @Override
    public Usuario findUsuarioByName(String username) {
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
            if (argon2.verify(user.getPassword(), password.toCharArray())) {
                if (consultarMes(user.getMesActual())) {
                    return "redirect:/picks/Home";
                }
                return "redirect:/picks/Home-UpdateMes";
            } else {
                return "Invalid";
            }
        } else {
            return "Invalid";
        }
    }

    boolean consultarMes(String mes) {
        String currentMonth = new java.text.SimpleDateFormat("MMMM").format(new java.util.Date());
        currentMonth = currentMonth.substring(0, 1).toUpperCase() + currentMonth.substring(1).toLowerCase();
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
    public Object[] getRendimientos(int userId, Integer sportId, String mes) {
        Object[] result = (Object[]) usuarioRepository.getRendimientos(userId, sportId, mes);
        return (result != null && result.length >= 2) ? result : null;
    }

    @Override
    public void addUsuario(Usuario usuario) {
        if (usuario.getId() == null) {
            String hashedPassword = argon2.hash(2, 65536, 1, usuario.getPassword().toCharArray());
            usuario.setPassword(hashedPassword);           
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public boolean actualizarBankMes(Map<String, Object> data) {
        try {
            String userName = (String) data.get("userName");
            Double capital = data.get("capital") != null ? Double.parseDouble(data.get("capital").toString()) : null;
            String mes = (String) data.get("mes");
            String anio = (String) data.get("anio");
            Usuario usuario = findUsuarioByName(userName);

            RentabilidadMensual lastRent = rentabilidadMensualRepository.findAll()
                    .stream()
                    .filter(r -> Objects.equals(r.getUsuario().getId(), usuario.getId()))
                    .max((r1, r2) -> r1.getId() - r2.getId())
                    .orElse(null);

            RentabilidadMensual newRent = new RentabilidadMensual();
            newRent.setMes(mes);
            newRent.setAnio(anio);
            newRent.setUsuario(usuario);
            newRent.setSaldoMes(lastRent.getSaldoMes());
            if (capital != null) {
                
                if (lastRent.getSaldoMes() > capital) {
                    if (capital > usuario.getSaldoActual()) {
                        usuario.setCapitalInvertido(usuario.getCapitalInvertido() + (capital - usuario.getSaldoActual()));
                    }
                    else {
                        usuario.setTotalRetiros(usuario.getTotalRetiros() + (usuario.getSaldoActual() - capital));                      
                    }
                    usuario.setSaldoActual(usuario.getSaldoActual() - (usuario.getSaldoActual() - capital));
                } 
                else if (lastRent.getSaldoMes() < capital) {
                    usuario.setCapitalInvertido(usuario.getCapitalInvertido() + (capital - usuario.getSaldoActual()));
                    usuario.setSaldoActual(usuario.getSaldoActual() + (capital - usuario.getSaldoActual()));
                }
                else {
                    return false;
                }
                
                usuario.setSaldoInicialMes(capital);
                newRent.setSaldoMes(capital);
            }

            usuario.setMesActual(mes);
            rentabilidadMensualRepository.save(newRent);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
