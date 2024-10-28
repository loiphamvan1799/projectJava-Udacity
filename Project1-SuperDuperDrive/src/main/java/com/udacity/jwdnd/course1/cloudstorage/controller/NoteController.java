package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/home/note")
public class NoteController {

    @Autowired
    private final NoteService noteService;

    @Autowired
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }

    @PostMapping
    public String insertOrUpdateNote(Authentication authentication, Note note) {

        Integer userId = userService.getUserId(authentication).getUserId();
        if (note.getNoteId() != null) {
            noteService.updateNote(note);
        } else {
            noteService.insertNote(userId, note);
        }

        return "redirect:/result?success";
    }

    @GetMapping("/delete")
    public String deleteNote(@RequestParam Integer noteId,
                             RedirectAttributes redirectAttributes) {

        if (noteId != null) {
            noteService.deleteNote(noteId);
            return "redirect:/result?success";
        }

        redirectAttributes.addAttribute("error", "Error when delete note");
        return "redirect:/result?error";
    }
}
