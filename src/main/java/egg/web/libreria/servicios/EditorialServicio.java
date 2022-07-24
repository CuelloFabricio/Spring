package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.repositorios.EditorialRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EditorialServicio {

    @Autowired
    private EditorialRepository er;
    @Autowired
    private LibroServicio ls;

    @Transactional()
    public Editorial save(String nombre) throws Exception {
        validator(nombre);
        if (er.findByName(nombre) != null) {
            throw new Exception("Ya existe esa Editorial");
        }
        Editorial e = new Editorial();
        e.setNombre(nombre);
        return er.save(e);
    }

    public void validator(String nombre) throws Exception {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new Exception("Necesita un nombre");
        }
    }

    @Transactional()
    public void darBaja(Editorial l) throws Exception {
        if (l == null) {
            throw new Exception("Necesita una Editorial");
        }
        validator(l.getNombre());
        if (l.getAlta()) {
            l.setAlta(false);
        } else {
            l.setAlta(true);
        }
        er.save(l);
    }

    @Transactional()
    public Editorial edit(String id, String nombre) throws Exception {
        Optional<Editorial> respuesta = er.findById(id);
        validator(nombre);
        if (respuesta.isPresent()) {
            Editorial l = respuesta.get();
            l.setNombre(nombre);
            return er.save(l);
        } else {
            throw new Exception("La editorial no Existe");
        }
    }

    @Transactional()
    public Editorial encontrarId(String id) throws Exception {
        Optional<Editorial> respuesta = er.findById(id);
        if (respuesta.isPresent()) {
            return respuesta.get();
        } else {
            throw new Exception("La Editorial no existe");
        }
    }

    @Transactional(readOnly = true)
    public List<Editorial> findAll() throws Exception {
        return er.findAll();
    }

    @Transactional()
    public void Eliminar(Editorial l) throws Exception {
        if (l == null) {
            throw new Exception("Necesita una Editorial");
        }
        validator(l.getNombre());
        List<Libro> m = ls.findByPublisherName(l.getNombre());
        for (Libro libro : m) {
            ls.Eliminar(libro);
        }
        er.delete(l);
    }

    @Transactional(readOnly = true)
    public Editorial findByName(String nombre) throws Exception {
        validator(nombre);
        if (er.findByName(nombre) == null) {
            throw new Exception("No existe esa editorial");
        }
        return er.findByName(nombre);
    }

}
