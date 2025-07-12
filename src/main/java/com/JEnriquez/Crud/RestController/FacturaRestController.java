package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IFacturaDAO;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.Result;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity GetAllPageable(@RequestParam(defaultValue = "0") int numeroPagina, @RequestParam(defaultValue = "50") int tamanio) {
        Result result = new Result();
        Pageable pageable = PageRequest.of(numeroPagina, tamanio);

        try {
            Page<Factura> pageFactura = iFacturaDAO.findAll(pageable);
            result.objects = pageFactura.getContent();
            result.currentPage = pageFactura.getNumber();
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
    public ResponseEntity GetFacturaById(@PathVariable int IdFactura) {
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

    @PostMapping("/busquedaService")
    public ResponseEntity BusquedaDinamica(@RequestBody Factura factura) {
        Result<Factura>result = new Result();
        Result<Factura>resultBusqueda = new Result();
        try {
            result.objects = iFacturaDAO.findAll();
            resultBusqueda.objects = new ArrayList<>();
            
            result.objects = result.objects.stream()
                    .map(f -> (Factura) f)
                    .filter(f -> 
                            f.contrato.getClaveContrato().toUpperCase().contains(factura.contrato.getClaveContrato().toUpperCase())
                    )
                    .collect(Collectors.toList());
//            () -> System.out.println("");
        } catch (Exception ex) {
        }
        return null;
    }
}
