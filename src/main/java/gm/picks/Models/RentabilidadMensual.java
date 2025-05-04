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

    @Column(nullable = true, columnDefinition = "FLOAT DEFAULT 0")
    private Double ganancia = 0.0;

    @Column(nullable = true, columnDefinition = "FLOAT DEFAULT 0")
    private Double porcentaje = 0.0;

    @Column(nullable = false)
    Double saldoMes;

    @Column(nullable = false)
    String mes;

    @Column(nullable = false)
    String anio;
}