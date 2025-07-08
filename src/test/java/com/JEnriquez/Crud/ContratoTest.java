package com.JEnriquez.Crud;

import com.JEnriquez.Crud.DAO.IContratoDAO;
import com.JEnriquez.Crud.JPA.Contrato;
import com.JEnriquez.Crud.JPA.NodoComercialEntrega;
import com.JEnriquez.Crud.JPA.NodoComercialRecepcion;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Usuario;
import com.JEnriquez.Crud.Service.ContratoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ContratoTest {

    @Mock
    private IContratoDAO iContratoDAO;

    @InjectMocks
    private ContratoService contratoService;

    @Test
    public void testGetContratoByClave() {
        Contrato contrato = new Contrato();
        contrato.setIdContrato(1);
        contrato.setClaveContrato("CCCO");
        contrato.nodoComercialRecepcion = new NodoComercialRecepcion();
        contrato.nodoComercialEntrega = new NodoComercialEntrega();
        contrato.usuario = new Usuario();

        Mockito.when(iContratoDAO.findByclaveContrato("CCCO")).thenReturn(Optional.of(contrato));
        Result result = contratoService.GetContratoByClaveContrato("CCCO");

        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNotNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
        Mockito.verify(iContratoDAO, Mockito.atLeast(1)).findByclaveContrato("CCCO");
    }

    @Test
    public void testAddContrato() {
        List<Contrato> contratos = new ArrayList<>();
        Contrato contrato = new Contrato();
        contrato.setIdContrato(1);
        contrato.setClaveContrato("CCCO");
        contrato.nodoComercialRecepcion = new NodoComercialRecepcion();
        contrato.nodoComercialEntrega = new NodoComercialEntrega();
        contrato.usuario = new Usuario();

        Mockito.when(iContratoDAO.saveAll(contratos)).thenReturn(contratos);
        Result result = contratoService.AddContrato(contratos);
        
        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
        Mockito.verify(iContratoDAO, Mockito.atLeast(1)).saveAll(contratos);
    }
}
