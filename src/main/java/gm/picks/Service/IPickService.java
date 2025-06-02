package gm.picks.Service;

import gm.picks.Models.*;

import java.util.List;

public interface IPickService {

    public List<Casino> listCasinos();

    public List<Sport> listSports();

    public List<Country> listCountries();

    public List<DetallePick> listDetallePicks(int deporte);

    public List<Pick> listPicks();

    public void registrarPick(Pick pick);

    public void guardarRentabilidad(RentabilidadMensual rentabilidadMensual);
}
