package Back_end.entities;

import Back_end.enums.RoleList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data //(tener Geter, Seter y algunos otros metodos que necesitamos)
@NoArgsConstructor //Constructor sin arguemntos (bacio)
@AllArgsConstructor //Constructor con todos los argumentos
@Entity // Indicarle a Spring que es una entidad
public class Role {

    @Id //Identificador
    @GeneratedValue(strategy = GenerationType.IDENTITY) //valor autoincrementado
    private Integer id;

    @Column(nullable = false) //Indicamos que es una columna que no puede tener un valor null
    private RoleList name;
}
