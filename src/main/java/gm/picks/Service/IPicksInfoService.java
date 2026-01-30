package gm.picks.Service;

import java.util.Map;

public interface IPicksInfoService {

  public Map<String, Object> getPicksInfo(Integer sportId, String mes, String year);

}
