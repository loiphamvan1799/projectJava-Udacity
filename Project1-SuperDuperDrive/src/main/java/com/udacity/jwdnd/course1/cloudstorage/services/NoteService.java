package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    @Autowired
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNoteByUser(Integer userId) {
        return noteMapper.getAllNoteByUser(userId);
    }

    public void insertNote(Integer userId, Note note) {
        noteMapper.insert(userId, note.getNoteTitle(), note.getNoteDescription());
    }

    public void updateNote(Note note) {
        noteMapper.updateNote(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());
    }

    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }

}
