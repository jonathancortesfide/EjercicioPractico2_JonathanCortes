package Essentials.demo.controller;

import org.springframework.ui.Model;
import Essentials.demo.domain.Evento;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import Essentials.demo.service.EventoService;

@Controller
@Slf4j
@RequestMapping("/evento")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @GetMapping("/listado")
    public String inicio(Model model) {
        var eventos = eventoService.getEventos();
        model.addAttribute("eventos", eventos);
        model.addAttribute("totalEventos", eventos.size());
        return "/evento/listado";
    }

    @GetMapping("/nuevo")
    public String eventoNuevo(Evento evento) {
        return "/evento/modifica";
    }

    @PostMapping("/guardar")
    public String eventoGuardar(Evento evento) {
        eventoService.save(evento);
        return "redirect:/";
    }

    @GetMapping("/eliminar/{id}")
    public String eventoEliminar(Evento evento) {
        eventoService.delete(evento);
        return "redirect:/";
    }

    @GetMapping("/modificar/{id}")
    public String eventoModificar(Evento evento, Model model) {
        evento = eventoService.getEvento(evento);
        model.addAttribute("evento", evento);
        return "/evento/modifica";
    }
}
