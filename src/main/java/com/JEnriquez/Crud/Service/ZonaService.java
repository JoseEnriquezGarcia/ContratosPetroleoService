package com.JEnriquez.Crud.Service;

import com.JEnriquez.Crud.DAO.IZonaDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Zona;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZonaService {

    @Autowired
    private IZonaDAO iZonaDAO;

    public Result AddZona(List<Zona> zonas) {
        Result result = new Result();
        try {
            iZonaDAO.saveAll(zonas);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    public Result GetZonaByClaveZona(String zonaClave) {
        Result result = new Result();

        try {
            Zona zona = iZonaDAO.findByzonaClave(zonaClave).orElseThrow();
            result.object = zona.getIdZona();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
