package app.blog.controller;

import com.blade.ioc.annotation.Inject;
import com.blade.jdbc.Page;
import com.blade.route.annotation.Path;
import com.blade.route.annotation.Route;
import com.blade.view.ModelAndView;
import com.blade.web.http.HttpMethod;
import com.blade.web.http.Request;
import com.blade.web.http.Response;

import app.blog.Const;
import app.blog.model.Post;
import app.blog.service.PostService;
import blade.kit.StringKit;

@Path("/")
public class IndexController extends BaseController{
	
	@Inject
	private PostService postService;
	
	/**
	 * 首页
	 */
	@Route("/")
	public ModelAndView home(Request request, Response response){
		return this.homePage(request, response);
	}
	
	/**
	 * 关于页面
	 */
	@Route("/about")
	public ModelAndView about(Request request, Response response){
		return this.getView("about");
	}
	
	/**
	 * 发布页面
	 */
	@Route(method = HttpMethod.GET, value = "/publish")
	public ModelAndView show_publish(Request request, Response response) {
		if(null == request.session().attribute(Const.SIGNIN_SESSION_KEY)){
			response.go("/signin");
			return null;
		}
		return this.getView("publish");
	}
	
	/**
	 * 发布操作
	 */
	@Route(method = HttpMethod.POST, value = "/publish")
	public void publish(Request request, Response response) {
		String title = request.query("title");
		String content = request.query("content");
		
		if(StringKit.isBlank(title) || StringKit.isBlank(content)){
			request.attribute("error", "标题和内容不能为空");
			response.render(this.getView("publish"));
			return;
		}
		
		boolean flag = postService.savePost(title, content);
		if(flag){
			response.go("/");
		} else {
			request.attribute("error", "发布文章失败");
			response.render(this.getView("publish"));
		}
	}
	
	/**
	 * 分页列表
	 */
	@Route("/page/:page")
	public ModelAndView homePage(Request request, Response response){
		Integer page = request.paramAsInt("page");
		String title = request.query("title");
		Page<Post> postPage = postService.getPostList(title, page, 5);
		request.attribute("postPage", postPage);
		return this.getView("index");
	}
	
	/**
	 * 登录页面
	 */
	@Route(method = HttpMethod.GET, value = "/signin")
	public ModelAndView showSignin(Request request, Response response){
		return this.getView("signin");
	}
	
	/**
	 * 登录操作
	 */
	@Route(method = HttpMethod.POST, value = "/signin")
	public void signin(Request request, Response response){
		String login_name = request.query("login_name");
		String password = request.query("password");
		
		if(StringKit.isBlank(login_name) || StringKit.isBlank(password)){
			request.attribute("error", "用户名和密码不能为空");
			response.render(this.getView("signin"));
			return;
		}
		
		if(!login_name.equals("admin") || !password.equals("admin")){
			request.attribute("error", "用户名或密码错误");
			response.render(this.getView("signin"));
			return;
		}
		
		// 登录成功放入session
		request.session().attribute(Const.SIGNIN_SESSION_KEY, true);
		
		response.go("/");
		
	}
	
	/**
	 * 注销操作
	 */
	@Route(method = HttpMethod.GET, value = "/signin_out")
	public void signinOut(Request request, Response response){
		// 登录成功放入session
		request.session().removeAttribute(Const.SIGNIN_SESSION_KEY);
		response.go("/");
	}
	
}