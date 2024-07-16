package com.example.forohub.controller;

import com.example.forohub.dto.topic.*;
import com.example.forohub.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/topicsearch")
@SecurityRequirement(name = "bearer-key")
public class TopicSearchController
{
    @Autowired
    ServiceTopic serviceTopic;

    @GetMapping
    @Operation(summary = "Encuentre algunos temas por título y año", tags = "Get")
    public ResponseEntity getAllTopics(@RequestBody DtoTopicSearchTitleAndYear dtoTopicSearchTitleAndYear)
    {
        return ResponseEntity.ok(serviceTopic.findTopicByTitleAndYear(dtoTopicSearchTitleAndYear));
    }
}
