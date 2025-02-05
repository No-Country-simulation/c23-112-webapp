package Back_end.models;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResponse {

    private List<LoginUserResponse> loginUserResponses;
    private String message;             // Mensaje de respuesta (éxito/error)
    private String token;               // Token de autenticación
}
