package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.INodoComercialRecepcionDAO;
import com.JEnriquez.Crud.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nodoRecepcion")
public class NodoRecepcionRestContoller {

    @Autowired
    private INodoComercialRecepcionDAO iNodoComercialRecepcionDAO;

    @GetMapping
    public ResponseEntity GetAll() {
        Result result = new Result();
        try {
            result.objects = iNodoComercialRecepcionDAO.findAll();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if (result.correct) {
            if (!result.objects.isEmpty()) {
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.noContent().build();
            }
        } else {
            return ResponseEntity.internalServerError().body(result.errorMessage);
        }
    }
}
