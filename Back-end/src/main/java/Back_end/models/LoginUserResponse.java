package Back_end.models;

import Back_end.entities.Education;
import Back_end.entities.Experience;
import Back_end.entities.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginUserResponse {

    private String id;                  // ID del usuario
    private String name;                // Nombre del usuario
    private String last_name;            // Apellido del usuario
    private String email;               // Correo electrónico del usuario
    private String photo;               // URL de la foto del usuario
    private String phone;               // Número de teléfono del usuario
    private List<String> profession;    // Lista de profesiones del usuario
    private List<Experience> experience; // Lista de experiencias laborales del usuario
    private List<Education> education;  // Lista de educación del usuario
    private List<String> skills;        // Lista de habilidades del usuario
    private String aboutMe;             // Descripción sobre el usuario
    private String cv;                  // URL del CV del usuario
    private Role role;                  // Rol del usuario
}
