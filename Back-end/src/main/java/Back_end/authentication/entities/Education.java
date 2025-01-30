package Back_end.authentication.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Education {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String school;
    private String degree;

    private String startDate;     // Fecha de inicio (formato "MM/yyyy")
    private String endDate;       // Fecha de finalización (formato "MM/yyyy")

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;            // Relación con el usuario
}
