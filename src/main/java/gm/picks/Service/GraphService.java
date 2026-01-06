package gm.picks.Service;

import gm.picks.Models.Pick;
import gm.picks.Repository.PickRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphService {

    @Autowired
    private PickRepository pickRepository;

    public List<Pick> getPicks(Integer casinoId, Integer sportId, String mes) {
        List<Pick> all = pickRepository.findAll();
        return all.stream()
                .filter(p -> (casinoId == null || p.getCasino() == null || Objects.equals(p.getCasino().getId(), casinoId)))
                .filter(p -> (sportId == null || p.getSport() == null || Objects.equals(p.getSport().getId(), sportId)))
                .filter(p -> (mes == null || mes.isEmpty() || p.getMes() == null || p.getMes().equalsIgnoreCase(mes)))
                .collect(Collectors.toList());
    }

    // Construye una serie temporal simple de rendimiento acumulado por fecha
    public Map<String, Double> buildCumulativeByDate(List<Pick> picks) {
        Map<String, Double> map = new TreeMap<>();
        picks.stream()
                .sorted(Comparator.comparing(Pick::getFecha))
                .forEach(p -> {
                    double utilidad = p.getUtilidadPick() != null ? p.getUtilidadPick() : 0.0;
                    map.put(p.getFecha(), map.getOrDefault(p.getFecha(), 0.0) + utilidad);
                });

        // Acumular
        double acc = 0.0;
        Map<String, Double> acumulado = new LinkedHashMap<>();
        for (Map.Entry<String, Double> e : map.entrySet()) {
            acc += e.getValue();
            acumulado.put(e.getKey(), acc);
        }
        return acumulado;
    }
}
