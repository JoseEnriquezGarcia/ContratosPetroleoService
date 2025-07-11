package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IFacturaDAO;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.Result;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/factura")
public class FacturaRestController {

    @Autowired
    private IFacturaDAO iFacturaDAO;

    @GetMapping
    @Transactional
    public ResponseEntity GetAll(@RequestParam int numeroPagina, @RequestParam int tamanio) {
        Result result = new Result();
        Pageable pageable = PageRequest.of(numeroPagina, tamanio);
        
        try {
            Page<Factura> pageFactura = iFacturaDAO.findAll(pageable);
            result.objects = pageFactura.getContent();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if (result.correct == true) {
            if (result.objects.isEmpty()) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }
    
    @GetMapping("/byId/{IdFactura}")
    public ResponseEntity GetFacturaById(@PathVariable int IdFactura){
        Result result = new Result();
        try {
            result.object = iFacturaDAO.findById(IdFactura).orElseThrow();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        if (result.correct == true) {
            if (result.object == null) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            return ResponseEntity.badRequest().body(result.errorMessage);
        }
    }
}
