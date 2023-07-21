package springbootmvc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import springbootmvc.model.entity.Note;
import springbootmvc.model.service.NoteService;

import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/note")
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getListOfNotes() {
        ModelAndView modelAndView = new ModelAndView("list");
        modelAndView.addObject("notes", noteService.listAll());
        return modelAndView;
    }

    @PostMapping(value = "/delete", consumes = "application/x-www-form-urlencoded")
    public ModelAndView deleteNoteById(@RequestParam Map<String, String> reqParams) {
        noteService.deleteById(Long.parseLong(reqParams.get("id")));
        ModelAndView modelAndView = new ModelAndView("redirect:/note/list");
        modelAndView.addObject("notes", noteService.listAll());
        return modelAndView;
    }

    @GetMapping("/edit")
    public ModelAndView showEditPage(@RequestParam("id") long id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("note", noteService.getById(id));
        return modelAndView;
    }

    @PostMapping(value = "/edit", consumes = "application/x-www-form-urlencoded")
    public ModelAndView editNote(@RequestParam Map<String, String> reqParams) {
        Note note = new Note(Long.parseLong(reqParams.get("id")), reqParams.get("title"),reqParams.get("content"));
        noteService.update(note);
        ModelAndView modelAndView = new ModelAndView("redirect:/note/list");
        modelAndView.addObject("notes", noteService.listAll());
        return modelAndView;
    }
}
