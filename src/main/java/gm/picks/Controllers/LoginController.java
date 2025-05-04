package gm.picks.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gm.picks.Models.Usuario;
import gm.picks.Service.UsuarioService;

import java.util.Map;

@Controller
public class LoginController {
  private ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  UsuarioService usuarioService;

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String loginPage(ModelMap modelo) {
    modelo.put("Script", "Login.js");
    return "Login";
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  @ResponseBody
  public String login(@RequestBody String usuarioModel) throws JsonProcessingException {
    try {
      Usuario usuario = objectMapper.readValue(usuarioModel, Usuario.class);
      return usuarioService.validateLogin(usuario.getUserName(), usuario.getPassword());
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }

  @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
  @ResponseBody
  public String registerUser(@RequestBody String usuarioModel) throws JsonProcessingException {
    try {
      Usuario usuario = objectMapper.readValue(usuarioModel, Usuario.class);
      return usuarioService.addUsuario(usuario);
    } catch (Exception e) {
      return "Error: " + e.getMessage();
    }
  }

  @RequestMapping(value = "/actualizarBank", method = RequestMethod.POST)
  @ResponseBody
  public String actualizarBank(@RequestBody Map<String, Object> data) {
    try {
        return usuarioService.actualizarBankMes(data);
    } catch (Exception e) {
        return e.getMessage();
    }
  }
}
