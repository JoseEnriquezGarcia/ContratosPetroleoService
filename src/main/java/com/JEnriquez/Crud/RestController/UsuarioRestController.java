package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IUsuarioDAO;
import com.JEnriquez.Crud.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioRestController {
    
    @Autowired
    private IUsuarioDAO iUsuarioDAO;
    
    @GetMapping
    public ResponseEntity GetUsuario(){
        Result result = new Result();
        try {
            result.objects = iUsuarioDAO.findAll();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if(result.correct){
            if(result.objects.isEmpty()){
                return ResponseEntity.noContent().build();
            }else{
                return ResponseEntity.ok(result);
            }
        }else{
            return ResponseEntity.internalServerError().body(result.errorMessage);
        }
    }
    
    @GetMapping("/byId/{IdUsuario}")
    public ResponseEntity GetUsuarioContratoById(@PathVariable int IdUsuario){
        Result result = new Result();
        try {
            result.object = iUsuarioDAO.findById(IdUsuario).orElseThrow();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if(result.correct){
            if(result.object != null){
                return ResponseEntity.ok(result);
            }else{
                return ResponseEntity.noContent().build();
            }
        }else{
            return ResponseEntity.internalServerError().body(result.errorMessage);
        }
    }
}
