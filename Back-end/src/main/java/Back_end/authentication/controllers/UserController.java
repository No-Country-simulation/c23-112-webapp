package Back_end.authentication.controllers;

import Back_end.authentication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Transactional
    @DeleteMapping("/by-email/{email}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable String email) {
        if (!userService.existsByUserName(email)) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Usuario con email " + email + " no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        userService.deleteUser(email);

        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", "Usuario eliminado exitosamente");
        return ResponseEntity.ok(successResponse);
    }

}
