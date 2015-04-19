package info.smartkit.yolo_adventure.controller;

import java.util.List;

import info.smartkit.yolo_adventure.model.Idea;
import info.smartkit.yolo_adventure.service.IdeaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller	
@RequestMapping("/")
public class IdeaController {

    @Autowired
    private IdeaService service;

    // Controller mappings
    
    @RequestMapping(method = RequestMethod.GET)
    public String viewIdeas() {
        return "ideas";
    }
    
    @RequestMapping(value = "/ideas", method = RequestMethod.GET)
    public @ResponseBody List getIdeas() {
        return service.getIdeas();
    }

    @NotifyClients
    @RequestMapping(value = "/ideas/{id}", method = RequestMethod.PUT)
    public @ResponseBody Idea update(@PathVariable int id, @RequestBody Idea idea) {
        idea.setId(id);
        Idea out = service.updateIdea(idea);
        return out;
    }

    @NotifyClients
    @RequestMapping(value = "/ideas", method = RequestMethod.POST)
    public @ResponseBody Idea add(@RequestBody Idea idea) {
        Idea out = service.addIdea(idea);
        return out;
    }

    @NotifyClients
    @RequestMapping(value = "/ideas/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        Idea task = new Idea();
        task.setId(id);
        service.deleteIdea(task);
    }
}
