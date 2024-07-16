package com.example.forohub.service;

import com.example.forohub.dto.topic.*;
import com.example.forohub.repository.*;
import com.example.forohub.model.*;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceResponse
{
    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    TopicRepository topicRepository;

    @Autowired
    UserRepository userRepository;

    public DtoResponseInfoResponse getResponseById(int responseId)
    {
        Optional<Response> responseGetter = responseRepository.findById(Long.valueOf(responseId));

        if(responseGetter.isEmpty())
        {
            throw new ValidationException("La respuesta no existe, por favor verifica si el código es correcto");
        }

        Response response = responseGetter.get();

        DtoResponseInfoResponse dtoResponseInfoResponse = new DtoResponseInfoResponse(response.getCode(),
                response.getTopic().getCode(),
                response.getMessage(),
                response.getSolution(),
                response.getCreationdate(),
                response.getAuthor().getUsername());

        return dtoResponseInfoResponse;
    }

    public DtoResponseInfoOfResponseTopic createResponse(DtoCreateResponse dtoCreateResponse)
    {
        //Find the objects with the IDs
        Optional<User> userGetter = userRepository.findById(Long.valueOf(dtoCreateResponse.idAuthor()));
        Optional<Topic> topicGetter = topicRepository.findById(Long.valueOf(dtoCreateResponse.idTopic()));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("El código del usuario no existe, por favor compruébalo");
        }

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("El tema no existe, por favor compruébalo");
        }

        DtoCreateResponseToDatabase dtoCreateResponseToDatabase = new DtoCreateResponseToDatabase(
                dtoCreateResponse.message(),
                topicGetter.get(),
                userGetter.get(),
                dtoCreateResponse.solution()
        );

        Response response = new Response(dtoCreateResponseToDatabase);

        responseRepository.save(response);

        return fillData(topicGetter.get(), response);
    }

    public DtoResponseInfoOfResponseTopic updateResponse(Long idResponse, DtoUpdateResponse dtoUpdateResponse)
    {
        //Find the objects with the IDs
        Optional<User> userGetter = userRepository.findById(Long.valueOf(dtoUpdateResponse.idAuthor()));
        Optional<Topic> topicGetter = topicRepository.findById(Long.valueOf(dtoUpdateResponse.idTopic()));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("El código del usuario no existe, por favor compruébalo");
        }

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("El tema no existe, por favor compruébalo");
        }

        Response response = responseRepository.findById(idResponse).get();

        response.setMessage(dtoUpdateResponse.message());
        response.setSolution(dtoUpdateResponse.solution());
        response.setAuthor(userGetter.get());
        response.setTopic(topicGetter.get());

        return fillData(topicGetter.get(), response);
    }

    public DtoResponseDeleteResponse deleteResponse(Long id)
    {
        try
        {
            DtoResponseDeleteResponse dtoResponseDeleteResponse = new DtoResponseDeleteResponse(200,
                    "La respuesta fue eliminada correctamente");

            responseRepository.deleteById(id);

            return dtoResponseDeleteResponse;
        }
        catch (Exception e)
        {
            throw new ValidationException("Ocurrió un error al borrar una respuesta");
        }
    }

    public List<DtoResponseInfoOfResponseTopic> getResponsesByTopic(int topicId)
    {
        Optional<Topic> topicGetter = topicRepository.findById(Long.valueOf(topicId));
        List<DtoResponseInfoOfResponseTopic> listResponses = new ArrayList<>();

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("El código del usuario no existe, por favor compruébalo");
        }

        Topic topic = topicGetter.get();

        List<Response> responsesList =  responseRepository.findByTopicId(topicId);

        for(Response r: responsesList)
        {
            DtoResponseInfoOfResponseTopic dtoResponseInfoOfResponseTopic = fillData(topic, r);

            listResponses.add(dtoResponseInfoOfResponseTopic);
        }

        return listResponses;
    }

    public DtoResponseInfoOfResponseTopic fillData(Topic topic, Response response)
    {
        DtoResponseInfoOfResponseTopic dtoResponseInfoOfResponseTopic = new DtoResponseInfoOfResponseTopic(
                topic.getCode(),
                topic.getMessage(),
                response.getCode(),
                response.getMessage(),
                response.getSolution(),
                response.getCreationdate(),
                response.getAuthor().getUsername()
                );

        return dtoResponseInfoOfResponseTopic;
    }
}