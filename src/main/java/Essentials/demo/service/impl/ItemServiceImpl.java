package Essentials.demo.service.impl;

import Essentials.demo.dao.FacturaDao;
import Essentials.demo.dao.VentaDao;
import Essentials.demo.domain.Evento;
import Essentials.demo.domain.Usuario;
import Essentials.demo.domain.Factura;
import Essentials.demo.domain.Item;
import Essentials.demo.domain.Venta;
import Essentials.demo.service.UsuarioService;
import Essentials.demo.service.ItemService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import Essentials.demo.dao.EventoDao;

@Service
public class ItemServiceImpl implements ItemService {

    @Override
    public List<Item> gets() {
        return listaItems;
    }

    //Se usa en el addCarrito... agrega un elemento
    @Override
    public void save(Item item) {
        boolean existe = false;
        for (Item i : listaItems) {
            //Busca si ya existe el producto en el carrito
            if (Objects.equals(i.getId(), item.getId())) {
                //Valida si aún puede colocar un item adicional -segun existencias-
                if (i.getCantidad() < item.getCantidad()) {
                    //Incrementa en 1 la cantidad de elementos
                    i.setCantidad(i.getCantidad() + 1);
                }
                existe = true;
                break;
            }
        }
        if (!existe) {//Si no está el producto en el carrito se agrega cantidad =1.            
            item.setCantidad(1);
            listaItems.add(item);
        }
    }

    //Se usa para eliminar un producto del carrito
    @Override
    public void delete(Item item) {
        var posicion = -1;
        var existe = false;
        for (Item i : listaItems) {
            ++posicion;
            if (Objects.equals(i.getId(), item.getId())) {
                existe = true;
                break;
            }
        }
        if (existe) {
            listaItems.remove(posicion);
        }
    }

    //Se obtiene la información de un producto del carrito... para modificarlo
    @Override
    public Item get(Item item) {
        for (Item i : listaItems) {
            if (Objects.equals(i.getId(), item.getId())) {
                return i;
            }
        }
        return null;
    }

    //Se usa en la página para actualizar la cantidad de productos
    @Override
    public void actualiza(Item item) {
        for (Item i : listaItems) {
            if (Objects.equals(i.getId(), item.getId())) {
                i.setCantidad(item.getCantidad());
                break;
            }
        }
    }

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FacturaDao facturaDao;
    @Autowired
    private VentaDao ventaDao;
    @Autowired
    private EventoDao productoDao;

    @Override
    public void facturar() {
        System.out.println("Facturando");

        //Se obtiene el usuario autenticado
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }

        if (username.isBlank()) {
            return;
        }

        Usuario usuario = usuarioService.getUsuarioPorUsername(username);

        if (usuario == null) {
            return;
        }

        Factura factura = new Factura(usuario.getIdUsuario());
        factura = facturaDao.save(factura);

        double total = 0;
        for (Item i : listaItems) {
            Venta venta = new Venta(factura.getId(), i.getId(), i.getCantidad());
            ventaDao.save(venta);
            Evento producto = productoDao.getReferenceById(i.getId());
            producto.setCapacidad(producto.getCapacidad()-i.getCantidad());
            productoDao.save(producto);
            total += i.getCantidad();
        }
        factura.setTotal(total);
        facturaDao.save(factura);
        listaItems.clear();
    }
}