package gm.picks.Controllers;

import gm.picks.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @Autowired
    UsuarioService usuarioService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String Home(ModelMap modelo){
        modelo.put("Content", "Home.jsp");
        modelo.put("Script", "Home.js");
        modelo.put("Usuario", usuarioService.findUsuario("iAndresT"));
        return "Layout";
    }
}
