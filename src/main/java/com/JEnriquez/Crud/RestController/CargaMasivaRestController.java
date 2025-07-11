package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Contrato;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.NodoComercialEntrega;
import com.JEnriquez.Crud.JPA.NodoComercialRecepcion;
import com.JEnriquez.Crud.JPA.ResultCargaMasiva;
import com.JEnriquez.Crud.JPA.Usuario;
import com.JEnriquez.Crud.JPA.Zona;
import com.JEnriquez.Crud.Service.ContratoService;
import com.JEnriquez.Crud.Service.FacturaService;
import com.JEnriquez.Crud.Service.NodoComercialEntregaService;
import com.JEnriquez.Crud.Service.NodoComercialRecepcionService;
import com.JEnriquez.Crud.Service.UsuarioService;
import com.JEnriquez.Crud.Service.ZonaService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
    private ZonaService zonaService;

    @Autowired
    private NodoComercialRecepcionService nodoComercialRecepcionService;

    @Autowired
    private NodoComercialEntregaService nodoComercialEntregaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private FacturaService facturaService;

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
            zonaService.AddZona(resultCargaMasiva.listaZonas);
            usuarioService.AddUsuario(resultCargaMasiva.listaUsuarios);

            for (NodoComercialRecepcion nodoComercialRecepcion : resultCargaMasiva.listaNodosRecepcion) {
                Result<Integer> resultZona = zonaService.GetZonaByClaveZona(nodoComercialRecepcion.zona.getZonaClave());
                nodoComercialRecepcion.zona.setIdZona(resultZona.object);
            }

            for (NodoComercialEntrega nodoComercialEntrega : resultCargaMasiva.listaNodosEntrega) {
                Result<Integer> resultZona = zonaService.GetZonaByClaveZona(nodoComercialEntrega.zona.getZonaClave());
                nodoComercialEntrega.zona.setIdZona(resultZona.object);
            }
            
            nodoComercialRecepcionService.AddNodoComercialRecepcion(resultCargaMasiva.listaNodosRecepcion);
            nodoComercialEntregaService.AddNodoComercialEntrega(resultCargaMasiva.listaNodosEntrega);
            

            for (Contrato contrato : resultCargaMasiva.listaContratos) {
                Result<Integer> resultUsuario = usuarioService.GetUsuarioByNombre(contrato.usuario.getNombre());
                Result<Integer> resultNodoRecepcion = nodoComercialRecepcionService.GetNodoRecepcionByClaveNodo(contrato.nodoComercialRecepcion.getClaveNodo());
                Result<Integer> resultNodoEntrega = nodoComercialEntregaService.GetNodoRecepcionByClaveNodo(contrato.nodoComercialEntrega.getClaveNodo());

                contrato.usuario.setIdUsuario(resultUsuario.object);
                contrato.nodoComercialRecepcion.setIdNodo(resultNodoRecepcion.object);
                contrato.nodoComercialEntrega.setIdNodo(resultNodoEntrega.object);
            }

            contratoService.AddContrato(resultCargaMasiva.listaContratos);

            for (Factura factura : resultCargaMasiva.listaFacturas) {
                Result<Integer> resultFactura = contratoService.GetContratoByClaveContrato(factura.contrato.getClaveContrato());

                factura.contrato.setIdContrato(resultFactura.object);
            }

            facturaService.AddFactura(resultCargaMasiva.listaFacturas);

            result.correct = true;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            return ResponseEntity.internalServerError().body(result);
        }
    }

    public ResultCargaMasiva lecturaArchivoExcel(File archivo) {
        Result result = new Result();
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
                        nodoRecepcionComercial.zona = new Zona();
                        nodoRecepcionComercial.zona.setZonaClave(row.getCell(7).getStringCellValue());
                        resultCargaMasiva.listaNodosRecepcion.add(nodoRecepcionComercial);
                    }

                    String nodoEntregaNombre = row.getCell(5).getStringCellValue();

                    if (!nodoUnicoEntrega.contains(nodoEntregaNombre)) {
                        NodoComercialEntrega nodoEntregaComercial = new NodoComercialEntrega();
                        nodoUnicoEntrega.add(nodoEntregaNombre);
                        nodoEntregaComercial.setClaveNodo(nodoEntregaNombre);
                        nodoEntregaComercial.setDescripcion(row.getCell(6).toString());
                        nodoEntregaComercial.zona = new Zona();
                        nodoEntregaComercial.zona.setZonaClave(row.getCell(8).getStringCellValue());
                        resultCargaMasiva.listaNodosEntrega.add(nodoEntregaComercial);
                    }

                    String contratoClave = row.getCell(1).getStringCellValue();

                    if (!contratoUnico.contains(contratoClave)) {
                        Contrato contrato = new Contrato();
                        contratoUnico.add(contratoClave);
                        contrato.usuario = new Usuario();
                        contrato.usuario.setNombre(row.getCell(2).getStringCellValue());
                        contrato.nodoComercialRecepcion = new NodoComercialRecepcion();
                        contrato.nodoComercialRecepcion.setClaveNodo(row.getCell(3).toString());
                        contrato.nodoComercialEntrega = new NodoComercialEntrega();
                        contrato.nodoComercialEntrega.setClaveNodo(row.getCell(5).toString());
                        contrato.setClaveContrato(contratoClave);
                        resultCargaMasiva.listaContratos.add(contrato);
                    }

                    Factura factura = new Factura();

                    factura.contrato = new Contrato();
                    factura.contrato.setClaveContrato(row.getCell(1).getStringCellValue());
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
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return resultCargaMasiva;
    }

}
