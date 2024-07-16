package com.example.forohub.controller;

import com.example.forohub.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/responsetopic")
@SecurityRequirement(name = "bearer-key")
public class ResponseTopicController
{
    @Autowired
    ServiceResponse serviceResponse;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener las respuestas por tema", tags = "Get")
    public ResponseEntity getResponseByTopic(@PathVariable Integer id)
    {
        return ResponseEntity.ok(serviceResponse.getResponsesByTopic(id));
    }
}
