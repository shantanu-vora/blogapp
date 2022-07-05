package com.shantanu.blogapp.repository;

import com.shantanu.blogapp.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	@Query(value="SELECT * FROM post WHERE id IN (SELECT p.id FROM post p JOIN post_tag pt ON p.id = pt.post_id JOIN tag t ON t.id = pt.tag_id WHERE p.is_published = true AND ((LOWER(t.name) LIKE %?1%) OR LOWER(p.title) LIKE %?1% OR LOWER(p.content) LIKE %?1% OR LOWER(p.author) LIKE %?1%))", nativeQuery = true)
	Page<Post> findBySearchKeyword(Pageable pageable, String search);

	@Query(value="SELECT * FROM post WHERE id IN (SELECT p.id FROM post p JOIN post_tag pt ON p.id = pt.post_id JOIN tag t ON t.id = pt.tag_id WHERE p.is_published = ?2 and ((LOWER(t.name) like %?1%) or LOWER(p.title) like %?1% or LOWER(p.content) like %?1% or LOWER(p.author) like %?1%))", nativeQuery = true)
	Page<Post> findBySearchKeyword(Pageable pageable, String search, Boolean isPublished);

	@Query(value="SELECT * FROM post WHERE id IN " +
					"(SELECT p.id FROM post p JOIN post_tag pt ON p.id = pt.post_id" +
					" JOIN tag t ON t.id = pt.tag_id WHERE p.is_published = true and" +
					" ((LOWER(t.name) like %:search% or LOWER(p.title) like %:search% or LOWER(p.content) like %:search% or" +
					" LOWER(p.author) like %:search%)) and t.id IN :tagIdList)", nativeQuery = true)
	Page<Post> findByFilteredTags(Pageable pageable,
																@Param("search") String search,
																@Param("tagIdList") List<Integer> tagIdList);
}
