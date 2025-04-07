package gm.picks.Repository;

import gm.picks.Models.Pick;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PickRepository extends JpaRepository<Pick, Integer> {
}
