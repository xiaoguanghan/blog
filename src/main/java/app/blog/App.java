package app.blog;

import static com.blade.Blade.me;

import com.blade.Blade;
import com.blade.Bootstrap;
import com.blade.jdbc.DB;
import com.bladejava.view.template.JetbrickTemplateEngine;

public class App extends Bootstrap {
	
	@Override
	public void init(Blade blade) {
		blade.basePackage("app.blog");
		blade.viewEngin(new JetbrickTemplateEngine());
		DB.open("com.mysql.jdbc.Driver", "jdbc:mysql://127.0.0.1/blog", "root", "root", true);
	}
	
	public static void main(String[] args) {
		Blade blade = me();
		blade.app(new App()).listen(9000).start();
	}
	
}