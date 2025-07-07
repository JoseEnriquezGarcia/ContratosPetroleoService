package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IUsuarioDAO;
import com.JEnriquez.Crud.DAO.IZonaDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Contrato;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.NodoComercialEntrega;
import com.JEnriquez.Crud.JPA.NodoComercialRecepcion;
import com.JEnriquez.Crud.JPA.ResultCargaMasiva;
import com.JEnriquez.Crud.JPA.Usuario;
import com.JEnriquez.Crud.JPA.Zona;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cargaMasiva")
public class CargaMasivaRestController {
    
    @Autowired
    private IZonaDAO iZonaDAO;
    
    @Autowired
    private IUsuarioDAO iUsuarioDAO;
    
    @PostMapping
    public ResponseEntity CargaMasiva(@RequestParam MultipartFile archivo) throws IOException {
        Result result = new Result();
        
        if (archivo != null && !archivo.isEmpty()) {
            String root = System.getProperty("user.dir");
            String path = "src/main/resources/static/archivos";
            String absolutePath = root + "/" + path + "/" + "/" + archivo.getOriginalFilename();
            archivo.transferTo(new File(absolutePath));
            
            ResultCargaMasiva resultCargaMasiva = new ResultCargaMasiva();
            
            resultCargaMasiva = lecturaArchivoExcel(new File(absolutePath));
            
            procesar(resultCargaMasiva);
            result.correct = true;
        } else {
            result.correct = false;
        }
        
        return ResponseEntity.ok().body(result);
    }
    
    public ResponseEntity procesar(ResultCargaMasiva resultCargaMasiva) {
        Result result = new Result();
        try {
            //Aca empiezo a juntar todos los datos que sean necesarios para llenar los catalogos
//            iZonaDAO.saveAll(resultCargaMasiva.listaZonas);
//            iUsuarioDAO.saveAll(resultCargaMasiva.listaUsuarios);
            //procesarNodoComercialRecepcion
            
            List<NodoComercialRecepcion> nodoRecepcion = resultCargaMasiva.listaNodosRecepcion;
            
            for (NodoComercialRecepcion nodoComercialRecepcion : nodoRecepcion) {
                List<Zona> zonas = nodoComercialRecepcion.getIdZonaTarifaInyeccion();
                for (Zona zonaClave : zonas) {
                    Zona zona = iZonaDAO.findByzonaClave(zonaClave.getZonaClave()).orElseThrow();
                    List<Zona> zona2 = new ArrayList<>();
                    zona2.add(zona);
                    nodoComercialRecepcion.setIdZonaTarifaInyeccion(zona2);
                }
            }
            
            result.correct = true;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            return ResponseEntity.internalServerError().body(result);
        }
    }
    
    public ResultCargaMasiva lecturaArchivoExcel(File archivo) {
        ResultCargaMasiva resultCargaMasiva = new ResultCargaMasiva();
        
        Set<String> nodoUnicoRecepcion = new HashSet<>();
        Set<String> nodoUnicoEntrega = new HashSet<>();
        Set<String> zonaUnico = new HashSet<>();
        Set<String> contratoUnico = new HashSet<>();
        Set<String> usuarioUnico = new HashSet<>();
        
        resultCargaMasiva.listaNodosRecepcion = new ArrayList<>();
        resultCargaMasiva.listaNodosEntrega = new ArrayList<>();
        resultCargaMasiva.listaZonas = new ArrayList<>();
        resultCargaMasiva.listaUsuarios = new ArrayList<>();
        resultCargaMasiva.listaContratos = new ArrayList<>();
        resultCargaMasiva.listaFacturas = new ArrayList<>();
        
        try (XSSFWorkbook woorkbook = new XSSFWorkbook(archivo)) {
            for (Sheet sheet : woorkbook) {
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    
                    String zonaInyeccion = row.getCell(7).getStringCellValue();
                    String zonaExtraccion = row.getCell(8).getStringCellValue();
                    
                    if (!zonaUnico.contains(zonaInyeccion)) {
                        Zona zona = new Zona();
                        zonaUnico.add(zonaInyeccion);
                        zona.setZonaClave(zonaInyeccion);
                        resultCargaMasiva.listaZonas.add(zona);
                        
                    } else if (!zonaUnico.contains(zonaExtraccion)) {
                        Zona zona = new Zona();
                        zonaUnico.add(zonaExtraccion);
                        zona.setZonaClave(zonaExtraccion);
                        resultCargaMasiva.listaZonas.add(zona);
                    }
                    
                    String usuarioDato = row.getCell(2).getStringCellValue();
                    
                    if (!usuarioUnico.contains(usuarioDato)) {
                        Usuario usuario = new Usuario();
                        usuarioUnico.add(usuarioDato);
                        usuario.setNombre(usuarioDato);
                        resultCargaMasiva.listaUsuarios.add(usuario);
                    }
                    
                    String nodoRecepcionNombre = row.getCell(3).getStringCellValue();
                    
                    if (!nodoUnicoRecepcion.contains(nodoRecepcionNombre)) {
                        NodoComercialRecepcion nodoRecepcionComercial = new NodoComercialRecepcion();
                        nodoUnicoRecepcion.add(nodoRecepcionNombre);
                        nodoRecepcionComercial.setClaveNodo(nodoRecepcionNombre);
                        nodoRecepcionComercial.setDescripcion(row.getCell(4).toString());
                        List<Zona> zonaNodo = resultCargaMasiva.listaZonas.stream().filter(z -> z.getZonaClave().equals(row.getCell(7).getStringCellValue())).collect(Collectors.toList());
                        nodoRecepcionComercial.setIdZonaTarifaInyeccion(zonaNodo);
                        resultCargaMasiva.listaNodosRecepcion.add(nodoRecepcionComercial);
                    }
                    
                    String nodoEntregaNombre = row.getCell(5).getStringCellValue();
                    
                    if (!nodoUnicoEntrega.contains(nodoEntregaNombre)) {
                        NodoComercialEntrega nodoEntregaComercial = new NodoComercialEntrega();
                        nodoUnicoEntrega.add(nodoEntregaNombre);
                        nodoEntregaComercial.setClaveNodo(nodoEntregaNombre);
                        nodoEntregaComercial.setDescipcion(row.getCell(6).toString());
                        List<Zona> zonaNodo = resultCargaMasiva.listaZonas.stream().filter(z -> z.getZonaClave().equals(row.getCell(8).getStringCellValue())).collect(Collectors.toList());
                        nodoEntregaComercial.setIdZonaTarifaExtraccion(zonaNodo);
                        resultCargaMasiva.listaNodosEntrega.add(nodoEntregaComercial);
                    }
                    
                    String contratoClave = row.getCell(1).getStringCellValue();
                    
                    if (!contratoUnico.contains(contratoClave)) {
                        Contrato contrato = new Contrato();
                        contratoUnico.add(contratoClave);
                        contrato.IdnodoComercialRecepcion = new NodoComercialRecepcion();
                        contrato.IdnodoComercialRecepcion.setClaveNodo(row.getCell(4).toString());
                        contrato.IdnodoComercialEntrega = new NodoComercialEntrega();
                        contrato.IdnodoComercialEntrega.setClaveNodo(row.getCell(5).toString());
                        contrato.setClaveContrato(contratoClave);
                        resultCargaMasiva.listaContratos.add(contrato);
                    }
                    
                    Factura factura = new Factura();
                    
                    List<Contrato> contratosFactura = resultCargaMasiva.listaContratos.stream().filter(c -> c.getClaveContrato().equals(row.getCell(1).getStringCellValue())).collect(Collectors.toList());
                    factura.setContrato(contratosFactura);
                    factura.setFecha(row.getCell(0).getDateCellValue());
                    factura.setNominadaRecepcion(row.getCell(9).getNumericCellValue());
                    factura.setAsignadaRecepcion(row.getCell(10).getNumericCellValue());
                    factura.setNominadaEntrega(row.getCell(11).getNumericCellValue());
                    factura.setAsignadaEntrega(row.getCell(12).getNumericCellValue());
                    factura.setGasExceso(row.getCell(13).getNumericCellValue());
                    factura.setExcesoFirme(row.getCell(14).getNumericCellValue());
                    factura.setUsoInterrumpible(row.getCell(15).getNumericCellValue());
                    factura.setCargoUso(row.getCell(16).getNumericCellValue());
                    factura.setCargoGasExceso(row.getCell(17).getNumericCellValue());
                    factura.setTotalFactura(row.getCell(18).getNumericCellValue());
                    
                    resultCargaMasiva.listaFacturas.add(factura);
                }
            }
            
        } catch (Exception ex) {
        }
        return resultCargaMasiva;
    }
    
}
