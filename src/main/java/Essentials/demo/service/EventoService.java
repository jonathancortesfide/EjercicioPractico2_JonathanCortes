package Essentials.demo.service;

import Essentials.demo.domain.Evento;
import java.util.List;

public interface EventoService {
    
    // Se obtiene un listado de products en un List
    public List<Evento> getEventos();
    
    // Get a Category, based on the ID of a category
    public Evento getEvento(Evento evento);

    // Insert a new category if the category ID is empty
    // Update a category if the category ID is NOT empty
    public void save(Evento evento);

    // Delete the category that has the ID passed as a parameter
    public void delete(Evento evento);
 
}