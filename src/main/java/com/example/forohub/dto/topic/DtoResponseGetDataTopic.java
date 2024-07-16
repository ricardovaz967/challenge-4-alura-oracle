package com.example.forohub.dto.topic;

import com.example.forohub.dto.user.*;

import java.time.LocalDateTime;
import java.util.List;

public record DtoResponseGetDataTopic(Integer id,
                                      String title,
                                      String message,
                                      LocalDateTime creationDate,
                                      String status,
                                      DtoUser user,
                                      String course,
                                      List<DtoResponseTopic> listResponses
                               )
{

}
