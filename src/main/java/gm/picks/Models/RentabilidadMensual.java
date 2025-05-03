package gm.picks.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentabilidadMensual {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id", nullable = false)
    Usuario usuario;

    @Column(nullable = false)
    Double ganancia;

    @Column(nullable = false)
    Double porcentaje;

    @Column(nullable = false)
    Double saldoMes;

    @Column(nullable = false)
    String mes;

    @Column(nullable = false)
    String anio;
}