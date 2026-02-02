package gm.picks.Service;

import java.util.*;
import java.util.stream.Collectors;

import gm.picks.Models.Pick;
import gm.picks.Models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gm.picks.Repository.PickRepository;

@Service
public class PicksInfoService implements IPicksInfoService {

  @Autowired
  private PickRepository pickRepository;

  @Autowired
  private IUsuarioService usuarioService;

  @Override
  public Map<String, Object> getPicksInfo(Integer sportId, String mes, String year) {
    List<Pick> allPicks = pickRepository.findAll();

    // Filter
    List<Pick> filteredPicks = allPicks.stream()
        .filter(p -> sportId == null || (p.getSport() != null && p.getSport().getId().equals(sportId)))
        .filter(p -> mes == null || mes.isEmpty() || (p.getMes() != null && p.getMes().equalsIgnoreCase(mes)))
        .filter(p -> year == null || year.isEmpty() || (p.getYear() != null && p.getYear().equals(year)))
        .collect(Collectors.toList());

    Map<String, Object> response = new HashMap<>();

    // Stats
    response.put("stats", calculateStats(filteredPicks));

    // Grid Data
    response.put("gridData", calculateGridData(filteredPicks));

    return response;
  }

  private Map<String, Object> calculateStats(List<Pick> picks) {
    Map<String, Object> stats = new HashMap<>();
    if (picks.isEmpty())
      return stats;

    // Player Stats
    Map<String, Double> playerProfit = picks.stream()
        .filter(p -> p.getJugador() != null && !p.getJugador().isEmpty())
        .collect(Collectors.groupingBy(Pick::getJugador,
            Collectors.summingDouble(p -> p.getUtilidadPick() != null ? p.getUtilidadPick() : 0.0)));

    if (!playerProfit.isEmpty()) {
      stats.put("mostProfitablePlayer", getEntry(playerProfit, true));
      stats.put("leastProfitablePlayer", getEntry(playerProfit, false));
    }

    // Market Stats (descripcion)
    Map<String, Double> marketProfit = picks.stream()
        .filter(p -> p.getDescripcion() != null && !p.getDescripcion().isEmpty())
        .collect(Collectors.groupingBy(Pick::getDescripcion,
            Collectors.summingDouble(p -> p.getUtilidadPick() != null ? p.getUtilidadPick() : 0.0)));

    if (!marketProfit.isEmpty()) {
      stats.put("mostProfitableMarket", getEntry(marketProfit, true));
      stats.put("leastProfitableMarket", getEntry(marketProfit, false));
    }

    return stats;
  }

  private Map<String, Object> getEntry(Map<String, Double> map, boolean max) {
    Map.Entry<String, Double> entry = max ? Collections.max(map.entrySet(), Map.Entry.comparingByValue())
        : Collections.min(map.entrySet(), Map.Entry.comparingByValue());
    Map<String, Object> result = new HashMap<>();
    result.put("name", entry.getKey());
    result.put("value", entry.getValue());
    return result;
  }

  private List<Map<String, Object>> calculateGridData(List<Pick> picks) {
    Map<String, List<Pick>> picksByDate = picks.stream()
        .filter(p -> p.getFecha() != null)
        .collect(Collectors.groupingBy(Pick::getFecha));

    List<Map<String, Object>> gridData = new ArrayList<>();

    for (Map.Entry<String, List<Pick>> entry : picksByDate.entrySet()) {
      String date = entry.getKey();
      List<Pick> dailyPicks = entry.getValue();

      int total = dailyPicks.size();
      long won = dailyPicks.stream().filter(p -> "Acierto".equalsIgnoreCase(p.getResultado())).count();
      long lost = dailyPicks.stream().filter(p -> "Perdido".equalsIgnoreCase(p.getResultado())).count();
      long voided = dailyPicks.stream().filter(p -> "Nulo".equalsIgnoreCase(p.getResultado())).count();

      double wagered = dailyPicks.stream().mapToDouble(p -> p.getValor() != null ? p.getValor() : 0.0).sum();
      double profit = dailyPicks.stream().mapToDouble(p -> p.getUtilidadPick() != null ? p.getUtilidadPick() : 0.0)
          .sum();

      Usuario usuario = dailyPicks.get(0).getUsuario();
      String pickMes = dailyPicks.get(0).getMes();
      String pickYear = dailyPicks.get(0).getYear();

      Double saldoInicialMes = usuarioService.getSaldoInicialMes(usuario, pickMes, pickYear);
      double profit = (saldoInicialMes != null && saldoInicialMes != 0) ? (profit / saldoInicialMes) * 100 : 0.0;

      Map<String, Object> row = new HashMap<>();
      row.put("fecha", date);
      row.put("totalPicks", total);
      row.put("totalWon", won);
      row.put("totalLost", lost);
      row.put("totalVoid", voided);
      row.put("totalWagered", Math.round(wagered * 100.0) / 100.0);
      row.put("totalProfit", Math.round(profit * 100.0) / 100.0);
      row.put("profit", Math.round(profit * 100.0) / 100.0);

      gridData.add(row);
    }

    // Sort by date descending
    gridData.sort((a, b) -> ((String) b.get("fecha")).compareTo((String) a.get("fecha")));

    return gridData;
  }
}
