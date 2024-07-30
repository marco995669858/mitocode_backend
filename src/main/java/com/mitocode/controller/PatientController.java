package com.mitocode.controller;

import com.mitocode.dto.PatientDTO;
import com.mitocode.dto.PatientRecord;
import com.mitocode.model.Patient;
import com.mitocode.service.IPatientService;
import com.mitocode.util.MapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
//@RequestMapping("${patient.controller.path}")
@RequestMapping("/patients")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class PatientController {

    //@Autowired
    private final IPatientService service;// = new PatientServiceImpl();
    /*@Qualifier("defaultMapper")
    private final ModelMapper mapper;/
     */

    private final MapperUtil mapperUtil;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> findAll() {
        //List<PatientDTO> list = service.findAll().stream().map(p -> new PatientDTO(p.getIdPatient(), p.getFirstName(), p.getLastName(), p.getDni(), p.getAddress(), p.getPhone(), p.getEmail())).toList();
        //List<PatientDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        List<PatientDTO> list = mapperUtil.mapList(service.findAll(), PatientDTO.class);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable("id") Integer id) {
        Patient obj =  service.findById(id);

        //return ResponseEntity.ok(convertToDto(obj));
        return ResponseEntity.ok(mapperUtil.map(obj, PatientDTO.class));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody PatientDTO dto) {
        //Patient obj = service.save(convertToEntity(dto));
        Patient obj = service.save(mapperUtil.map(dto, Patient.class));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdPatient()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody PatientDTO dto) {
        dto.setIdPatient(id);
        //Patient obj = service.update(id, convertToEntity(dto));
        Patient obj = service.update(id, mapperUtil.map(dto, Patient.class));

        //return ResponseEntity.ok(convertToDto(obj));
        return ResponseEntity.ok(mapperUtil.map(obj, PatientDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<PatientDTO> findByIdHateoas(@PathVariable("id") Integer id) {
        //EntityModel<PatientDTO> resource = EntityModel.of(convertToDto(service.findById(id)));
        EntityModel<PatientDTO> resource = EntityModel.of(mapperUtil.map(service.findById(id), PatientDTO.class));

        //generar link informativo
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("patient-self-info"));
        resource.add(link2.withRel("patient-all-info"));

        return resource;
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<PatientDTO>> listPage(Pageable pageable){
        Page<PatientDTO> page = service.listPage(pageable).map(e -> mapperUtil.map(e, PatientDTO.class));

        return ResponseEntity.ok(page);
    }

    /*private PatientDTO convertToDto(Patient obj) {
        return mapper.map(obj, PatientDTO.class);
    }

    private Patient convertToEntity(PatientDTO dto) {
        return mapper.map(dto, Patient.class);
    }*/

    /*public PatientController(IPatientService service) {
        this.service = service;
    }*/

    /*@GetMapping
    public Patient findById() {
        return service.findByIdAndValidate(5);
    }*/
}
