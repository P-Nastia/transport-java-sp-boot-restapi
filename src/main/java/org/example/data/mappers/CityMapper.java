package org.example.data.mappers;

import org.example.data.dtos.city.CityCreateDTO;
import org.example.data.dtos.city.CityItemDTO;
import org.example.entities.location.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {
    @Mapping(source = "createdAt", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "country.id", target = "countryId")
    @Mapping(source = "country.name", target = "countryName")
    CityItemDTO toDto(CityEntity city);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "description", ignore = true)
    CityEntity fromCreateDTO(CityCreateDTO dto);
}
