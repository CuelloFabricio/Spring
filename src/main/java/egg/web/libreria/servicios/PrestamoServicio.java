package egg.web.libreria.servicios;

import egg.web.libreria.entidades.Cliente;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.entidades.Prestamo;
import egg.web.libreria.repositorios.PrestamoRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {
    @Autowired
    PrestamoRepository pr;
    
    public void save(Date fechaprestamo, Date fechadevolucion, Libro libro, Cliente cliente){
        
    }
    
    public void validator(Date fechaprestamo, Date fechadevolucion, Libro libro, Cliente cliente) throws Exception{
        if(fechaprestamo == null){
        
        }
        if(fechadevolucion == null){
            
        }
        if(libro == null){
            
        }
        if(cliente == null){
            
        }
    }
}

