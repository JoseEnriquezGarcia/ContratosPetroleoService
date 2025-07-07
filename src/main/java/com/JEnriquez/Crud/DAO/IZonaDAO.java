package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Zona;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IZonaDAO extends JpaRepository<Zona, Integer>{
    Optional<Zona> findByzonaClave(String ZonaClave);
}
