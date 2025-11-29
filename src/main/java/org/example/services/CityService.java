package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.data.dtos.city.CityCreateDTO;
import org.example.data.dtos.city.CityItemDTO;
import org.example.data.dtos.country.CountryCreateDTO;
import org.example.data.dtos.country.CountryItemDTO;
import org.example.data.mappers.CityMapper;
import org.example.data.mappers.CountryMapper;
import org.example.entities.location.CityEntity;
import org.example.entities.location.CountryEntity;
import org.example.repositories.ICityRepository;
import org.example.repositories.ICountryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CityService {

    private final ICityRepository cityRepository;
    private final ICountryRepository countryRepository;
    private final CityMapper cityMapper;
    private final FileService fileService;

    @Transactional
    public CityItemDTO create(CityCreateDTO dto) {
        if (cityRepository.existsBySlug(dto.getSlug())) {
            throw new IllegalArgumentException("Місто зі slug '" + dto.getSlug() + "' вже існує");
        }

        CityEntity entity = cityMapper.fromCreateDTO(dto);

        CountryEntity country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Країну не знайдено"));

        entity.setCountry(country);

        if(dto.getImage() != null){
            String fileName = fileService.load(dto.getImage());
            entity.setImage(fileName);
        }

        CityEntity saved = cityRepository.save(entity);
        return cityMapper.toDto(saved);
    }

    public List<CityItemDTO> getAll() {
        return cityRepository.findAll()
                .stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    public CityItemDTO getBySlug(String slug) {
        CityEntity entity = cityRepository.findBySlug(slug)
                .orElseThrow(() -> new IllegalArgumentException("Місто не знайдено: " + slug));
        return cityMapper.toDto(entity);
    }
}
