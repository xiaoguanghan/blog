package app.blog.service.impl;

import java.util.Date;

import com.blade.ioc.annotation.Component;
import com.blade.jdbc.AR;
import com.blade.jdbc.Page;
import com.blade.jdbc.QueryParam;

import app.blog.model.Post;
import app.blog.service.PostService;
import blade.kit.StringKit;

@Component
public class PostServiceImpl implements PostService {
	
	private String insertSql = "insert into t_post (title, content, view_count, create_time) values (?,?,?,?)";
	
	@Override
	public boolean savePost(final String title, final String content) {
		int result = AR.update(insertSql, title, content, 0, new Date()).executeUpdate();
		return result > 0;
	}

	@Override
	public Page<Post> getPostList(String title, Integer page, Integer count) {
		
		if(null == page || page < 1){
			page = 1;
		}
		
		if(null == count || count < 1){
			count = 10;
		}
		
		QueryParam queryParam = QueryParam.me();
		if(StringKit.isNotBlank(title)){
			queryParam.like("title", "%" + title + "%");
		}
		queryParam.orderby("id desc").page(page, count);
		
		return AR.find(queryParam).page(Post.class);
	}

}
