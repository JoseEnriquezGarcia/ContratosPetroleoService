package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IContratoDAO;
import com.JEnriquez.Crud.JPA.Contrato;
import com.JEnriquez.Crud.JPA.Result;
import java.util.stream.Collectors;
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
    public ResponseEntity BusquedaContratos(@RequestBody Contrato contrato) {
        Result<Contrato> result = new Result();
        try {
            result.objects = iContratoDAO.findAll();
            if (!contrato.getClaveContrato().equals("0")) {
                result.objects = result.objects.stream()
                        .map(c -> (Contrato) c)
                        .filter(c -> c.getClaveContrato().toUpperCase().contains(contrato.getClaveContrato().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!contrato.usuario.getNombre().equals("0")) {
                result.objects = result.objects.stream()
                        .map(u -> (Contrato) u)
                        .filter(u -> u.usuario.getNombre().toUpperCase().contains(contrato.usuario.getNombre().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!contrato.nodoComercialRecepcion.getClaveNodo().equals("0")) {
                result.objects = result.objects.stream()
                        .map(n -> (Contrato) n)
                        .filter(n -> n.nodoComercialRecepcion.getClaveNodo().toUpperCase().contains(contrato.nodoComercialRecepcion.getClaveNodo().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!contrato.nodoComercialEntrega.getClaveNodo().equals("0")) {
                result.objects = result.objects.stream()
                        .map(n -> (Contrato) n)
                        .filter(n -> n.nodoComercialEntrega.getClaveNodo().toUpperCase().contains(contrato.nodoComercialEntrega.getClaveNodo().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!contrato.nodoComercialRecepcion.zona.getZonaClave().equals("0")) {
                result.objects = result.objects.stream()
                        .map(z -> (Contrato) z)
                        .filter(z -> z.nodoComercialRecepcion.zona.getZonaClave().toUpperCase().contains(contrato.nodoComercialRecepcion.zona.getZonaClave().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!contrato.nodoComercialEntrega.zona.getZonaClave().equals("0")) {
                result.objects = result.objects.stream()
                        .map(z -> (Contrato) z)
                        .filter(z -> z.nodoComercialEntrega.zona.getZonaClave().toUpperCase().contains(contrato.nodoComercialEntrega.zona.getZonaClave().toUpperCase()))
                        .collect(Collectors.toList());
            }
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
