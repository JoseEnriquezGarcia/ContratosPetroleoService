package com.JEnriquez.Crud.RestController;

import com.JEnriquez.Crud.Configuration.JWTAuthenticationConfig;
import com.JEnriquez.Crud.JPA.ResultToken;
import com.JEnriquez.Crud.JPA.Usuario;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthRestController {

    @Autowired
    private JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/Authlogin")
    public ResponseEntity login(@RequestBody Usuario usuario) {
        ResultToken resultToken = new ResultToken();

        try {
            // Autenticamos con el AuthenticationManager
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            usuario.getUsername(),
                            usuario.getPassword()
                    )
            );

            // Usuario autenticado correctamente
            String username = authentication.getName();
            var authorities = authentication.getAuthorities();
            String rol = authorities.stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst().orElseThrow();
            
            
            Map<String, String> tokenInfo = jwtAuthenticationConfig.getJWTToken(username, authorities);

            resultToken.correct = true;
            resultToken.Message = "Creado satisfactoriamente";
            resultToken.StatusCode = 200;
            resultToken.Bearer = tokenInfo.get("token");
            resultToken.Expiration = tokenInfo.get("expiration");
            resultToken.rol = rol;

        } catch (AuthenticationException ex) {
            resultToken.correct = false;
            resultToken.Message = "Usuario o contraseña incorrectos";
            resultToken.StatusCode = 401;
        }
        return ResponseEntity.status(resultToken.StatusCode).body(resultToken);
    }
}
