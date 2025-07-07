package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFacturaDAO extends JpaRepository<Factura, Integer>{
    
}
