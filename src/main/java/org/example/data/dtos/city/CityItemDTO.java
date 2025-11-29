package org.example.data.dtos.city;

import lombok.Data;
import org.example.data.dtos.country.CountryItemDTO;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CityItemDTO {
    private Long id;
    private String name;
    private String slug;
    private String image;
    private String description;
    private String timezone;
    private Long countryId;
    private Long countryName;
    private String dateCreated;
}
