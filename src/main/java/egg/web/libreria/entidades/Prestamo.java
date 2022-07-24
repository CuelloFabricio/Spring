package egg.web.libreria.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Prestamo {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @Temporal(TemporalType.DATE)
    private Date fechaprestamo;
    @Temporal(TemporalType.DATE)
    private Date fechadevolucion;
    private Boolean alta = true;
    @OneToOne
    private Libro libro;
    @OneToOne
    private Cliente cliente;

    public Prestamo() {
    }

    public Prestamo(Date fechaPrestamo, Date fechaDevolucion, Libro libro, Cliente cliente) {
        this.fechaprestamo = fechaPrestamo;
        this.fechadevolucion = fechaDevolucion;
        this.libro = libro;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaPrestamo() {
        return fechaprestamo;
    }

    public void setFechaPrestamo(Date fechaPrestamo) {
        this.fechaprestamo = fechaPrestamo;
    }

    public Date getFechaDevolucion() {
        return fechadevolucion;
    }

    public void setFechaDevolucion(Date fechaDevolucion) {
        this.fechadevolucion = fechaDevolucion;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "Prestamo{" + "id=" + id + ", fechaPrestamo=" + fechaprestamo + ", fechaDevolucion=" + fechadevolucion + ", alta=" + alta + ", libro=" + libro + ", cliente=" + cliente + '}';
    }
    
}
