package app.blog.service;

import java.util.List;

import app.blog.model.Post;

public interface PostService {

	boolean savePost(String title, String content);
	
	List<Post> getPostList(String title, Integer page, Integer count);
	
}