package com.ovidius.nbspringproject.repositories;

import com.ovidius.nbspringproject.models.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long id);
}
