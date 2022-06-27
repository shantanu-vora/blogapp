package com.shantanu.blogapp.repository;

import com.shantanu.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query(value="select * from post where is_published = true and (upper(title) like %?1% or upper(content) like %?1%)", nativeQuery = true)
	Page<Post> findByKeyword(Pageable pageable, String keyword);

//	@Query(value="select * from post where is_published = true", nativeQuery = true)
//	Page<Post> findPublishedPosts(Pageable pageable);

}
