package egg.web.libreria.repositorios;

import egg.web.libreria.entidades.Libro;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, String> {

    @Query("SELECT l FROM Libro l WHERE l.isbn = :isbn")
    public Libro findByIsbn(@Param("isbn") Long isbn);

    @Query("SELECT DISTINCT a FROM Libro a WHERE a.titulo = :titulo")
    public Libro findByTitle(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE l.autor.nombre = :nombre")
    public List<Libro> findByAuthorName(@Param("nombre") String nombre);

    @Query("SELECT l FROM Libro l WHERE l.editorial.nombre = :nombre")
    public List<Libro> findByPublisherName(@Param("nombre") String nombre);
    
    @Query("SELECT l FROM Libro l WHERE l.titulo LIKE %:titulo%")
    public List<Libro> likeTitulo(@Param("titulo") String titulo);

}
