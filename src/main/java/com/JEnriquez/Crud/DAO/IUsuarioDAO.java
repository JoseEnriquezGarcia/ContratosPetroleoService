package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.UGTP_TBL_Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<UGTP_TBL_Usuario, Integer>{
    
}
