package com.JEnriquez.Crud.Service;

import com.JEnriquez.Crud.DAO.IFacturaDAO;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaService {
    @Autowired
    private IFacturaDAO iFacturaDAO;
    
    public Result AddFactura(List<Factura> facturas){
        Result result = new Result();
        try {
            iFacturaDAO.saveAll(facturas);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
