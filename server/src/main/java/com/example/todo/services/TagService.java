package com.example.todo.services;

import com.example.todo.entities.Tag;
import com.example.todo.repositories.TagRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public Tag getOrCreateByName(String name) {
        return tagRepository.findByName(name).orElseGet(() -> tagRepository.save(createTag(name)));
    }

    private Tag createTag(String name) {
        var tag = new Tag();
        tag.setName(name);
        return tag;
    }

}
