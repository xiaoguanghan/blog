package app.blog.service.impl;

import java.util.Date;
import java.util.List;

import com.blade.ioc.annotation.Component;
import com.blade.jdbc.AR;

import app.blog.model.Post;
import app.blog.service.PostService;
import blade.kit.StringKit;

@Component
public class PostServiceImpl implements PostService {
	
	private String insertSql = "insert into t_post (title, content, view_count, create_time) values (?,?,?,?)";
	
	@Override
	public boolean savePost(final String title, final String content) {
		return AR.update(insertSql, title, content, 0, new Date()).commit() > 0;
	}

	@Override
	public boolean updatePost(final Long id, final String title, final String content) {
		return AR.update("update t_post set title = ? and content = ? where id = ?",title, content, id).commit() > 0;
	}

	@Override
	public boolean deletePost(final Long id) {
		return AR.update("delete from t_post where id = ?",id).commit() > 0;
	}

	@Override
	public List<Post> getPostList(String title, Integer page, Integer count) {
		if(null == page || page < 1){
			page = 1;
		}
		if(null == count || count < 1){
			count = 10;
		}
		page = page - 1;
		
		if(StringKit.isNotBlank(title)){
			title = "%" + title + "%";
			return AR.find("where title like ? order by id desc limit ?,?", title, page, count).list(Post.class);
		}
		return AR.find("order by id desc limit ?,?", page, count).list(Post.class);
	}

}
