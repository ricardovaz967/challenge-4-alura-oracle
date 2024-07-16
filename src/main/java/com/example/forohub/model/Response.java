package com.example.forohub.model;

import com.example.forohub.dto.topic.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Table(name = "response")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Response
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int code;

    private String message;

    private LocalDateTime creationdate;

    private String solution;

    @JoinColumn(name="author", referencedColumnName="code")
    @OneToOne
    private User author;

    @JoinColumn(name="topic", referencedColumnName="code")
    @OneToOne
    private Topic topic;

    public Response(DtoCreateResponseToDatabase dtoCreateResponseToDatabase)
    {
        this.message = dtoCreateResponseToDatabase.message();
        this.creationdate = LocalDateTime.now();
        this.author = dtoCreateResponseToDatabase.author();
        this.topic = dtoCreateResponseToDatabase.topic();
        this.solution = dtoCreateResponseToDatabase.solution();
    }

}
