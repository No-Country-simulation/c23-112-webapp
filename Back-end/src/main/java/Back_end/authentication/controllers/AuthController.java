package Back_end.authentication.controllers;

import Back_end.authentication.dto.LoginUserDto;
import Back_end.authentication.dto.NewUserDto;
import Back_end.authentication.entities.User;
import Back_end.authentication.models.LoginResponse;
import Back_end.authentication.models.LoginUserResponse;
import Back_end.authentication.services.AuthService;
import Back_end.authentication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body(LoginResponse.builder()
                            .message("Revise sus credenciales")
                    .build());
        }
        try {
            // Autenticar al usuario y generar el token
            String jwt = authService.authenticate(loginUserDto.getEmail(), loginUserDto.getPassword());

            System.out.println(jwt);

            // Obtener el usuario por su nombre de usuario (email)
            Optional<User> user = userService.getUserByEmail(loginUserDto.getEmail());

            if (user.isPresent()) {
                // Construir la respuesta del usuario
                LoginUserResponse userResponse = LoginUserResponse.builder()
                        .id(user.get().getId())
                        .name(user.get().getName())
                        .last_name(user.get().getLast_name())
                        .email(user.get().getEmail())
                        .role(user.get().getRole())
                        .build();
                // Construir la respuesta del login
                LoginResponse loginResponse = LoginResponse.builder()
                        .loginUserResponses(List.of(userResponse)) // Agregar la respuesta del usuario
                        .message("Inicio de sesión exitoso")
                        .token(jwt) // Agregar el token
                        .build();

                return ResponseEntity.ok(loginResponse);
            }
            else {
                return ResponseEntity.badRequest().body(
                        LoginResponse.builder()
                                .message("Usuario no encontrado")
                                .build()
                );
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body(LoginResponse.builder()
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return ResponseEntity.badRequest().body("Revise los campos");
        }
        try {
            authService.registerUser(newUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Registrado");
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/check-auth")
    public ResponseEntity<String> checkAuth(){
        return ResponseEntity.ok().body("Autenticado");
    }

}
