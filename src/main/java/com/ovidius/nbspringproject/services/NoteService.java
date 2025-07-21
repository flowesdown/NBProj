package com.ovidius.nbspringproject.services;

import com.ovidius.nbspringproject.models.Note;
import com.ovidius.nbspringproject.models.User;
import com.ovidius.nbspringproject.repositories.NoteRepository;
import com.ovidius.nbspringproject.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    private User getUserByUserDetails(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("Пользователь не найден"));
    }

    @Transactional(readOnly = true)
    public List<Note> findNotesForCurrentUser(UserDetails userDetails) {
        User currentUser = getUserByUserDetails(userDetails);
        return currentUser.getNotes();
    }

    @Transactional
    public void createNote(String title, String content, UserDetails userDetails) {
        User currentUser = getUserByUserDetails(userDetails);
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        note.setUser(currentUser);
        noteRepository.save(note);
    }
    @Transactional
    public void deleteNoteById(Long noteId, UserDetails userDetails) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new EntityNotFoundException("Заметка не найдена: " + noteId));
        if (!note.getUser().getUsername().equals(userDetails.getUsername())) {
            throw new SecurityException("Вы не можете удалить чужую заметку.");
        }

        noteRepository.delete(note);
    }

}
