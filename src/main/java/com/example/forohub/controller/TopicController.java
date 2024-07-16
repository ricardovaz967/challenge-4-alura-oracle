package com.example.forohub.controller;

import com.example.forohub.dto.topic.*;
import com.example.forohub.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topic")
@SecurityRequirement(name = "bearer-key")
public class TopicController
{
    @Autowired
    ServiceTopic serviceTopic;

    @GetMapping
    @Operation(summary = "Obtener todos los temas", tags = "Get")
    public ResponseEntity getAllTopics()
    {
        return ResponseEntity.ok(serviceTopic.getAllDataTopic());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo tema", tags = "Post")
    public ResponseEntity createTopic(@RequestBody @Valid DtoCreateTopic dtoCreateTopic, UriComponentsBuilder uriComponentsBuilder)
    {
        DtoResponseGetDataTopic topicCreated = serviceTopic.createTopic(dtoCreateTopic);

        URI url = uriComponentsBuilder.path("/topic/{id}").buildAndExpand(topicCreated.id()).toUri();

        return ResponseEntity.created(url).body(topicCreated);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener la información de un tema por código", tags = "Get")
    public ResponseEntity getTopicById(@PathVariable Long id)
    {
        return ResponseEntity.ok(serviceTopic.getTopicById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualizar la información de un tema", tags = "Put")
    public ResponseEntity updateTopicById(@PathVariable Long id, @RequestBody DtoUpdateTopic dtoUpdateTopic)
    {
        DtoResponseGetDataTopic dtoResponseGetDataTopic = serviceTopic.updateTopic(id, dtoUpdateTopic);

        return ResponseEntity.ok(dtoResponseGetDataTopic);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Eliminar un tema por su identificador (ID)", tags = "Delete")
    public ResponseEntity deleteTopicById(@PathVariable Long id)
    {
       return ResponseEntity.ok(serviceTopic.DeleteTopic(id));
    }
}
