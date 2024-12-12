package Essentials.demo.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@Entity
@Table(name="eventos")
public class Evento implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    private String nombre;
    private String descripcion;
    private String tipo;
    private Date fecha_inicio;
    private Date fecha_fin;
    private int capacidad;

    public Evento() {
    }

    public Evento(Long id, String nombre, String descripcion, String tipo, Date fecha_inicio, Date fecha_fin, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.capacidad = capacidad;
    }

    
}