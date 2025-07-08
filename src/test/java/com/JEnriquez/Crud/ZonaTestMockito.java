package com.JEnriquez.Crud;

import com.JEnriquez.Crud.DAO.IZonaDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Zona;
import com.JEnriquez.Crud.Service.ZonaService;
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
public class ZonaTestMockito {
    
    @Mock
    private IZonaDAO iZonaDAO;
    
    @InjectMocks
    private ZonaService zonaService;
    
    @Test
    public void testFindByClaveZona(){
        Zona zona = new Zona();
        zona.setIdZona(1);
        zona.setZonaClave("Zona 1");
        
        Mockito.when(iZonaDAO.findByzonaClave("Zona 1")).thenReturn(Optional.of(zona));
        
        Result result = zonaService.GetZonaByClaveZona("Zona 1");
        
        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNotNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
    }
    
    @Test
    public void testAddZona(){
        List<Zona> zonas = new ArrayList<>();
        Zona zona = new Zona();
        zona.setIdZona(1);
        zona.setZonaClave("Zona 1");
        zonas.add(zona);
        
        Mockito.when(iZonaDAO.saveAll(zonas)).thenReturn(zonas);
        
        Result result = zonaService.AddZona(zonas);
        
        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
//        Mockito.verify(iZonaDAO, Mo)
    }
}
