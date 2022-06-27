package com.shantanu.blogapp.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "title")
	private String title;

	@Column(name = "excerpt")
	private String excerpt;

	@Column(name = "content")
	private String content;

	@Column(name = "author")
	private String author;

	@Column(name = "published_at")
	private Timestamp publishedAt;

	@Column(name = "is_published")
	private boolean isPublished;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="post_tag",
						 joinColumns=@JoinColumn(name="post_id"),
						 inverseJoinColumns=@JoinColumn(name="tag_id")
						)
	private List<Tag> tags = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="post_id")
	private List<Comment> comments;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExcerpt() {
		return excerpt;
	}

	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Timestamp getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(Timestamp publishedAt) {
		this.publishedAt = publishedAt;
	}

	public boolean isPublished() {
		return isPublished;
	}

	public void setPublished(boolean published) {
		isPublished = published;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		if(comments == null) {
			comments = new ArrayList<>();
		}
		comments.add(comment);
	}

	public Comment getCommentById(int commentId) {
		System.out.println(this.getId());
		Comment theComment = null;
		for(Comment comment: comments) {
			System.out.println(comment.getId());
			if(comment.getId() == commentId) {
				theComment= comment;
			}
		}
		return theComment;
	}

	@Override
	public String toString() {
		return "Post{" +
						"id=" + id +
						", title='" + title + '\'' +
						", excerpt='" + excerpt + '\'' +
						", content='" + content + '\'' +
						", author='" + author + '\'' +
						", publishedAt=" + publishedAt +
						", isPublished=" + isPublished +
						", createdAt=" + createdAt +
						", updatedAt=" + updatedAt +
						", tags=" + tags +
						", comments=" + comments +
						'}';
	}
}