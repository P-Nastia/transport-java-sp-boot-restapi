package org.example.data.mappers;

import org.example.data.dtos.city.CityCreateDTO;
import org.example.data.dtos.city.CityItemDTO;
import org.example.entities.location.CityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {
    @Mapping(source = "createdAt", target = "dateCreated", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "country.id", target = "country_id")
    CityItemDTO toDto(CityEntity country);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "country", ignore = true)
    CityEntity fromCreateDTO(CityCreateDTO dto);
}
