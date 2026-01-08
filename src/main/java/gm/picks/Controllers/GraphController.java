package gm.picks.Controllers;

import gm.picks.Service.GraphService;
import gm.picks.Repository.CasinoRepository;
import gm.picks.Repository.SportRepository;
import gm.picks.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class GraphController {

    @Autowired
    GraphService graphService;

    @Autowired
    CasinoRepository casinoRepository;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    UsuarioService usuarioService;

    @RequestMapping(value = "/Graphs", method = RequestMethod.GET)
    public String graphs(ModelMap model) {
        model.put("Content", "Graphs.jsp");
        model.put("Script", "Graphs.js");
        model.put("Casinos", casinoRepository.findAll());
        model.put("Sports", sportRepository.findAll());
        model.put("Usuario", usuarioService.findUsuarioById(1));
        return "Layout";
    }

    @RequestMapping(value = "/api/graphs/cumulative", produces = "application/json")
    @ResponseBody
    public Map<String, Double> cumulative(
            @RequestParam(required = false) Integer casinoId,
            @RequestParam(required = false) Integer sportId,
            @RequestParam(required = false) String mes
    ) {
        return graphService.buildCumulativeByDate(graphService.getPicks(casinoId, sportId, mes));
    }
}
