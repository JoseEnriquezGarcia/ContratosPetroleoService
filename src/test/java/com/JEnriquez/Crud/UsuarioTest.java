package com.JEnriquez.Crud;

import com.JEnriquez.Crud.DAO.IUsuarioDAO;
import com.JEnriquez.Crud.JPA.Result;
import com.JEnriquez.Crud.JPA.Usuario;
import com.JEnriquez.Crud.Service.UsuarioService;
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
public class UsuarioTest {
    @Mock
    private IUsuarioDAO iUsuarioDAO;
    
    @InjectMocks
    private UsuarioService usuarioService;
    
    @Test
    public void testGetUsuarioByNombre(){
        Usuario usuario = new Usuario();
        
        usuario.setIdUsuario(1);
        usuario.setNombre("Pemex");
        
        Mockito.when(iUsuarioDAO.findBynombre("Pemex")).thenReturn(Optional.of(usuario));
        Result result = usuarioService.GetUsuarioByNombre("Pemex");
        
        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNotNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
        Mockito.verify(iUsuarioDAO, Mockito.atLeast(1)).findBynombre("Pemex");
    }
    
    @Test
    public void testAddUsuario(){
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Pemex");
        
        usuarios.add(usuario);
        
        Mockito.when(iUsuarioDAO.saveAll(usuarios)).thenReturn(usuarios);
        Result result = usuarioService.AddUsuario(usuarios);
        
        Assertions.assertNotNull(result, "Result viene null");
        Assertions.assertTrue(result.correct, "result.correct viene false");
        Assertions.assertNull(result.object, "result.object contiene datos");
        Assertions.assertNull(result.objects, "result.objects viene null");
        Assertions.assertNull(result.ex, "result.ex contiene una excepción");
        Assertions.assertNull(result.errorMessage, "result.errorMessage contiene un mensaje de error");
        
        Mockito.verify(iUsuarioDAO, Mockito.atLeast(1)).saveAll(usuarios);
    }
}
