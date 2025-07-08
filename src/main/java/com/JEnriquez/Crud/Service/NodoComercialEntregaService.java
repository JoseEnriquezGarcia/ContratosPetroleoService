package com.JEnriquez.Crud.Service;

import com.JEnriquez.Crud.DAO.INodoComercialEntregaDAO;
import com.JEnriquez.Crud.JPA.NodoComercialEntrega;
import com.JEnriquez.Crud.JPA.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NodoComercialEntregaService {

    @Autowired
    private INodoComercialEntregaDAO iNodoComercialEntrega;

    public Result AddNodoComercialEntrega(List<NodoComercialEntrega> nodosComercialesEntrega) {
        Result result = new Result();
        try {
            iNodoComercialEntrega.saveAll(nodosComercialesEntrega);
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
            NodoComercialEntrega nodoRecepcion = iNodoComercialEntrega.findByclaveNodo(claveNodo).orElseThrow();
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
