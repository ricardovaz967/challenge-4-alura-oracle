package com.example.forohub.controller;

import com.example.forohub.dto.topic.*;
import com.example.forohub.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/response")
@SecurityRequirement(name = "bearer-key")
public class ResponseController
{
    @Autowired
    ServiceResponse serviceResponse;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener la información de una respuesta específica por código", tags = "Get")
    public ResponseEntity getResponsesByTopic(@PathVariable Integer id)
    {
        return ResponseEntity.ok(serviceResponse.getResponseById(id));
    }

    @PostMapping
    @Operation(summary = "Obtener las respuestas del tema específico", tags = "Post")
    public ResponseEntity getResponseByTopic(@RequestBody DtoCreateResponse dtoCreateResponse, UriComponentsBuilder uriComponentsBuilder )
    {
        DtoResponseInfoOfResponseTopic dtoResponseInfoOfResponseTopic = serviceResponse.createResponse(dtoCreateResponse);

        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(dtoResponseInfoOfResponseTopic.codeResponse()).toUri();

        return ResponseEntity.created(url).body(dtoResponseInfoOfResponseTopic);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar la información de la respuesta", tags = "Put")
    @Transactional
    public ResponseEntity updateResponseData(@PathVariable Integer id, @RequestBody DtoUpdateResponse dtoUpdateResponse)
    {
        return ResponseEntity.ok(serviceResponse.updateResponse(Long.valueOf(id), dtoUpdateResponse));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una respuesta utilizando el código", tags = "Delete")
    @Transactional
    public ResponseEntity deleteResponseData(@PathVariable Integer id)
    {
        return ResponseEntity.ok(serviceResponse.deleteResponse(Long.valueOf(id)));
    }

}
