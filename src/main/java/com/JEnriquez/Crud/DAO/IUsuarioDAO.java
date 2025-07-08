package com.JEnriquez.Crud.DAO;

import com.JEnriquez.Crud.JPA.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioDAO extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findBynombre(String Nombre);
}
