package com.ovidius.nbspringproject.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="notes")
public class Note {
    @Id
            @GeneratedValue(strategy= GenerationType.IDENTITY)
    int id;

    String title;

    @Column(columnDefinition = "TEXT")
    String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
