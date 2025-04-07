package gm.picks.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Pick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String fecha;
    String mes;
    String year;
    @ManyToOne
    @JoinColumn(name = "idCasino", referencedColumnName = "id")
    Casino casino;
    String linea; //Over or Under
    @ManyToOne
    @JoinColumn(name = "idSport", referencedColumnName = "id")
    Sport sport;
    @ManyToOne
    @JoinColumn(name = "idCountry", referencedColumnName = "id")
    Country country;
    String equipoLocal;
    String equipoVisitante;
    String descripcion;
    String jugador;
    Double valor;
    Double cuota;
    Double unidades;
    String probabilidad;
    String riesgo;
    String resultado;
    Double pagoPick;
    Double utilidadPick;

}
