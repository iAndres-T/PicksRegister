package gm.picks.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import gm.picks.Models.*;
import gm.picks.Service.PickService;
import gm.picks.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Controller
public class PickController {
    private GenericResponse response = new GenericResponse();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    PickService pickService;
    @Autowired
    UsuarioService usuarioService;

    @RequestMapping(value = "/loadCasinos", method = RequestMethod.GET)
    @ResponseBody
    public List<Casino> loadCasinos(){
        return pickService.listCasinos();
    }

    @RequestMapping(value = "/loadSports", method = RequestMethod.GET)
    @ResponseBody
    public List<Sport> loadSports(){
        return pickService.listSports();
    }

    @RequestMapping(value = "/loadCountries", method = RequestMethod.GET)
    @ResponseBody
    public List<Country> loadCountries(){
        return pickService.listCountries();
    }

    @RequestMapping(value = "/loadDetallePicks", method = RequestMethod.GET)
    @ResponseBody
    public List<DetallePick> loadDetallePicks(int deporte){
        return pickService.listDetallePicks(deporte);
    }

    @RequestMapping(value = "/registrarPick", method = RequestMethod.POST)
    @ResponseBody
    public GenericResponse registrarPick(@RequestBody String pickModel) throws JsonProcessingException {
        try {
            Pick pick = objectMapper.readValue(pickModel, Pick.class);
            pickService.registrarPick(pick);
            if (!pick.getResultado().equals("Pendiente") && !pick.getResultado().equals("Nulo")) {
                usuarioService.updateSaldo(pick);
            }
            response.Estado = true;

        } catch (Exception e) {
            response.Estado = false;
            response.Mensaje = e.getMessage();
        }
        return response;
    }
    
    @RequestMapping(value = "/loadPicks", method = RequestMethod.GET)
    @ResponseBody
    public List<Pick> loadPicks(){
        return pickService.listPicks();
    }
}
