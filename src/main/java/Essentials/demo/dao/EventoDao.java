package Essentials.demo.dao;

import Essentials.demo.domain.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoDao extends JpaRepository <Evento,Long> {
    
}