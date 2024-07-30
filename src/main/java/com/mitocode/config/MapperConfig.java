package com.mitocode.config;

import com.mitocode.dto.ConsultDTO;
import com.mitocode.dto.MedicDTO;
import com.mitocode.model.Consult;
import com.mitocode.model.Medic;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper defaultMapper() {
        return new ModelMapper();
    }

    @Bean("medicMapper")
    public ModelMapper medicMapper() {
        ModelMapper modelMapper = new ModelMapper();

        //Escritura
        modelMapper.createTypeMap(MedicDTO.class, Medic.class)
                .addMapping(MedicDTO::getPrimaryName, (dest, v) -> dest.setFirstName((String) v))
                .addMapping(MedicDTO::getSurname, (dest, v) -> dest.setLastName((String) v))
                .addMapping(MedicDTO::getPhoto, (dest, v) -> dest.setPhotoUrl((String) v));

        //Lectura
        modelMapper.createTypeMap(Medic.class, MedicDTO.class)
                .addMapping(Medic::getFirstName, (dest, v) -> dest.setPrimaryName((String) v))
                .addMapping(Medic::getLastName, (dest, v) -> dest.setSurname((String) v));

        return modelMapper;
    }

    @Bean("consultMapper")
    public ModelMapper consultMapper(){
        ModelMapper mapper = new ModelMapper();

        mapper.createTypeMap(Consult.class, ConsultDTO.class)
                .addMapping(e -> e.getMedic().getFirstName(), (dest, v) -> dest.getMedic().setPrimaryName((String) v))
                .addMapping(e -> e.getMedic().getLastName(), (dest, v) -> dest.getMedic().setSurname((String) v));

        return mapper;
    }
}
