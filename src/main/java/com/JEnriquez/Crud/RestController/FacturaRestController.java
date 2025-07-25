package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IFacturaDAO;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.Result;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    @GetMapping("/getByFecha")
    public ResponseEntity GetByFecha(@RequestParam String Desde, @RequestParam String Hasta) {
        Result result = new Result();
        try {
            String fechaDesde = Desde;
            SimpleDateFormat formatoDesde = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateUtilDesde = formatoDesde.parse(fechaDesde);

            String fechaHasta = Hasta;
            SimpleDateFormat formatoHasta = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date dateUtilHasta = formatoHasta.parse(fechaHasta);

            result.objects = iFacturaDAO.findByFechaBetween(dateUtilDesde, dateUtilHasta);
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

    @PostMapping("/busquedaService")
    public ResponseEntity BusquedaDinamica(@RequestBody Factura factura) {
        Result<Factura> result = new Result();
        try {
            result.objects = iFacturaDAO.findAll();

            if (!factura.contrato.getClaveContrato().equals("0")) {
                result.objects = result.objects.stream()
                        .map(c -> (Factura) c)
                        .filter(c -> c.contrato.getClaveContrato().toUpperCase().contains(factura.contrato.getClaveContrato().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!factura.contrato.usuario.getNombre().equals("0")) {
                result.objects = result.objects.stream()
                        .map(u -> (Factura) u)
                        .filter(u -> u.contrato.usuario.getNombre().toUpperCase().contains(factura.contrato.usuario.getNombre().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!factura.contrato.nodoComercialRecepcion.getClaveNodo().equals("0")) {
                result.objects = result.objects.stream()
                        .map(n -> (Factura) n)
                        .filter(n -> n.contrato.nodoComercialRecepcion.getClaveNodo().toUpperCase().contains(factura.contrato.nodoComercialRecepcion.getClaveNodo().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!factura.contrato.nodoComercialEntrega.getClaveNodo().equals("0")) {
                result.objects = result.objects.stream()
                        .map(n -> (Factura) n)
                        .filter(n -> n.contrato.nodoComercialEntrega.getClaveNodo().toUpperCase().contains(factura.contrato.nodoComercialEntrega.getClaveNodo()))
                        .collect(Collectors.toList());
            }

            if (!factura.contrato.nodoComercialRecepcion.zona.getZonaClave().equals("0")) {
                result.objects = result.objects.stream()
                        .map(z -> (Factura) z)
                        .filter(z -> z.contrato.nodoComercialRecepcion.zona.getZonaClave().toUpperCase().contains(factura.contrato.nodoComercialRecepcion.zona.getZonaClave().toUpperCase()))
                        .collect(Collectors.toList());
            }
            if (!factura.contrato.nodoComercialEntrega.zona.getZonaClave().equals("0")) {
                result.objects = result.objects.stream()
                        .map(z -> (Factura) z)
                        .filter(z -> z.contrato.nodoComercialEntrega.zona.getZonaClave().toUpperCase().contains(factura.contrato.nodoComercialEntrega.zona.getZonaClave().toUpperCase()))
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

    @GetMapping("/fecha")
    public ResponseEntity fechaMaxMin() {
        Result result = new Result();
        try {
            List<Factura> facturas = iFacturaDAO.findAll();
            Date maxDate = facturas.stream()
                    .map(f -> f.getFecha()).max(Date::compareTo).get();
            LocalDate fechaMax = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            Date minDate = facturas.stream()
                    .map(f -> f.getFecha()).min(Date::compareTo).get();
            LocalDate fechaMin = minDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            result.objects = new ArrayList();
            result.objects.add(fechaMax);
            result.objects.add(fechaMin);
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

    @PostMapping("/add")
    public ResponseEntity Add(@RequestBody Factura factura) {
        Result result = new Result();
        try {
            iFacturaDAO.save(factura);
            result.correct = true;
            result.statusCode = 200;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.statusCode = 400;
        }
        return ResponseEntity.status(result.statusCode).body(result.errorMessage);
    }
}
