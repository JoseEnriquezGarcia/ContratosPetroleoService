package com.JEnriquez.Crud;

import com.JEnriquez.Crud.DAO.INodoComercialEntregaDAO;
import com.JEnriquez.Crud.JPA.NodoComercialEntrega;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Zona;
import com.JEnriquez.Crud.Service.NodoComercialEntregaService;
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
public class NodoComercialEntregaTest {

    @Mock
    private INodoComercialEntregaDAO iNodoComercialEntregaDAO;

    @InjectMocks
    private NodoComercialEntregaService nodoComercialEntregaService;

    @Test
    public void testGetNodoEntregaByClaveNodo() {
        NodoComercialEntrega nodoEntrega = new NodoComercialEntrega();
        nodoEntrega.setIdNodo(1);
        nodoEntrega.setClaveNodo("HGJJ04");
        nodoEntrega.setDescripcion("BB");
        nodoEntrega.zona = new Zona();

        Mockito.when(iNodoComercialEntregaDAO.findByclaveNodo("HGJJ04")).thenReturn(Optional.of(nodoEntrega));
        Result result = nodoComercialEntregaService.GetNodoRecepcionByClaveNodo("HGJJ04");

        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNotNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
        Mockito.verify(iNodoComercialEntregaDAO, Mockito.atLeast(1)).findByclaveNodo("HGJJ04");
    }

    @Test
    public void testAddNodoEntrega() {
        List<NodoComercialEntrega> nodosEntrega = new ArrayList<>();
        NodoComercialEntrega nodoEntrega = new NodoComercialEntrega();
        nodoEntrega.setIdNodo(1);
        nodoEntrega.setClaveNodo("HGJJ04");
        nodoEntrega.setDescripcion("BB");
        nodoEntrega.zona = new Zona();
        nodosEntrega.add(nodoEntrega);
        
        Mockito.when(iNodoComercialEntregaDAO.saveAll(nodosEntrega)).thenReturn(nodosEntrega);
        Result result = nodoComercialEntregaService.AddNodoComercialEntrega(nodosEntrega);
        
        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
        Mockito.verify(iNodoComercialEntregaDAO, Mockito.atLeast(1)).saveAll(nodosEntrega);
    }
}
