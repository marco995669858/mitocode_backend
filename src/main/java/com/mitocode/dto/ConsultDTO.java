package com.mitocode.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.mitocode.model.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ConsultDTO {

    @EqualsAndHashCode.Include
    private Integer idConsult;

    @NotNull
    private PatientDTO patient;

    @NotNull
    private MedicDTO medic;

    @NotNull
    private Integer idUser;

    @NotNull
    private String numConsult;

    @NotNull
    private LocalDateTime consultDate;

    @NotNull
    @JsonManagedReference
    private List<ConsultDetailDTO> details;
}
