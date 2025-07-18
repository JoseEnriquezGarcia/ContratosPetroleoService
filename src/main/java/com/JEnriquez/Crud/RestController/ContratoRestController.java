package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IContratoDAO;
import com.JEnriquez.Crud.JPA.Contrato;
import com.JEnriquez.Crud.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contrato")
public class ContratoRestController {

    @Autowired
    private IContratoDAO iContratoDAO;

    @GetMapping
    public ResponseEntity GetAllContrato() {
        Result result = new Result();
        try {
            result.objects = iContratoDAO.findAll();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if (result.correct) {
            if (result.objects.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok().body(result);
            }
        } else {
            return ResponseEntity.internalServerError().body(result.errorMessage);
        }
    }

    @GetMapping("/getContratoByUsuario")
    public ResponseEntity GetContratoByUsuario(@RequestParam String nombreUsuario) {
        Result result = new Result();
        try {
            result.objects = iContratoDAO.findByusuario(nombreUsuario);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        if (result.correct = true) {
            if (result.objects.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }
    
    @PostMapping("/busquedaContratoService")
    public ResponseEntity BusquedaContratos(@RequestBody Contrato contrato){
        Result result = new Result();
        try {
            result.objects = iContratoDAO.findAll();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        if(result.correct){
            if(!result.objects.isEmpty()){
                return ResponseEntity.ok().body(result);
            }else{
                return ResponseEntity.noContent().build();
            }
        }else{
            return ResponseEntity.internalServerError().body(result.errorMessage);
        }
    }
}
