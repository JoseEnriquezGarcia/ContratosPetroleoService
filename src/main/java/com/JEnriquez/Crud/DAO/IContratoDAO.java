package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Contrato;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContratoDAO extends JpaRepository<Contrato, Integer>{
    Optional<Contrato> findByclaveContrato(String ClaveContrato);
}
