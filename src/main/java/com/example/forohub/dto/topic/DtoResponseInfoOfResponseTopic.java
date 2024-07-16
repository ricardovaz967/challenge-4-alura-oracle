package com.example.forohub.dto.topic;

import java.time.LocalDateTime;

public record DtoResponseInfoOfResponseTopic(
        Integer codeTopic,
        String messageTopic,
        Integer codeResponse,
        String messageResponse,
        String solution,
        LocalDateTime creationDate,
        String usernameAuthor
) {
}
