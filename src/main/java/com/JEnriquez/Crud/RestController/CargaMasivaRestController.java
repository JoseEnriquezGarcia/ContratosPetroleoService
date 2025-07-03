package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.DAO.ReportSplitter;
import com.JEnriquez.Crud.JPA.Result;
import java.io.File;
import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cargaMasiva")
public class CargaMasivaRestController {

    @PostMapping
    public ResponseEntity CargaMasiva(@RequestParam MultipartFile archivo) throws IOException{
        Result result = new Result();
        
        if(archivo != null && !archivo.isEmpty()){
            String root = System.getProperty("user.dir");
            String path = "src/main/resources/static/archivos";
            String absolutePath = root  + "/" + path + "/" + "/" + archivo.getOriginalFilename();
            archivo.transferTo(new File(absolutePath));
            new ReportSplitter(absolutePath, 6);
            
            result.correct = true;
        }else{
            result.correct = false;
        }
        
        return ResponseEntity.ok().body(result);
    }
    

}
