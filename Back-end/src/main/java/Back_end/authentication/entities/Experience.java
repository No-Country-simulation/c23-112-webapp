package Back_end.authentication.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String company;
    private String position;
    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;            // Relación con el usuario

}
