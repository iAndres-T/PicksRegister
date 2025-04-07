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
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String name;
    @ManyToOne
    @JoinColumn(name = "idCountry", referencedColumnName = "id")
    Country country;
    @ManyToOne
    @JoinColumn(name = "idSport", referencedColumnName = "id")
    Sport sport;

}
