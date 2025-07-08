package com.JEnriquez.Crud.Service;

import com.JEnriquez.Crud.DAO.IUsuarioDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioDAO iUsuarioDAO;

    public Result AddUsuario(List<Usuario> usuarios) {
        Result result = new Result();

        try {
            iUsuarioDAO.saveAll(usuarios);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
    public Result GetUsuarioByNombre(String nombre){
        Result result = new Result();
        try {
            Usuario usuario = iUsuarioDAO.findBynombre(nombre).orElseThrow();
            result.object = usuario.getIdUsuario();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
