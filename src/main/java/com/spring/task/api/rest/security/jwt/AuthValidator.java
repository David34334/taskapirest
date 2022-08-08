package com.spring.task.api.rest.security.jwt;

import com.spring.task.api.rest.exceptions.Unauthorized;
import com.spring.task.api.rest.models.dto.AuthUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthValidator {

    private static final String CLIENT_CREDENTIALS="client_credentials";

    @Autowired
    private JwtIO jwtIO;

    /** Metodo para validar que el token esté vigente */
    public void validarToken(String token) throws Unauthorized {
        boolean res = jwtIO.validateToken(token);
        if (res) {
            message("Ocurrio un error al validar el token");
        }
    }

    /** Metodo para validar que el grant_type sea correcto y que las credenciales no esten vacias */
    public void validateGrantType(AuthUserDTO authUserDTO, String grantType) throws Unauthorized {
        if(grantType.isEmpty() || !grantType.equals(CLIENT_CREDENTIALS)){
            message("El campo gran_type no es valido");
        }

        if(authUserDTO.getEmail().isEmpty() || authUserDTO.getPassword().isEmpty()){
            message("Usuario y/o contraseña incorrectos");
        }
    }

    /** Metodo para retornar mensaje de error
     * @return*/
    public ResponseEntity<Object> message(String message) throws Unauthorized {
        throw new Unauthorized(message);
    }

}
