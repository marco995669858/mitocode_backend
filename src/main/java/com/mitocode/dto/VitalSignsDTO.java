package com.mitocode.dto;

import com.mitocode.model.Patient;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VitalSignsDTO {
    @EqualsAndHashCode.Include
    private Integer idSigns;

    @NotNull
    private Patient patient;

    @NotNull
    private LocalDateTime registrationDate;

    @NotNull
    private String temperature;

    @NotNull
    private String pulse;

    @NotNull
    private String respiratoryRate;
}
