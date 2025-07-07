package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContratoDAO extends JpaRepository<Contrato, Integer>{
    
}
