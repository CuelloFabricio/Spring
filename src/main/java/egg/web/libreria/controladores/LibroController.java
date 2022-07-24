package egg.web.libreria.controladores;

import egg.web.libreria.entidades.Libro;
import egg.web.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/libros")
public class LibroController {

    @Autowired
    private LibroServicio ls;
    
    @GetMapping
    public String libro(ModelMap modelo) throws Exception{
        List<Libro> libros = ls.findAll();
            modelo.addAttribute("libros", libros);
        return "libros.html";
    }
    
    @GetMapping("/alta/{id}")
    public String daralta(@PathVariable String id) throws Exception {
        try {
            Libro l = ls.encontrarId(id);
             ls.darBaja(l);
            return "redirect:/libros";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
    
}
