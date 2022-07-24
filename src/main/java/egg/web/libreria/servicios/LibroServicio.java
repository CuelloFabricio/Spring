package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.repositorios.LibroRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepository lr;

    @Transactional()
    public Libro save(Long isbn, String titulo, Integer anio, Integer ejemplares, Autor autor, Editorial editorial) throws Exception {
        validator(isbn, titulo, anio, ejemplares, 0, ejemplares, autor, editorial);
        if (lr.findByIsbn(isbn) != null) {
            throw new Exception("Ya existe ese libro");
        }
        Libro l = new Libro();
        l.setAutor(autor);
        l.setAnio(anio);
        l.setEditorial(editorial);
        l.setEjemplares(ejemplares);
        l.setEjemplaresPrestados(0);
        l.setEjemplaresRestantes(ejemplares);
        l.setIsbn(isbn);
        l.setTitulo(titulo);
        return lr.save(l);
    }

    @Transactional()
    public Libro edit(String id, Long isbn, String titulo, Integer anio, Integer ejemplares, Integer prestados, Integer restantes, Autor autor, Editorial editorial) throws Exception {
        validator(isbn, titulo, anio, ejemplares, prestados, restantes, autor, editorial);
        Optional<Libro> respuesta = lr.findById(id);
        if (respuesta.isPresent()) {
            Libro l = respuesta.get();
            l.setAutor(autor);
            l.setAnio(anio);
            l.setEditorial(editorial);
            l.setEjemplares(ejemplares);
            l.setEjemplaresPrestados(prestados);
            l.setEjemplaresRestantes(restantes);
            l.setIsbn(isbn);
            l.setTitulo(titulo);
            return lr.save(l);
        } else {
            throw new Exception("El libro no existe");
        }
    }

    @Transactional()
    public Libro pedirPrestado(Libro l, Integer prestados) throws Exception {
        if (l == null) {
            throw new Exception("Necesita un libro");
        }
        validator(l.getIsbn(), l.getTitulo(), l.getAnio(), l.getEjemplares(), prestados, l.getEjemplaresRestantes(), l.getAutor(), l.getEditorial());
        Integer restantes = l.getEjemplares() - prestados;
        l.setEjemplaresPrestados(prestados);
        l.setEjemplaresRestantes(restantes);
        return edit(l.getId(), l.getIsbn(), l.getTitulo(), l.getAnio(), l.getEjemplares(), l.getEjemplaresPrestados(), l.getEjemplaresRestantes(), l.getAutor(), l.getEditorial());
    }

    @Transactional()
    public void darBaja(Libro l) throws Exception {
        if (l == null) {
            throw new Exception("Necesita un libro");
        }
        validator(l.getIsbn(), l.getTitulo(), l.getAnio(), l.getEjemplares(), l.getEjemplaresPrestados(), l.getEjemplaresRestantes(), l.getAutor(), l.getEditorial());
        if (l.getAlta()) {
            l.setAlta(false);
        } else {
            l.setAlta(true);
        }
        lr.save(l);
    }

    @Transactional()
    public void Eliminar(Libro l) throws Exception {
        if (l == null) {
            throw new Exception("Necesita un libro");
        }
        lr.delete(l);
    }

    public void validator(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer prestados, Integer restantes, Autor autor, Editorial editorial) throws Exception {
        if (isbn == null || isbn.toString().length() < 3) {
            throw new Exception("Error en el isbn");
        }
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new Exception("Necesita un titulo");
        }
        if (anio == null) {
            throw new Exception("Necesita un año");
        }
        if (ejemplares == null || ejemplares <= 0) {
            throw new Exception("Necesita un ejemplares");
        }
        if (prestados == null || prestados <= -1) {
            throw new Exception("Necesita prestados");
        }
        if (restantes == null || restantes <= 0 || restantes > ejemplares) {
            throw new Exception("Necesita restantes");
        }
        if (autor == null) {
            throw new Exception("Necesita un Autor");
        }
        if (editorial == null) {
            throw new Exception("Necesita una editorial");
        }
    }

    @Transactional(readOnly = true)
    public Libro encontrarId(String id) throws Exception {
        Optional<Libro> respuesta = lr.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("El libro no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<Libro> findAll() throws Exception {
        return lr.findAll();
    }

    @Transactional(readOnly = true)
    public List<Libro> findByAuthorName(String name) throws Exception {
        validatorName(name);
        if (lr.findByAuthorName(name) == null) {
            throw new Exception("No existe ese autor");
        }
        return lr.findByAuthorName(name);
    }

    @Transactional(readOnly = true)
    public List<Libro> findByPublisherName(String name) throws Exception {
        validatorName(name);
        if (lr.findByPublisherName(name) == null) {
            throw new Exception("No existe ese autor");
        }
        return lr.findByPublisherName(name);
    }

    public void validatorName(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Necesita un nombre");
        }
    }
}
