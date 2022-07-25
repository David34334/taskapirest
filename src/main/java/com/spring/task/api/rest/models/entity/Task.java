package com.spring.task.api.rest.models.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table( name = "tasks" )
@Data
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Task {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;
    @Column( name = "name" )
    private String name;
    @Column( name = "description" )
    private String description;
    @Column( name = "state" )
    private boolean state;
    @Column( name = "created_at" )
    @Builder.Default
    private Date createdAt = new Date(System.currentTimeMillis());
    @Column( name = "updated_at" )
    private Date lastUpdatedAt;

}
