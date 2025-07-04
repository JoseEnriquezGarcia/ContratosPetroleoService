package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.UGTP_TBL_Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaDAO extends JpaRepository<UGTP_TBL_Factura, Integer>{
    
}
