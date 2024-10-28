package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userId = #{userId}")
    List<Note> getAllNoteByUser(Integer userId) ;

    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteByUser(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES (#{noteTitle}, #{noteDecription}, #{userId})")
    @Options(useGeneratedKeys = true)
    void insert(Integer userId, String noteTitle, String noteDecription);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    void updateNote(Integer noteId, String noteTitle, String noteDescription);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int delete(Integer noteId);
}
