package com.JEnriquez.Crud;

import com.JEnriquez.Crud.DAO.INodoComercialRecepcionDAO;
import com.JEnriquez.Crud.JPA.NodoComercialRecepcion;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Zona;
import com.JEnriquez.Crud.Service.NodoComercialRecepcionService;
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
public class NodoComercialRecepcionTest {

    @Mock
    private INodoComercialRecepcionDAO iNodoComercialRecepcion;

    @InjectMocks
    private NodoComercialRecepcionService nodoComercialRecepcionService;

    @Test
    public void testGetByNodoComercialRecepcion() {
        NodoComercialRecepcion nodoRecepcion = new NodoComercialRecepcion();
        nodoRecepcion.setIdNodo(1);
        nodoRecepcion.setClaveNodo("GH54887");
        nodoRecepcion.setDescripcion("AA");
        nodoRecepcion.zona = new Zona();

        Mockito.when(iNodoComercialRecepcion.findByclaveNodo("GH54887")).thenReturn(Optional.of(nodoRecepcion));
        Result result = nodoComercialRecepcionService.GetNodoRecepcionByClaveNodo("GH54887");

        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNotNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");

        Mockito.verify(iNodoComercialRecepcion, Mockito.atLeast(1)).findByclaveNodo("GH54887");
    }

    @Test
    public void testAddNodoComercial() {
        List<NodoComercialRecepcion> nodosRecepcion = new ArrayList<>();
        NodoComercialRecepcion nodoRecepcion = new NodoComercialRecepcion();
        nodoRecepcion.setIdNodo(1);
        nodoRecepcion.setClaveNodo("GH54887");
        nodoRecepcion.setDescripcion("AA");
        nodoRecepcion.zona = new Zona();
        nodosRecepcion.add(nodoRecepcion);

        Mockito.when(iNodoComercialRecepcion.saveAll(nodosRecepcion)).thenReturn(nodosRecepcion);
        Result result = nodoComercialRecepcionService.AddNodoComercialRecepcion(nodosRecepcion);

        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");

        Mockito.verify(iNodoComercialRecepcion, Mockito.atLeast(1)).saveAll(nodosRecepcion);
    }
}
