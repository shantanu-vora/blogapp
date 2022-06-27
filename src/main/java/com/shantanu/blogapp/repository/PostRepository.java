package com.shantanu.blogapp.repository;

import com.shantanu.blogapp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query(value="select * from post p where upper(p.title) like %:keyword% " +
					" or upper(p.content) like %:keyword% or upper(p.author) like %:keyword%", nativeQuery = true)
	List<Post> findByKeyword(@Param("keyword") String keyword);
}
