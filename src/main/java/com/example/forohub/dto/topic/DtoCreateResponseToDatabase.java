package com.example.forohub.dto.topic;

import com.example.forohub.model.*;

public record DtoCreateResponseToDatabase(
        String message,
        Topic topic,
        User author,
        String solution
) {
}
