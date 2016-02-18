package app.blog.service;

import java.util.List;

import app.blog.model.Post;

public interface PostService {

	boolean savePost(String title, String content);
	
	boolean updatePost(Long id, String title, String content);
	
	boolean deletePost(Long id);
	
	List<Post> getPostList(String title, Integer page, Integer count);
	
}