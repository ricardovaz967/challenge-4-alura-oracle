package com.example.forohub.dto.topic;

import com.example.forohub.model.*;

public record DtoCreateTopicToDatabase (
        String title,
        String message,
        User user,
        Course course,
        Status status
)
{
}
