package egg.web.libreria.controladores;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.servicios.AutorServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorServicio as;

    @GetMapping
    public String autor(ModelMap modelo) throws Exception {
        List<Autor> autores = as.findAll();
        modelo.addAttribute("autores", autores);
        return "Autor.html";
    }

    @GetMapping("/alta/{id}")
    public String daralta(@PathVariable String id) throws Exception {
        try {
             as.darBaja(id);
            return "redirect:/autores";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
