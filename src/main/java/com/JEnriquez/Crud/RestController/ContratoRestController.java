package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IFacturaDAO;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.Service.ContratoService;
import jakarta.transaction.Transactional;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contrato")
public class ContratoRestController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private IFacturaDAO iFacturaDAO;

    @GetMapping
    public ResponseEntity GetContratoByUsuario() {
        Result result = new Result();
        try {
            Result resultService = new Result();
            resultService = contratoService.GetContratoByUsuario("Pemex Transformación Industrial");
            result.objects = resultService.objects;
            if (resultService.correct == false) {
                result.correct = false;
            } else {
                result.correct = true;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getAll")
    @Transactional
    public ResponseEntity GetContratoAll() {
        Result result = new Result();
        try {
            result.objects = iFacturaDAO.facturas();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return ResponseEntity.ok(result);
    }
}
