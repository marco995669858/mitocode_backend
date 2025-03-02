package com.mitocode.controller;

import com.mitocode.dto.ExamDTO;
import com.mitocode.model.Exam;
import com.mitocode.service.IExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
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
//@RequestMapping("${exam.controller.path}")
@RequestMapping("/exams")
@RequiredArgsConstructor
public class ExamController {

    private final IExamService service;
    @Qualifier("defaultMapper")
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ExamDTO>> findAll() {
        List<ExamDTO> list = service.findAll().stream().map(this::convertToDto).toList();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> findById(@PathVariable("id") Integer id) {
        Exam obj = service.findById(id);

        return ResponseEntity.ok(convertToDto(obj));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ExamDTO dto) {
        Exam obj = service.save(convertToEntity(dto));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdExam()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> update(@Valid @PathVariable("id") Integer id, @RequestBody ExamDTO dto) {
        dto.setIdExam(id);
        Exam obj = service.update(id, convertToEntity(dto));

        return ResponseEntity.ok(convertToDto(obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hateoas/{id}")
    public EntityModel<ExamDTO> findByIdHateoas(@PathVariable("id") Integer id) {
        EntityModel<ExamDTO> resource = EntityModel.of(convertToDto(service.findById(id)));

        //generar link informativo
        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));
        WebMvcLinkBuilder link2 = linkTo(methodOn(this.getClass()).findAll());

        resource.add(link1.withRel("exam-self-info"));
        resource.add(link2.withRel("exam-all-info"));

        return resource;
    }

    private ExamDTO convertToDto(Exam obj) {
        return mapper.map(obj, ExamDTO.class);
    }

    private Exam convertToEntity(ExamDTO dto) {
        return mapper.map(dto, Exam.class);
    }

    /*public ExamController(IExamService service) {
        this.service = service;
    }*/

    /*@GetMapping
    public Exam findById() {
        return service.findByIdAndValidate(5);
    }*/
}
