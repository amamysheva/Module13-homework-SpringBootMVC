package springbootmvc.model.service;

import org.springframework.stereotype.Service;
import springbootmvc.model.entity.Note;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class NoteService {
    private static final List<Note> listNote = new ArrayList<>();
    static {
        listNote.add(new Note(1, "Title_1", "Content_1"));
        listNote.add(new Note(2, "Title_2", "Content_2"));
        listNote.add(new Note(3, "Title_3", "Content_3"));
        listNote.add(new Note(4, "Title_4", "Content_4"));
        listNote.add(new Note(5, "Title_5", "Content_5"));
        listNote.add(new Note(6, "Title_6", "Content_6"));
    }
    public List<Note> listAll() {
        return listNote;
    }

    public Note add(Note note) {
        note.setId(generateId());
        listNote.add(note);
        return note;
    }

    private long generateId() {
        long id = new SecureRandom().nextLong(500);
        boolean isIdMatch = listNote.stream()
                .map(Note::getId)
                .anyMatch(noteId -> noteId == id);
        return isIdMatch ? generateId() : id;
    }

    public void deleteById(long id) {
        listNote.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .ifPresentOrElse(listNote::remove, () -> {throw new NoSuchElementException();});
    }

    public void update(Note note) {
        listNote.stream()
                .filter(noteToUpdate -> note.getId() == noteToUpdate.getId())
                .findFirst()
                .ifPresentOrElse(noteToUpdate ->
                {
                    noteToUpdate.setContent(note.getContent());
                    noteToUpdate.setTitle(note.getTitle());
                }, () -> {throw new NoSuchElementException();});
    }

    public Note getById(long id) {
        return listNote.stream()
                .filter(note -> note.getId() == id)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
