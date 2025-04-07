package gm.picks.Service;

import gm.picks.Models.*;
import gm.picks.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class PickService implements IPickService {
  
    @Autowired
    private PickRepository pickRepository;
    @Autowired
    private CasinoRepository casinoRepository;
    @Autowired
    private SportRepository sportRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private DetallePickRepository detallePickRepository;

    @Override
    public List<Casino> listCasinos() {
        return casinoRepository.findAll();
    }

    @Override
    public List<Sport> listSports() {
        return sportRepository.findAll();
    }

    @Override
    public List<Country> listCountries() {
        return countryRepository.findAll();
    }

    @Override
    public List<DetallePick> listDetallePicks(int deporte) {
        return detallePickRepository.findAll()
            .stream()
            .filter(detallePick -> detallePick.getIdDeporte() == deporte)
            .toList();
    }


    @Override
    public List<Pick> listPicks() {
        return pickRepository.findAll()
            .stream()
            .sorted(Comparator.comparing(Pick::getId).reversed()) // Orden descendente por ID
            .toList();
    }

    @Override
    public void registrarPick(Pick pick) {
        pickRepository.save(pick);
    }
}
