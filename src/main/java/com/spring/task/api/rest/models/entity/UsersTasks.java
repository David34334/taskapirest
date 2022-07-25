package com.spring.task.api.rest.models.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table( name = "userstask" )
@Getter
@Setter
@ToString
@NoArgsConstructor
public class UsersTasks {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "id" )
    private Long id;

    @JoinColumn( name = "id_user", referencedColumnName = "id" )
    @ManyToOne
    private User user;

    @JoinColumn( name = "id_task", referencedColumnName = "id" )
    @OneToOne
    private Task task;

}