package gm.picks.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(unique = true, nullable = false)
    String userName;
    String password;
    Double saldoActual;
    Double capitalInvertido;
    @Column(nullable = true, columnDefinition = "FLOAT DEFAULT 0")
    Double totalRetiros = 0.0;
    Double saldoInicialMes;
    Double saldoInicialHistory;
    String mesActual;
    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "id")
    Rol rol;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true, updatable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    Date fechaCreacion;
}
