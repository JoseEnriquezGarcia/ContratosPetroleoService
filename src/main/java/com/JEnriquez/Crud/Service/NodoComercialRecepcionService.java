package com.JEnriquez.Crud.Service;

import com.JEnriquez.Crud.DAO.INodoComercialRecepcionDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.NodoComercialRecepcion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodoComercialRecepcionService {

    @Autowired
    INodoComercialRecepcionDAO iNodoComercialRecepcionDAO;

    public Result AddNodoComercialRecepcion(List<NodoComercialRecepcion> nodoComercialRecepcion) {
        Result result = new Result();
        try {
            iNodoComercialRecepcionDAO.saveAll(nodoComercialRecepcion);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    public Result GetNodoRecepcionByClaveNodo(String claveNodo) {
        Result result = new Result();
        try {
            NodoComercialRecepcion nodoRecepcion = iNodoComercialRecepcionDAO.findByclaveNodo(claveNodo).orElseThrow();
            result.object = nodoRecepcion.getIdNodo();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}
