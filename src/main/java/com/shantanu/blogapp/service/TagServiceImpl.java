package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.repository.TagRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagRepository tagRepository;

	@Override
	public String saveTag(Tag tag) {

		try {
			tagRepository.save(tag);
		} catch(ConstraintViolationException | DataIntegrityViolationException e) {
			return "redirect:/post/newPost";
		}
		return null;
	}
}
