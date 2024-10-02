package pl.kurs.finaltesttest.mapper;

import org.mapstruct.Mapper;
import pl.kurs.finaltesttest.dto.AdminDTO;
import pl.kurs.finaltesttest.model.Admin;

@Mapper(componentModel = "spring")
public interface AdminMapper {
    AdminDTO toDto(Admin admin);
    Admin toEntity(AdminDTO adminDTO);
}