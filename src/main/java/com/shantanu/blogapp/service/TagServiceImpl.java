package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagRepository tagRepository;

	@Override
	public void saveTag(Tag tag) {
		tagRepository.save(tag);
	}
}
