package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IZonaDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.UGTP_TBL_Contrato;
import com.JEnriquez.Crud.JPA.UGTP_TBL_NodoComercialEntrega;
import com.JEnriquez.Crud.JPA.UGTP_TBL_NodoComercialRecepcion;
import com.JEnriquez.Crud.JPA.UGTP_TBL_Zona;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private IZonaDAO iZonaDAO;

    @PostMapping
    public ResponseEntity CargaMasiva(@RequestParam MultipartFile archivo) throws IOException {
        Result result = new Result();

        if (archivo != null && !archivo.isEmpty()) {
            String root = System.getProperty("user.dir");
            String path = "src/main/resources/static/archivos";
            String absolutePath = root + "/" + path + "/" + "/" + archivo.getOriginalFilename();
            archivo.transferTo(new File(absolutePath));

            List<UGTP_TBL_NodoComercialRecepcion> listaNodoRecepcion = new ArrayList<>();

            listaNodoRecepcion = lecturaArchivoExcel(new File(absolutePath));

//            procesar(listaNodoRecepcion);
            result.correct = true;
        } else {
            result.correct = false;
        }

        return ResponseEntity.ok().body(result);
    }

    public ResponseEntity procesar(List<UGTP_TBL_Zona> listaZonas) {
        Result result = new Result();
        try {
            List<UGTP_TBL_Zona> listaZona = new ArrayList<>();
            listaZona = listaZonas;

            iZonaDAO.saveAll(listaZona);

            result.correct = true;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            return ResponseEntity.internalServerError().body(result);
        }
    }

    public List<UGTP_TBL_NodoComercialRecepcion> lecturaArchivoExcel(File archivo) {
        Set<String> contratoUnico = new HashSet<>();
        Set<String> nodoUnicoRecepcion = new HashSet<>();
        Set<String> nodoUnicoEntrega = new HashSet<>();
        Set<String> zonaUnico = new HashSet<>();

        List<UGTP_TBL_NodoComercialRecepcion> listaNodosRecepcion = new ArrayList<>();
        List<UGTP_TBL_NodoComercialEntrega> listaNodosEntrega = new ArrayList<>();
        List<UGTP_TBL_Zona> listaZonas = new ArrayList<>();
        List<UGTP_TBL_Contrato> listaContratos = new ArrayList<>();

        try (XSSFWorkbook woorkbook = new XSSFWorkbook(archivo)) {
            for (Sheet sheet : woorkbook) {
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    
                    UGTP_TBL_NodoComercialRecepcion nodoComercialRecepcion = new UGTP_TBL_NodoComercialRecepcion();
                    String nodoRecepcionNombre = row.getCell(3).getStringCellValue();

                    if (!nodoUnicoRecepcion.contains(nodoRecepcionNombre)) {
                        nodoUnicoRecepcion.add(nodoRecepcionNombre);
                        nodoComercialRecepcion.setClaveNodo(nodoRecepcionNombre);
                        nodoComercialRecepcion.setDescripcion(row.getCell(4).toString());
                        listaNodosRecepcion.add(nodoComercialRecepcion);
                    }

                    UGTP_TBL_NodoComercialEntrega nodoEntregaComercial = new UGTP_TBL_NodoComercialEntrega();
                    String nodoEntregaNombre = row.getCell(5).getStringCellValue();

                    if (!nodoUnicoEntrega.contains(nodoEntregaNombre)) {
                        nodoUnicoEntrega.add(nodoEntregaNombre);
                        nodoEntregaComercial.setClaveNodo(nodoEntregaNombre);
                        nodoEntregaComercial.setDescipcion(row.getCell(6).toString());
                        listaNodosEntrega.add(nodoEntregaComercial);
                    }

                    UGTP_TBL_Zona zona = new UGTP_TBL_Zona();
                    String zonaInyeccion = row.getCell(7).getStringCellValue();
                    String zonaExtraccion = row.getCell(8).getStringCellValue();

                    if (!zonaUnico.contains(zonaInyeccion)) {
                        zonaUnico.add(zonaInyeccion);
                        zona.setZonaClave(zonaInyeccion);
                        listaZonas.add(zona);

                    } else if (!zonaUnico.contains(zonaExtraccion)) {
                        zonaUnico.add(zonaExtraccion);
                        zona.setZonaClave(zonaExtraccion);
                        listaZonas.add(zona);
                    }

                    UGTP_TBL_Contrato contrato = new UGTP_TBL_Contrato();
                    String contratoClave = row.getCell(1).getStringCellValue();

                    if (!contratoUnico.contains(contratoClave)) {
                        contratoUnico.add(contratoClave);
                        contrato.setClaveContrato(contratoClave);
                        listaContratos.add(contrato);
                    }
                }
            }

        } catch (Exception ex) {
            listaNodosRecepcion = null;
        }
        return listaNodosRecepcion;
    }

}
