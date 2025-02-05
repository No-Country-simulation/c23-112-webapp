package Back_end.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private String startDate;
    private String endDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

}
