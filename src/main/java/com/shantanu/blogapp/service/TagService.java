package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Tag;
import java.util.List;

public interface TagService {
	void saveTag(Tag tag);
	Tag getTagByName(String name);
	List<Tag> getAllTags();
}
