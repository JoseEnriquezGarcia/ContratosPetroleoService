package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.UGTP_TBL_Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IContratoDAO extends JpaRepository<UGTP_TBL_Contrato, Integer>{
    
}
