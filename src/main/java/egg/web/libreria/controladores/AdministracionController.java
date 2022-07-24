package egg.web.libreria.controladores;

import egg.web.libreria.entidades.Autor;
import egg.web.libreria.entidades.Editorial;
import egg.web.libreria.entidades.Libro;
import egg.web.libreria.servicios.AutorServicio;
import egg.web.libreria.servicios.EditorialServicio;
import egg.web.libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/administracion")
public class AdministracionController {

    @Autowired
    private AutorServicio as;
    @Autowired
    private EditorialServicio es;
    @Autowired
    private LibroServicio ls;

    @GetMapping
    public String index(){
        return "redirect:/administracion/libro";
    }
    @GetMapping("/libro")
    public String administradorLibro(ModelMap modelo) throws Exception {
        List<Editorial> editoriales = es.findAll();
        modelo.addAttribute("editoriales", editoriales);
        List<Autor> autores = as.findAll();
        modelo.addAttribute("autores", autores);
        return "administracionLibro.html";
    }

    @PostMapping("/libro")
    public String registroLibro(ModelMap modelo, @RequestParam String titulo, @RequestParam Long isbn, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String autor, @RequestParam String editorial) throws Exception {
        try {
            Autor a = as.findByName(autor);
            Editorial e = es.findByName(editorial);
            ls.save(isbn, titulo, anio, ejemplares, a, e);
            modelo.put("exito", "Se a Completado con exito");
            return "administracionLibro.html";
        } catch (Exception e) {
            modelo.put("error", "Faltan datos");
            List<Editorial> editoriales = es.findAll();
            modelo.addAttribute("editoriales", editoriales);
            List<Autor> autores = as.findAll();
            modelo.addAttribute("autores", autores);
            return "administracionLibro.html";
        }
    }

    @GetMapping("/autor")
    public String administradorAutor() {
        return "administracionAutor.html";
    }

    @PostMapping("/autor")
    public String registroAutor(ModelMap modelo, @RequestParam String autorNombre) throws Exception {
        try {
            as.save(autorNombre);
            modelo.put("exito", "Se a Completado con exito");
            return "administracionAutor.html";
        } catch (Exception e) {
            modelo.put("error", "Faltan datos");
            return "administracionAutor.html";
        }
    }

    @GetMapping("/editorial")
    public String administradorEditorial() {
        return "administradorEditorial.html";
    }

    @PostMapping("/editorial")
    public String registroEditorial(ModelMap modelo, @RequestParam String editorialNombre) throws Exception {
        try {
            es.save(editorialNombre);
            modelo.put("exito", "Se a Completado con exito");
            return "administradorEditorial.html";
        } catch (Exception e) {
            modelo.put("error", "Faltan datos");
            return "administradorEditorial.html";
        }
    }

    @GetMapping("/autor/{id}")
    public String modificarAutor(@PathVariable String id, ModelMap modelo) throws Exception {
        modelo.put("autor", as.encontrarId(id));
        return "modifAutor.html";
    }

    @PostMapping("/autor/{id}")
    public String modifAutor(@PathVariable String id, ModelMap modelo, @RequestParam String autorNombre) throws Exception {
        try {
            as.edit(id, autorNombre);
            modelo.put("exito", "Se a Completado con exito");
            return "redirect:/autores";
        } catch (Exception e) {
            modelo.put("error", "Faltan datos");
            modelo.put("autor", as.encontrarId(id));
            return "modifAutor.html";
        }
    }

    @GetMapping("/editorial/{id}")
    public String modificarEditorial(@PathVariable String id, ModelMap modelo) throws Exception {
        modelo.put("editorial", es.encontrarId(id));
        return "modifEditorial.html";
    }

    @PostMapping("/editorial/{id}")
    public String modifEditorial(@PathVariable String id, ModelMap modelo, @RequestParam String editorialNombre) throws Exception {
        try {
            es.edit(id, editorialNombre);
            modelo.put("exito", "Se a Completado con exito");
            return "redirect:/editoriales";
        } catch (Exception e) {
            modelo.put("error", "Faltan datos");
            modelo.put("editorial", es.encontrarId(id));
            return "modifEditorial.html";
        }
    }

    @GetMapping("/libro/{id}")
    public String modificarLibro(@PathVariable String id, ModelMap modelo) throws Exception {
        modelo.put("libro", ls.encontrarId(id));
        List<Editorial> editoriales = es.findAll();
        modelo.addAttribute("editoriales", editoriales);
        List<Autor> autores = as.findAll();
        modelo.addAttribute("autores", autores);
        return "modifLibros.html";
    }

    @PostMapping("/libro/{id}")
    public String modifLibro(@PathVariable String id, ModelMap modelo, @RequestParam String titulo, @RequestParam Long isbn, @RequestParam Integer anio, @RequestParam Integer ejemplares, @RequestParam String autor, @RequestParam String editorial) throws Exception {
        try {
            Autor a = as.findByName(autor);
            Editorial e = es.findByName(editorial);
            Libro l = ls.encontrarId(id);
            Integer restantes = ejemplares - l.getEjemplaresPrestados();
            ls.edit(id, isbn, titulo, anio, ejemplares, l.getEjemplaresPrestados(), restantes, a, e);
            return "redirect:/libros";
        } catch (Exception e) {
            modelo.put("error", "Faltan datos");
            modelo.put("libro", ls.encontrarId(id));
            List<Editorial> editoriales = es.findAll();
            modelo.addAttribute("editoriales", editoriales);
            List<Autor> autores = as.findAll();
            modelo.addAttribute("autores", autores);
            return "modifLibros.html";
        }
    }
}
