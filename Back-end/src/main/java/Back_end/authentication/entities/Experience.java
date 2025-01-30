package Back_end.authentication.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String company;
    private String position;

    private String startDate;     // Fecha de inicio (formato "MM/yyyy")
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;            // Relación con el usuario

}
