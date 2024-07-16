package com.example.forohub.repository;

import com.example.forohub.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ResponseRepository extends JpaRepository<Response, Long>
{
    @Query("SELECT r from Response r WHERE r.topic=:codeTopic")
    List<Response> findByTopic(Topic codeTopic);

    @Query(nativeQuery = true, value = """
            SELECT r.code,
            r.message,
            r.topic,
            r.creationdate,
            r.author,
            r.solution
            FROM response as r
            WHERE r.topic = :codeTopic
            """)
    List<Response> findByTopicId(Integer codeTopic);
}
