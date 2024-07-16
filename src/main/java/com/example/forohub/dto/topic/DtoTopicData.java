package com.example.forohub.dto.topic;

import com.example.forohub.model.*;

import java.time.LocalDateTime;

public record DtoTopicData (
        String title,
        String message,
        LocalDateTime creationdate,
        Status status,
        User author,
        Course course
)
{
}
