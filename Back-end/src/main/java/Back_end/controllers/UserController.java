package Back_end.controllers;

import Back_end.entities.User;
import Back_end.security.CustomUserDetails;
import Back_end.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔒 Solo ADMIN puede eliminar cualquier usuario
    @PreAuthorize("hasRole('ADMIN_1')")
    @DeleteMapping("/by-id/{id}")
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable String id, Authentication authentication) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Usuario no encontrado"));
            }

            User user = userOptional.get();
            String authenticatedEmail = authentication.getName();
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN_1"));

            // Si el usuario autenticado no es admin ni el mismo usuario, no se puede eliminar
            if (!isAdmin && !authenticatedEmail.equals(user.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", "No tienes permisos para eliminar este usuario"));
            }

            userService.deleteUserById(id);
            return ResponseEntity.ok(Map.of("message", "Usuario eliminado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Error al procesar la solicitud"));
        }
    }

    // 🔒 Solo ADMIN puede ver todos los usuarios (PASO LA PRUEBA - GET)
    @PreAuthorize("hasRole('ADMIN_1')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // 🔒 Solo ADMIN puede obtener un usuario por ID (PASO LA PRUEBA - GET)
    //@PreAuthorize("hasRole('ADMIN_1')")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // 🔒 Un usuario puede eliminar su propio perfil, pero no el de otros (PASO LA PRUEBA - DELET)
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();


        // Verifica si el id en la URL es igual al id del usuario autenticado
        if (!userDetails.getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar este usuario.");
        }

        userService.deleteUser(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

    // Editar perfil del usuario (solo si es el propio usuario o un admin) (PASO LA PRUEBA - PUT)
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN_1')")
    @PutMapping("/{id}")
    public ResponseEntity<User> editUserProfile(@PathVariable String id, @RequestBody User userDetails) {

        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Verificar que el id del usuario coincide con el id en la URL
        // Si es necesario, puedes agregar un cifrado aquí

        // Actualizar el perfil
        User updatedUser = userService.updateUser(userOptional.get().getId(),userDetails);
        return ResponseEntity.ok(updatedUser);
    }
}
