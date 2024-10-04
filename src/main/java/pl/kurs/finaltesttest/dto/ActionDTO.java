package pl.kurs.finaltesttest.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ActionDTO {
    private Long id;
    private Long adminId;
    private String actionType;
    private String fieldName;
    private String oldValue;
    private String newValue;
    private LocalDateTime createdDate;
}
