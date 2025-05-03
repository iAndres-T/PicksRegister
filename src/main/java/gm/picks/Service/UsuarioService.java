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
        String hashedPassword = argon2.hash(2, 65536, 1, usuario.getPassword().toCharArray());
        usuario.setPassword(hashedPassword);
        usuarioRepository.save(usuario);
    }

    @Override
    public boolean actualizarBankMes(Map<String, Object> data) {
        try {
            String userName = (String) data.get("userName");
            Double capital = data.get("capital") != null ? ((Number) data.get("capital")).doubleValue() : null;
            String mes = (String) data.get("mes");
            String anio = (String) data.get("anio");
            Usuario usuario = findUsuarioByName(userName);

            RentabilidadMensual lastRent = rentabilidadMensualRepository.findAll()
                                                                .stream()
                                                                .filter(r -> r.getUsuario().getId() == usuario.getId())
                                                                .max((r1, r2) -> r1.getId() - r2.getId())
                                                                .orElse(null);
            
            
            lastRent.setId(null);
            lastRent.setMes(mes);
            lastRent.setAnio(anio);
            lastRent.setUsuario(usuario);
            lastRent.setGanancia(0.0);
            lastRent.setPorcentaje(0.0);
            usuario.setSaldoInicialMes(capital);
            if (capital != null) {

                if (lastRent.getSaldoMes() > capital) {
                    usuario.setSaldoActual(usuario.getSaldoActual() - (lastRent.getSaldoMes() - capital));
                    usuario.setTotalRetiros(usuario.getTotalRetiros() + (lastRent.getSaldoMes() - capital));
                } else {
                    usuario.setSaldoActual(usuario.getSaldoActual() + (capital - lastRent.getSaldoMes()));
                    usuario.setCapitalInvertido(usuario.getCapitalInvertido() + (capital - lastRent.getSaldoMes()));
                }

                lastRent.setSaldoMes(capital);
            }
            usuario.setMesActual(mes);
            addUsuario(usuario);
            rentabilidadMensualRepository.save(lastRent);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
