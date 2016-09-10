package webcrawler;

import java.util.Collection;
import java.util.List;

import javax.transaction.TransactionManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.Data;

@Data
@Repository
public class UrlDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	
	public UrlModel getBatchById(int id) {
		Session session = sessionFactory.openSession();
		List<UrlModel> result = session.createQuery("from UrlModel u where u.id = :id")
								.setParameter("id", id)
								.getResultList();
		return result.get(0);
	}
	
	public List<UrlModel> list() {
		Session session = sessionFactory.openSession();
		List<UrlModel> list = session.createQuery("from UrlModel").getResultList();
		session.close();
		return list;
	}
	
	public void batchInsertUrls(Collection<String> urls) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		urls.stream()
			.map(url -> new UrlModel(url))
			.forEach(urlModel -> session.save(urlModel));
		session.flush();
		transaction.commit();
		session.close();
	}
	
	
	public void isNull() {
		System.out.println(sessionFactory == null);
	}
	
	
}
 