package pl.kurs.finaltesttest.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "actions")
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "id", nullable = false)

    private Admin createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private ActionType actionType;

    private String fieldName;
    private String oldValue;
    private String newValue;
}