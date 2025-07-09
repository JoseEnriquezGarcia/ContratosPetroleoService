package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Factura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface IFacturaDAO extends JpaRepository<Factura, Integer> {

    @Procedure(refCursor = true, outputParameterName = "", procedureName = "GetAll")
    List<Factura> facturas();
}
