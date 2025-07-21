package com.ovidius.nbspringproject.controllers;


import com.ovidius.nbspringproject.models.Note;
import com.ovidius.nbspringproject.services.NoteService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import com.ovidius.nbspringproject.dto.UserRegistrationDto;
import com.ovidius.nbspringproject.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final NoteService noteService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto("", ""));
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") UserRegistrationDto registrationDto) {
        userService.registerNewUser(registrationDto, passwordEncoder);
        return "redirect:/login?success";
    }

    @GetMapping("/notes")
    public String showNotesPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Note> notes = noteService.findNotesForCurrentUser(userDetails);
        model.addAttribute("notes", notes);
        model.addAttribute("newNote", new Note());
        return "notes";
    }

    @PostMapping("/add-note")
    public String addNote(@ModelAttribute("newNote") Note note, @AuthenticationPrincipal UserDetails userDetails) {
        noteService.createNote(note.getTitle(), note.getContent(), userDetails);
        return "redirect:/notes";
    }

    @PostMapping("/delete-note")
    public String deleteNote(@RequestParam("id") Long noteId, @AuthenticationPrincipal UserDetails userDetails) {
        noteService.deleteNoteById(noteId, userDetails);
        return "redirect:/notes";
    }
}