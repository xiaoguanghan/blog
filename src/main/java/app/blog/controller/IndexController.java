package app.blog.controller;

import java.util.List;

import com.blade.ioc.annotation.Inject;
import com.blade.route.annotation.Path;
import com.blade.route.annotation.Route;
import com.blade.view.template.ModelAndView;
import com.blade.web.http.HttpMethod;
import com.blade.web.http.Request;
import com.blade.web.http.Response;

import app.blog.model.Post;
import app.blog.service.PostService;

@Path("/")
public class IndexController extends BaseController{
	
	@Inject
	private PostService postService;
	
	@Route("/")
	public ModelAndView home(Request request, Response response){
		return this.homePage(request, response);
	}
	
	@Route("/about")
	public ModelAndView about(Request request, Response response){
		return this.getView("about");
	}
	
	@Route(method = HttpMethod.GET, value = "/publish")
	public ModelAndView show_publish(Request request, Response response) {
		return this.getView("publish");
	}
	
	@Route(method = HttpMethod.POST, value = "/publish")
	public void publish(Request request, Response response) {
		
	}
	
	@Route("/:page")
	public ModelAndView homePage(Request request, Response response){
		Integer page = request.paramAsInt("page");
		String title = request.query("title");
		List<Post> posts = postService.getPostList(title, page, 5);
		request.attribute("posts", posts);
		return this.getView("index");
	}
	
	@Route("/:pid")
	public ModelAndView postDetail(Request request, Response response){
		return this.getView("detail");
	}
	
}
