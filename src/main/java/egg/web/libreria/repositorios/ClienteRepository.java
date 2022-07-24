
package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends  JpaRepository<Cliente, String> {

}
