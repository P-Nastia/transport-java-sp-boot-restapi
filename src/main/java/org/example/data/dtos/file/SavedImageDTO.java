package org.example.data.dtos.file;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SavedImageDTO {
    private Long id;
    private String imageName;
    private String dateCreated;
}