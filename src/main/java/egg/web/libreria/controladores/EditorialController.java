package egg.web.libreria.controladores;

import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.servicios.EditorialServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/editoriales")
public class EditorialController {

    @Autowired
    private EditorialServicio es;

    @GetMapping
    public String editorial(ModelMap modelo) throws Exception {
        List<Editorial> editoriales = es.findAll();

        modelo.addAttribute("editoriales", editoriales);
        return "editoriales.html";
    }
    
    @GetMapping("/alta/{id}")
    public String daralta(@PathVariable String id) throws Exception {
        try {
            Editorial e = es.encontrarId(id);
             es.darBaja(e);
            return "redirect:/editoriales";
        } catch (Exception e) {
            return "redirect:/";
        }
    }
}
