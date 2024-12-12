package Essentials.demo.controller;

import Essentials.demo.domain.Evento;
import Essentials.demo.domain.Item;
import Essentials.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import Essentials.demo.service.EventoService;

@Controller
public class CarritoController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private EventoService eventoService;
    
    @GetMapping("/")
    private String listado(Model model) {
        var eventos = eventoService.getEventos();
        model.addAttribute("eventos", eventos);
        return "/index";
    }

    //Para ver el carrito
    @GetMapping("/carrito/listado")
    public String inicio(Model model) {
        var items = itemService.gets();
        model.addAttribute("items", items);
        var carritoTotalVenta = 0;
        for (Item i : items) {
            carritoTotalVenta += (i.getCantidad());
        }
        model.addAttribute("carritoTotal", 
                carritoTotalVenta);
        return "/carrito/listado";
    }    
   
    //Para Agregar un producto al carrito
    @GetMapping("/carrito/agregar/{id}")
    public ModelAndView agregarItem(Model model, Item item) {
        Item item2 = itemService.get(item);
        if (item2 == null) {
            Evento evento = eventoService.getEvento(item);
            item2 = new Item(evento);
        }
        itemService.save(item2);
        var lista = itemService.gets();
        var totalCarritos = 0;
        var carritoTotalVenta = 0;
        for (Item i : lista) {
            totalCarritos += i.getCantidad();
            carritoTotalVenta += (i.getCantidad());
        }
        model.addAttribute("listaItems", lista);
        model.addAttribute("listaTotal", totalCarritos);
        model.addAttribute("carritoTotal", carritoTotalVenta);
        return new ModelAndView("/carrito/fragmentos :: verCarrito");
    }

    //Para mofificar un producto del carrito
    @GetMapping("/carrito/modificar/{id}")
    public String modificarItem(Item item, Model model) {
        item = itemService.get(item);
        model.addAttribute("item", item);
        return "/carrito/modificar";
    }

    //Para eliminar un elemento del carrito
    @GetMapping("/carrito/eliminar/{id}")
    public String eliminarItem(Item item) {
        itemService.delete(item);
        return "redirect:/carrito/listado";
    }

    //Para actualizar un producto del carrito (cantidad)
    @PostMapping("/carrito/guardar")
    public String guardarItem(Item item) {
        itemService.actualiza(item);
        return "redirect:/carrito/listado";
    }

    //Para facturar los productos del carrito... no implementado...
    @GetMapping("/facturar/carrito")
    public String facturarCarrito() {
        itemService.facturar();
        return "redirect:/";
    }
}
