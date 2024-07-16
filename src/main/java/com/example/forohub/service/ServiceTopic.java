package com.example.forohub.service;

import com.example.forohub.dto.user.*;
import com.example.forohub.dto.topic.*;
import com.example.forohub.validation.*;
import com.example.forohub.repository.*;
import com.example.forohub.model.*;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceTopic
{
    @Autowired
    TopicRepository topicRepository;

    @Autowired
    ResponseRepository responseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    List<IValidationTopic> validationTopics;

    public List<DtoResponseGetDataTopic> getAllDataTopic()
    {
        List<Topic> listTopics = topicRepository.findAll();
        List<DtoResponseGetDataTopic> topics = new ArrayList<>();

        for(Topic t :listTopics)
        {
            DtoResponseGetDataTopic dataTopic =fillDtoGetDataTopic(t);

            topics.add(dataTopic);
        }

        return topics;
    }

    public DtoResponseGetDataTopic createTopic(DtoCreateTopic dtoCreateTopic)
    {
        //Encontrar los objetos con IDs
        Optional<User> userGetter = userRepository.findById(Long.valueOf(dtoCreateTopic.user()));
        Optional<Course> courseGetter = courseRepository.findById(Long.valueOf(dtoCreateTopic.course()));
        Optional<Status> statusGetter =statusRepository.findById(Long.valueOf(1));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("El usuario no existe, por favor revisa el código");
        }

        if(courseGetter.isEmpty())
        {
            throw new ValidationException("El curso no existe, por favor verifique el código del curso");
        }

        if(statusGetter.isEmpty())
        {
            throw new ValidationException("El estado no existe, verifique el código de estado");
        }

        DtoCreateTopicToDatabase dtoCreateTopicToDatabase = new DtoCreateTopicToDatabase(
                dtoCreateTopic.title(),
                dtoCreateTopic.message(),
                userGetter.get(),
                courseGetter.get(),
                statusGetter.get()
        );

        validationTopics.forEach(t -> t.checkValidation(dtoCreateTopicToDatabase));

        Topic topicData = new Topic(dtoCreateTopicToDatabase);

        topicRepository.save(topicData);

        DtoResponseGetDataTopic dataTopic =fillDtoGetDataTopic(topicData);

        return dataTopic;
    }

    public DtoResponseGetDataTopic getTopicById(Long id)
    {
        Optional<Topic> optional_Topic = topicRepository.findById(id);

        if(optional_Topic.isEmpty())
        {
            throw new ValidationException("El tema no está presente, verifique el código");
        }

        Topic topicData = optional_Topic.get();

        DtoResponseGetDataTopic dataTopic =fillDtoGetDataTopic(topicData);

        return dataTopic;
    }

    public DtoResponseGetDataTopic updateTopic(Long id, DtoUpdateTopic dtoUpdateTopic)
    {
        Optional<Topic> topicGetter = topicRepository.findById(id);

        if(topicGetter.isEmpty())
        {
            throw new ValidationException("El tema no existe, por favor revisa el código");
        }

        Topic topic = topicGetter.get();

        Optional<User> userGetter = userRepository.findById(Long.valueOf(dtoUpdateTopic.user()));
        Optional<Course> courseGetter = courseRepository.findById(Long.valueOf(dtoUpdateTopic.course()));
        Optional<Status> statusGetter =statusRepository.findById(Long.valueOf(1));

        if(userGetter.isEmpty())
        {
            throw new ValidationException("El usuario no existe");
        }

        if(courseGetter.isEmpty())
        {
            throw new ValidationException("El curso no existe");
        }

        if(statusGetter.isEmpty())
        {
            throw new ValidationException("El estado no existe");
        }

        topic.setMessage(dtoUpdateTopic.message());
        topic.setTitle(dtoUpdateTopic.title());
        topic.setAuthor(userGetter.get());
        topic.setCourse(courseGetter.get());
        topic.setStatus(statusGetter.get());

        DtoResponseGetDataTopic dtoResponseGetDataTopic = fillDtoGetDataTopic(topic);

        return dtoResponseGetDataTopic;
    }

    public DtoResponseDeleteTopic DeleteTopic(Long id)
    {
        DtoResponseDeleteTopic dtoResponseDeleteTopic = new DtoResponseDeleteTopic(200,
                "El registro ha sido eliminado");

        topicRepository.deleteById(id);

        return dtoResponseDeleteTopic;
    }

    public List<DtoResponseGetDataTopic> findLastTenRecords()
    {
        List<Topic> listTopics = topicRepository.findLastTenRecordsByCreationDate();
        List<DtoResponseGetDataTopic> topics = new ArrayList<>();

        for(Topic t :listTopics)
        {
            DtoResponseGetDataTopic dataTopic =fillDtoGetDataTopic(t);

            topics.add(dataTopic);
        }

        return topics;
    }

    public List<DtoResponseGetDataTopic> findTopicByTitleAndYear(DtoTopicSearchTitleAndYear dtoTopicSearchTitleAndYear)
    {
        List<Topic> listTopics = topicRepository.findTopicByCourseNameAndYear(dtoTopicSearchTitleAndYear.courseName(),
                dtoTopicSearchTitleAndYear.year());
        List<DtoResponseGetDataTopic> topics = new ArrayList<>();

        for(Topic t :listTopics)
        {
            DtoResponseGetDataTopic dataTopic =fillDtoGetDataTopic(t);

            topics.add(dataTopic);
        }

        return topics;
    }

    public DtoResponseGetDataTopic fillDtoGetDataTopic(Topic topicData)
    {
        List<Response> listResponses = responseRepository.findByTopic(topicData);

        //Obtain the list of Responses
        List<DtoResponseTopic> listDtoResponseTopic = listResponses.stream()
                .map(r -> new DtoResponseTopic(r.getCreationdate(),
                        r.getMessage(),
                        r.getSolution(),
                        new DtoUser(r.getAuthor().getUsername(), r.getAuthor().getEmail())))
                .toList();

        DtoResponseGetDataTopic dtoResponseGetDataTopic = new DtoResponseGetDataTopic(
                topicData.getCode(),
                topicData.getTitle(),
                topicData.getMessage(),
                topicData.getCreationDate(),
                topicData.getStatus().getDescription(),
                new DtoUser(topicData.getAuthor().getUsername(), topicData.getAuthor().getEmail()),
                topicData.getCourse().getName(),
                listDtoResponseTopic
        );

        return dtoResponseGetDataTopic;
    }
}
