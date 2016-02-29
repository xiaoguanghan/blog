package app.blog.service;

import com.blade.jdbc.Page;

import app.blog.model.Post;

public interface PostService {

	boolean savePost(String title, String content);
	
	Page<Post> getPostList(String title, Integer page, Integer count);
	
}