package Back_end.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {

    private String userName; //correo electronico
    private String name;
    private String last_name;
    private String password;
    private int rol;
}
