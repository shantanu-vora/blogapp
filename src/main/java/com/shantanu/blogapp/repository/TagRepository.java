package com.shantanu.blogapp.repository;

import com.shantanu.blogapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
	Tag getTagByName(String name);
}
