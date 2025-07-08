package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.NodoComercialEntrega;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INodoComercialEntregaDAO extends JpaRepository<NodoComercialEntrega, Integer>{
    Optional<NodoComercialEntrega> findByclaveNodo(String ClaveNodo);
}
