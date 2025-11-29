package org.example.services;

import io.micrometer.common.lang.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.data.dtos.city.CityCreateDTO;
import org.example.data.dtos.city.CityItemDTO;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.example.data.mappers.CityMapper;
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

//    @Transactional
//    public CityItemDTO create(CityCreateDTO dto) {
//        if (cityRepository.existsBySlug(dto.getSlug())) {
//            throw new IllegalArgumentException("Місто зі slug '" + dto.getSlug() + "' вже існує");
//        }
//
//        CityEntity entity = cityMapper.fromCreateDTO(dto);
//
//        CountryEntity country = countryRepository.findById(dto.getCountryId())
//                .orElseThrow(() -> new IllegalArgumentException("Країну не знайдено"));
//
//        entity.setCountry(country);
//
//        if(dto.getImage() != null){
//            String fileName = fileService.load(dto.getImage());
//            entity.setImage(fileName);
//        }
//
//        CityEntity saved = cityRepository.save(entity);
//        return cityMapper.toDto(saved);
//    }

    @Transactional
    public CityItemDTO create(CityCreateDTO dto, @Nullable HttpServletRequest request) {
        if (cityRepository.existsBySlug(dto.getSlug())) {
            throw new IllegalArgumentException("Місто зі slug '" + dto.getSlug() + "' вже існує");
        }

        CityEntity entity = cityMapper.fromCreateDTO(dto);

        CountryEntity country = countryRepository.findById(dto.getCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Місто з ID " + dto.getCountryId() + " не знайдена."));

        entity.setCountry(country);

        if (dto.getImage() != null) {
            String fileName = fileService.load(dto.getImage());
            entity.setImage(fileName);
        }

        if (dto.getDescription() != null && !dto.getDescription().isBlank() && request != null) {
            String processedDescription = processDescriptionImages(dto.getDescription(), request);
            entity.setDescription(processedDescription);
        }

        CityEntity saved = cityRepository.save(entity);
        return cityMapper.toDto(saved);
    }

    public String processDescriptionImages(String html, HttpServletRequest request) {
        Document doc = Jsoup.parseBodyFragment(html);
        Elements images = doc.select("img");

        String baseUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            baseUrl += ":" + request.getServerPort();
        }

        for (Element img : images) {
            String src = img.attr("src");
            if (src != null && !src.isBlank() && src.startsWith("http")) {
                String serverFileName = fileService.load(src);
                img.attr("src", baseUrl + "/uploads/large/" + serverFileName);
            }
        }

        return doc.body().html();
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
