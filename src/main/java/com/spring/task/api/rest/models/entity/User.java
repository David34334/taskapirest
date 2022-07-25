package com.spring.task.api.rest.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "users" )
@Data
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;
    @Column( name = "username" )
    private String username;
    @Column( name = "email" )
    private String email;
    @Column( name = "password" )
    private String password;
    @Column( name = "created_at" )
    private Date createdAt;

}
