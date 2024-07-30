package com.mitocode.controller;

import com.mitocode.dto.PatientDTO;
import com.mitocode.dto.VitalSignsDTO;
import com.mitocode.model.Patient;
import com.mitocode.model.VitalSigns;
import com.mitocode.service.IVitalSignsService;
import com.mitocode.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/vitalsigns")
@RequiredArgsConstructor
public class VitalSignsController {

    private final IVitalSignsService vitalSignService;
    private final MapperUtil mapperUtil;

      @GetMapping
    public ResponseEntity<List<VitalSignsDTO>> findAll(){
        List<VitalSignsDTO> list = mapperUtil.mapList(vitalSignService.findAll(), VitalSignsDTO.class);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VitalSignsDTO> findById(@PathVariable("id") Integer id){
        VitalSignsDTO obj = mapperUtil.map(vitalSignService.findById(id), VitalSignsDTO.class);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    public ResponseEntity<Void> save (@Valid @RequestBody VitalSignsDTO dto){
        VitalSigns obj = vitalSignService.save(mapperUtil.map(dto, VitalSigns.class));
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdSigns()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VitalSignsDTO> update (@Valid @PathVariable("id") Integer id, @RequestBody VitalSignsDTO dto){
        dto.setIdSigns(id);
        VitalSigns obj = vitalSignService.update(id, mapperUtil.map(dto, VitalSigns.class));
        return ResponseEntity.ok(mapperUtil.map(obj, VitalSignsDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id){
        vitalSignService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
