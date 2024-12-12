
package Essentials.demo.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Item extends Evento {
    private int cantidad; //Almacenar la cantidad de items de un producto

    public Item() {
    }

    public Item(Evento producto) {
        super.setId(producto.getId());
        super.setNombre(producto.getNombre());
        super.setDescripcion(producto.getDescripcion());
        super.setTipo(producto.getTipo());
        super.setFecha_inicio(producto.getFecha_inicio());
        super.setFecha_fin(producto.getFecha_fin());
        super.setCapacidad(producto.getCapacidad());
        this.cantidad = 0;
    }
}