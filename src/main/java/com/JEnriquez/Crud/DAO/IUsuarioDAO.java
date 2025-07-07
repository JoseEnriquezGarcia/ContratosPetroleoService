package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<Usuario, Integer>{
    
}
