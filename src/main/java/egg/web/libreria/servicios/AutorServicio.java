package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.repositorios.AutorRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorServicio {

    @Autowired
    private AutorRepository er;
    @Autowired
    private LibroServicio ls;

    public void validator(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Necesita un nombre");
        }
    }

    @Transactional()
    public Autor save(String nombre) throws Exception {
        validator(nombre);
        if (er.findByName(nombre) != null) {
            throw new Exception("Ya existe ese autor");
        }
        Autor e = new Autor();
        e.setNombre(nombre);
        return er.save(e);
    }

    @Transactional()
    public Autor edit(String id, String nombre) throws Exception {
        Optional<Autor> respuesta = er.findById(id);
        validator(nombre);
        if (respuesta.isPresent()) {
            Autor l = respuesta.get();
            l.setNombre(nombre);
            return er.save(l);
        } else {
            throw new Exception("La editorial no Existe");
        }
    }

    @Transactional(readOnly = true)
    public Autor encontrarId(String id) throws Exception {
        Optional<Autor> respuesta = er.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("El autor no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<Autor> findAll() throws Exception {
        return er.findAll();
    }

    @Transactional
    public void darBaja(String id) throws Exception {
        Autor l = encontrarId(id);
        if (l == null) {
            throw new Exception("Necesita un Autor");
        }
        validator(l.getNombre());
        if (l.getAlta()) {
            l.setAlta(false);
        } else {
            l.setAlta(true);
        }
        er.save(l);
    }

    @Transactional(readOnly = true)
    public Autor findByName(String nombre) throws Exception {
        validator(nombre);
        if (er.findByName(nombre) == null) {
            throw new Exception("No existe ese autor");
        }
        return er.findByName(nombre);
    }

    @Transactional()
    public void Eliminar(Autor l) throws Exception {
        if (l == null) {
            throw new Exception("Necesita una Editorial");
        }
        validator(l.getNombre());
        List<Libro> m = ls.findByAuthorName(l.getNombre());
        for (Libro libro : m) {
            ls.Eliminar(libro);
        }
        er.delete(l);
    }
}
