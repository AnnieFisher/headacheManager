package data;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import entities.Headache;
import entities.User;

@Transactional
public class TrackerDAO {

	@PersistenceContext
	private EntityManager em;
	
	public List<User> indexUser() {
		String query = "Select u from User u";
		return em.createQuery(query, User.class).getResultList();
	}
	
	public User showUser(int id) {
		return em.find(User.class, id);
	}
	public List<Headache> indexHeadache() {
		String query = "Select h from Headache h";
		return em.createQuery(query, Headache.class).getResultList();
	}
	public Collection<Headache> showHeadache(int id) {
		User user = null;
		try{
			user = em.find(User.class, id);
		}catch(Exception e) {
			e.printStackTrace();
		}
		if(user !=null){
			return user.getHeadaches();
		}
		return null;
	}

	public void destroyUser(int id) {
		User user = em.find(User.class, id);
		em.remove(user);
	}
	public void destroyHeadache(int id, int hId) {
		
		String query = "Delete from Headache h where id=:hId";
		em.createQuery(query).setParameter("hId",hId).executeUpdate();
		
	}
	public void createHeadache(int id, Headache headache) {
		User user = em.find(User.class, id);
		headache.setUser(user);
		em.persist(headache);
		em.flush();
	}
	public void create(User user) {
		em.persist(user);
		em.flush();
	}
	public Headache update(int id, Headache headache) {
		User managedUser = em.find(User.class, id);
		headache.setUser(managedUser);
		Headache managedHeadache = em.find(Headache.class,id);
		managedHeadache.setAmtSleep(headache.getAmtSleep());
		managedHeadache.setDuration(headache.getDuration());
		managedHeadache.setOnset(headache.getOnset());
		managedHeadache.setPainScale(headache.getPainScale());
		managedHeadache.setSymptoms(headache.getSymptoms());
		
		
		return managedHeadache;
	}
	public User update(int id, User user) {
		User updatedUser = em.find(User.class, id);
		updatedUser.setAmtDay(user.getAmtDay());
		updatedUser.setAmtWeek(user.getAmtWeek());
		updatedUser.setAvgPainScale(user.getAvgPainScale());
		updatedUser.setAvgDuration(user.getAvgDuration());
		updatedUser.setOverallSymptoms(user.getOverallSymptoms());
		em.merge(updatedUser);
		return updatedUser;
	}
	
}
