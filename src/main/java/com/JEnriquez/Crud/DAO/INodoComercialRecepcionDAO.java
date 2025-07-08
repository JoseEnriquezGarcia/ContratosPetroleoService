package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.NodoComercialRecepcion;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INodoComercialRecepcionDAO extends JpaRepository<NodoComercialRecepcion, Integer>{
    Optional<NodoComercialRecepcion> findByclaveNodo(String ClaveNodo);
}
