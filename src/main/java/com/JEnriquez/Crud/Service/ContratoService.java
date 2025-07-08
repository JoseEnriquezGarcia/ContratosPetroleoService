package com.JEnriquez.Crud.Service;

import com.JEnriquez.Crud.DAO.IContratoDAO;
import com.JEnriquez.Crud.JPA.Contrato;
import com.JEnriquez.Crud.JPA.Result;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContratoService {

    @Autowired
    private IContratoDAO iContratoDAO;

    public Result AddContrato(List<Contrato> contratos) {
        Result result = new Result();
        try {
            iContratoDAO.saveAll(contratos);
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    public Result GetContratoByClaveContrato(String claveContrato) {
        Result result = new Result();
        try {
            Contrato contrato = iContratoDAO.findByclaveContrato(claveContrato).orElseThrow();
            result.object = contrato.getIdContrato();
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
