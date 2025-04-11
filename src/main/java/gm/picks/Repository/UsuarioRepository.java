package gm.picks.Repository;

import gm.picks.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

  @Query(value = """
        EXEC sp_GetRendimientos 
            @UserId = :userId, 
            @SportId = :sportId, 
            @Mes = :mes
        """, nativeQuery = true)
    Object getRendimientos(
        @Param("userId") Integer userId, 
        @Param("sportId") Integer sportId, 
        @Param("mes") String mes
    );
}
