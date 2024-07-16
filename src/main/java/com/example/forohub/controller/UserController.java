package com.example.forohub.controller;

import com.example.forohub.dto.user.*;
import com.example.forohub.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.security.Provider;
import java.util.List;

@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearer-key")
public class UserController
{
    @Autowired
    ServiceUser serviceUser;

    @GetMapping
    @Operation(summary = "Buscar todos los usuarios", tags = "Get")
    public ResponseEntity findAllUsers()
    {
        List<DtoUserMoreDetails> dtoUserMoreDetailsList = serviceUser.findAllUsers();

        return ResponseEntity.ok(dtoUserMoreDetailsList);
    }

    @GetMapping("{id}")
    @Operation(summary = "Obtener la información del usuario por id", tags = "Get")
    public ResponseEntity findUserById(@PathVariable Integer id)
    {
        DtoUserMoreDetails dtoUserMoreDetails = serviceUser.findUserById(Long.valueOf(id));

        return ResponseEntity.ok(dtoUserMoreDetails);
    }

    @PostMapping
    @Operation(summary = "Creaar un nuevo usuario", tags = "Post")
    public ResponseEntity createUser(@RequestBody @Valid DtoCreateUser dtoCreateUser, UriComponentsBuilder uriComponentsBuilder)
    {
        DtoUserMoreDetails dtoUserMoreDetails = serviceUser.createNewUser(dtoCreateUser);

        URI url = uriComponentsBuilder.path("/user/{id}").buildAndExpand(dtoUserMoreDetails.code()).toUri();

        return ResponseEntity.created(url).body(dtoUserMoreDetails);
    }

    @PutMapping("{id}")
    @Transactional
    @Operation(summary = "Actualizar información de un usuario", tags = "Put")
    public ResponseEntity updateUser(@PathVariable Integer id,@RequestBody DtoUpdateUser dtoUpdateUser)
    {
        DtoUserMoreDetails dtoUserMoreDetails = serviceUser.updateUser(Long.valueOf(id), dtoUpdateUser);

        return ResponseEntity.ok(dtoUserMoreDetails);
    }
}
