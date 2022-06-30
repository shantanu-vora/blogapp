package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Tag;
import com.shantanu.blogapp.repository.TagRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagRepository tagRepository;

	@Override
	public String saveTag(Tag tag) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		try {
			List<String> tagsList = Arrays.asList(tag.getName().split(","));
			tag.setCreatedAt(currentTimestamp);
			tagRepository.save(tag);
		} catch(ConstraintViolationException | DataIntegrityViolationException e) {
			return "redirect:/post/newPost";
		}
		return null;
	}

	@Override
	public String updateTag(Tag tag) {
		Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
		try {
			tag.setUpdatedAt(currentTimestamp);
			tagRepository.save(tag);
		} catch(ConstraintViolationException | DataIntegrityViolationException e) {
			return "redirect:/post/" + tag.getId();
		}
		return "redirect:/post/" + tag.getId();
	}

	@Override
	public Tag getTagByName(String name) {
		return tagRepository.getTagByName(name);
	}

	@Override
	public List<Tag> getAllTags() {
		return tagRepository.findAll();
	}
}