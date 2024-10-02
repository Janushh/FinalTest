package pl.kurs.finaltesttest.mapper;

import org.mapstruct.Mapper;
import pl.kurs.finaltesttest.dto.ActionDTO;
import pl.kurs.finaltesttest.model.Action;

@Mapper(componentModel = "spring")
public interface ActionMapper {
    ActionDTO toDto(Action action);
    Action toEntity(ActionDTO actionDto);
}
