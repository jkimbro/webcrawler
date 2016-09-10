package webcrawler;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Crawler {
	
	SessionFactory sessionFactory;
	
	public static void main(String[] args) {
		new Crawler().run();
	}
	
	public void run() {
		System.out.println("ap");
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring.xml");
		sessionFactory = (SessionFactory) ctx.getBean("SessionFactory");
		Session s = sessionFactory.openSession();
		List<UrlModel> list = s.createQuery("from UrlModel u").getResultList();
		System.out.println(list.size());
		UrlModel uu = list.get(0);
		System.out.println(uu.getUrl());
		
		
		
		s.close();
		sessionFactory.close();
		System.exit(0);
	}

}
