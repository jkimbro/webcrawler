package webcrawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class Crawler {
	
	@Autowired
	UrlDao dao;


	public static void main(String[] args) throws BeansException, IOException {
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath*:spring.xml");
		System.out.println(((Crawler)ctx.getBean("crawler"))== null);
		((Crawler)ctx.getBean("crawler")).run();
	}
	
	public void run() throws IOException {
		System.out.println("ap");
		List<UrlModel> list = dao.list();
		UrlModel m = list.get(0);
		Map<String, Boolean> visited = new HashMap<String, Boolean>();
		list.stream().forEach(i -> visited.put(i.getUrl(), true));
		Queue<String> q = new LinkedList<String>();
		q.add(m.getUrl());
		int cnt = 0;
		while(cnt < 100) {
			cnt++;
			String url = q.remove();
			System.out.println(url + Integer.toString(q.size()));
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			links.stream().map(link -> link.attr("abs:href")).filter(i -> !visited.containsKey(i)).forEach(i -> q.add(i));
			
		}
        
		//dao.batchInsertUrls(Arrays.asList("https://www.wikipedia.org", "https://www.yahoo.com"));

		//sessionFactory = (SessionFactory) ctx.getBean("SessionFactory");
		//Session s = sessionFactory.openSession();
		//List<UrlModel> list = s.createQuery("from UrlModel u").getResultList();
		//System.out.println(list.size());
		//UrlModel uu = list.get(0);
		//System.out.println(uu.getUrl());
		//System.out.println(sessionFactory == null);
		
		//s.close();
		//sessionFactory.close();
		System.exit(0);
	}

}
