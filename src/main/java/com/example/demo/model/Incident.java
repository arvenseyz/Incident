package com.example.demo.model;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

    private Long id;

    @Nonnull
    private String description;
    public boolean isValid() {
        return description!= null &&!description.isEmpty();
    }


}