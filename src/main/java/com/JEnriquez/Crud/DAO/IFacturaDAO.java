package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Factura;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface IFacturaDAO extends JpaRepository<Factura, Integer> {

    @Procedure(refCursor = true, procedureName = "GetAll")
    List<Factura> GetAll();

    List<Factura> findByFechaBetween(Date DesdeFecha, Date HastaFecha);

    @Query(
            value = "SELECT f.* "
            + "FROM UGTP_TBL_Factura f "
            + "INNER JOIN UGTP_TBL_Contrato c ON f.idcontrato = c.idcontrato "
            + "INNER JOIN UGTP_TBL_Usuario u ON u.idusuario = c.idusuario "
            + "WHERE u.username = :username",
            countQuery = "SELECT COUNT(*) "
            + "FROM UGTP_TBL_Factura f "
            + "INNER JOIN UGTP_TBL_Contrato c ON f.idcontrato = c.idcontrato "
            + "INNER JOIN UGTP_TBL_Usuario u ON u.idusuario = c.idusuario "
            + "WHERE u.username = :username",
            nativeQuery = true
    )
    Page<Factura> findByUsername(@Param("username") String Username, Pageable pageable);
}
