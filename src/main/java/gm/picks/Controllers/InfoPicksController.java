package gm.picks.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import gm.picks.Service.IPicksInfoService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class InfoPicksController {

  @Autowired
  IPicksInfoService picksInfoService;

  @RequestMapping(value = "/InfoPicks", method = RequestMethod.GET)
  public String infoPicks(Model model) {
    model.addAttribute("Content", "InfoPicks.jsp");
    model.addAttribute("Script", "InfoPicks.js");
    return "Layout";
  }

  @RequestMapping(value = "/loadPicksInfo", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, Object> loadPicksInfo(
      @RequestParam(required = false) Integer sportId,
      @RequestParam(required = false) String mes,
      @RequestParam(required = false) String year) {
    return picksInfoService.getPicksInfo(sportId, mes, year);
  }

}
