package Essentials.demo.service.impl;

import Essentials.demo.domain.Evento;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Essentials.demo.dao.EventoDao;
import Essentials.demo.service.EventoService;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoDao eventoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Evento> getEventos() {
        var lista = eventoDao.findAll();
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Evento getEvento(Evento evento) {
        return eventoDao.findById(evento.getId()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Evento product) {
        eventoDao.save(product);
    }

    @Override
    @Transactional
    public void delete(Evento product) {
        eventoDao.delete(product);
    }
}
