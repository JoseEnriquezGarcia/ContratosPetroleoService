package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Contrato;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IContratoDAO extends JpaRepository<Contrato, Integer>{
    Optional<Contrato> findByclaveContrato(String ClaveContrato);
    
    @Query(value = "select c.* from UGTP_TBL_Contrato c inner join UGTP_TBL_Usuario u on c.idusuario = u.idusuario where u.nombre = :nombreusuario", nativeQuery = true)
    List<Contrato> findByusuario(@Param("nombreusuario") String nombre);
}
