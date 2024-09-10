package com.petproject.ordermanagmentsystem.services;

import com.petproject.ordermanagmentsystem.models.Tag;
import com.petproject.ordermanagmentsystem.repositories.TagRepository;
import com.petproject.ordermanagmentsystem.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TagService {
    private final TagRepository tagRepository;
    
    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Transactional
    public void createNewTag(Tag tag){
        tagRepository.save(tag);
    }

    public Tag getTagById(int id){
        return tagRepository.findById(id).orElse(null);
    }

    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    @Transactional
    public Tag updateTagById(int id, Tag tag) {
        if(tagRepository.existsById(id)){
            tag.setId(id);
            return tagRepository.save(tag);
        }
        else throw new ResourceNotFoundException("Tag with id "+ id + " does not exist!");
    }

    @Transactional
    public void deleteTagById(int id) {
        if(tagRepository.existsById(id)){
            tagRepository.deleteById(id);
        }
        else throw new ResourceNotFoundException("Tag with id "+ id + " does not exist!");
    }


}
