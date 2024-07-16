package com.example.forohub.dto.topic;

import com.example.forohub.model.*;

public record DtoCreateResponse (
        String message,
        int idTopic,
        int idAuthor,
        String solution
)
{
}
