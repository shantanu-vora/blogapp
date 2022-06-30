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

//	@Query(value="select * from post where id in (select p.id from post p join post_tag pt on p.id = pt.post_id join tag t on t.id = pt.tag_id where lower(t.name) like %?1%)", nativeQuery = true)
	@Query(value="select * from post where id in (select p.id from post p join post_tag pt on p.id = pt.post_id join tag t on t.id = pt.tag_id where p.is_published = true and ((lower(t.name) like %?1%) or lower(p.title) like %?1% or lower(p.content) like %?1% or lower(p.author) like %?1%))", nativeQuery = true)
	Page<Post> findByKeyword(Pageable pageable, String keyword);

	@Query(value="select * from post where id in (select p.id from post p join post_tag pt on p.id = pt.post_id join tag t on t.id = pt.tag_id where t.id in :tagIdList)", nativeQuery = true)
	Page<Post> findByFilteredTags(Pageable pageable, @Param("tagIdList") List<Integer> tagIdList);


}
