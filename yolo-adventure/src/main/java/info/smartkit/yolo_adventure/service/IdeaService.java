package info.smartkit.yolo_adventure.service;

import info.smartkit.yolo_adventure.model.Idea;

import java.util.List;

import javax.transaction.Transactional;

public interface IdeaService {

    List getIdeas();

    @Transactional
    Idea addIdea(Idea idea);

    @Transactional
    Idea updateIdea(Idea idea);

    @Transactional
    void deleteIdea(Idea idea);
}
