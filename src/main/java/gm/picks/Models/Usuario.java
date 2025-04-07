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
    @Column(nullable = false, columnDefinition = "FLOAT DEFAULT 0")
    Double totalRetiros;
    Double saldoInicial;
    @ManyToOne
    @JoinColumn(name = "idRol", referencedColumnName = "id")
    Rol rol;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false, columnDefinition = "DATETIME DEFAULT GETDATE()")
    Date fechaCreacion;
}
