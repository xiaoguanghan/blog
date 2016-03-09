package app.blog.controller;

import java.util.HashMap;
import java.util.Map;

import com.blade.view.ModelAndView;

public class BaseController {

	public ModelAndView getView(String view){
		return getView(new HashMap<String, Object>(), view);
	}
	
	public ModelAndView getView(Map<String, Object> map, String view){
		return new ModelAndView(map, "template/" + view + ".html");
	}
	
}
