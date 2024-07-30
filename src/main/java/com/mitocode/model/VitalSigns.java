package com.mitocode.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VitalSigns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idSigns;

    @ManyToOne
    @JoinColumn(name = "id_patient", nullable = false, foreignKey = @ForeignKey(name = "FK_VITALSIGNS_PATIENT"))
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false, length = 50)
    private String temperature;

    @Column(nullable = false, length = 50)
    private String pulse;

    @Column(nullable = false, length = 100)
    private String respiratoryRate;
}
