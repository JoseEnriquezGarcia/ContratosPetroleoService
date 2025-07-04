package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.UGTP_TBL_MontosFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMontosFacturaDAO extends JpaRepository<UGTP_TBL_MontosFactura, Integer>{
    
}
