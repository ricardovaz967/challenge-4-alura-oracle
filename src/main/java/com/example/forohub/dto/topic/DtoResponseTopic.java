package com.example.forohub.dto.topic;

import com.example.forohub.dto.user.*;

import java.time.LocalDateTime;

public record DtoResponseTopic (
        LocalDateTime creationDate,
        String message,
        String solution,
        DtoUser author
)
{
}
