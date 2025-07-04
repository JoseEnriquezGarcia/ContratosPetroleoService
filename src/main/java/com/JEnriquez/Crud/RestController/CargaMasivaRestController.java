package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.IZonaDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.UGTP_TBL_Zona;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Cell;
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

            List<UGTP_TBL_Zona> listaZonas = new ArrayList<>();

            listaZonas = lecturaArchivoExcel(new File(absolutePath));

            procesar(listaZonas);
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
            
            for (UGTP_TBL_Zona zona : listaZonas) {
                iZonaDAO.save(zona);
            }
            result.correct = true;
            return ResponseEntity.ok(result);
        } catch (Exception ex) {
            result.correct = false;
            return ResponseEntity.internalServerError().body(result);
        }
    }

    public List<UGTP_TBL_Zona> lecturaArchivoExcel(File archivo) {
        List<String> listZonas = new ArrayList<>();
        List<String> listaZonasFiltradas = new ArrayList<>();
        List<UGTP_TBL_Zona> listaZonaSinDuplicados = new ArrayList<>();
        try (XSSFWorkbook woorkbook = new XSSFWorkbook(archivo)) {
            for (Sheet sheet : woorkbook) {
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    for (Cell cell : row) {
                        listZonas.add(cell.toString());
                    }
                }
            }

            listaZonasFiltradas = listZonas.stream()
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            //Guarda las zonas
            for (String zonas : listaZonasFiltradas) {
                UGTP_TBL_Zona zona = new UGTP_TBL_Zona();
                zona.setZonaClave(zonas);
                listaZonaSinDuplicados.add(zona);
            }

        } catch (Exception ex) {

        }
        return listaZonaSinDuplicados;
    }

}
