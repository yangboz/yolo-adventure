package info.smartkit.yolo_adventure.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import info.smartkit.yolo_adventure.dto.IdeaDto;
import info.smartkit.yolo_adventure.model.Idea;
import info.smartkit.yolo_adventure.repository.IdeaRepository;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

public class IdeaServiceImpl implements IdeaService {

    @Autowired
    private IdeaRepository repo;

    @Autowired
    private Mapper mapper;

    public List getIdeas() {
        List list = repo.findAll();
        List out = new ArrayList();
        for (IdeaDto dto : list) {
            out.add(mapper.map(dto, Idea.class));
        }
        return out;
    }

    @Transactional
    public Idea addIdea(Idea idea) {
        IdeaDto dto = mapper.map(idea, IdeaDto.class);
        return mapper.map(repo.saveAndFlush(dto), Idea.class);
    }

    @Transactional
    public Idea updateIdea(Idea idea) {
        IdeaDto dto = (IdeaDto) repo.findOne(idea.getId());
        dto.setDescription(idea.getDescription());
        dto.setTitle(idea.getTitle());
        dto.setVotes(idea.getVotes());
        return mapper.map(repo.saveAndFlush(dto), Idea.class);
    }

    @Transactional
    public void deleteIdea(Idea idea) {
        repo.delete(idea.getId());
    }
}
