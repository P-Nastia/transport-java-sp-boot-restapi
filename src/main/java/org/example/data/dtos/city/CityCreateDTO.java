package org.example.data.dtos.city;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CityCreateDTO {
    private String name;
    private String slug;
    private MultipartFile image;
    private String description;
    private String timezone;
    private Long countryId;
}
