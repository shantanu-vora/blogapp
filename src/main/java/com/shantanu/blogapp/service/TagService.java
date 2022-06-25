package com.shantanu.blogapp.service;

import com.shantanu.blogapp.entity.Tag;

public interface TagService {
	String saveTag(Tag tag);
	String updateTag(Tag tag);
	Tag getTagByName(String name);
}
