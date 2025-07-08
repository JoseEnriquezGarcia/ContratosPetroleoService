package com.JEnriquez.Crud;

import com.JEnriquez.Crud.DAO.IFacturaDAO;
import com.JEnriquez.Crud.JPA.Factura;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.Service.FacturaService;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FacturaTest {

    @Mock
    private IFacturaDAO iFacturaDAO;

    @InjectMocks
    private FacturaService facturaService;

    @Test
    public void testAddFactura() throws ParseException {
        List<Factura> facturas = new ArrayList<>();
        Factura factura = new Factura();
        factura.setIdFactura(1);
        String fecha = "16/05/2024";
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dateUtil = formato.parse(fecha);
        Date fechaSql = new Date(dateUtil.getTime());
        factura.setFecha(fechaSql);
        factura.setNominadaRecepcion(55478.21);
        factura.setAsignadaRecepcion(5421.20);
        factura.setNominadaEntrega(8745.21);
        factura.setAsignadaEntrega(5487);
        factura.setExcesoFirme(58);
        factura.setUsoInterrumpible(896);
        factura.setGasExceso(0);
        factura.setCargoUso(0);
        factura.setCargoGasExceso(54);
        factura.setTotalFactura(547);
        facturas.add(factura);

        Mockito.when(iFacturaDAO.saveAll(facturas)).thenReturn(facturas);
        Result result = facturaService.AddFactura(facturas);

        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNull(result.object, "result.object viene null");
        Assertions.assertNull(result.objects, "result.objects contiene datos");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
        Mockito.verify(iFacturaDAO, Mockito.atLeast(1)).saveAll(facturas);
    }
}
