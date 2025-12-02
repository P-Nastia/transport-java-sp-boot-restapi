package org.example.data.dtos.city;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CityCreateDTO {
    @NotBlank(message = "Ім'я обов'язкове")
    private String name;
    @NotBlank(message = "Slug обов'язковий")
    private String slug;
    private MultipartFile image;
    private String description;
    private String timezone;
    @NotNull(message = "Країна обов'язкова")
    private Long countryId;

    private List<Long> descriptionImageIds;
}
