package sba.sms.services;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import sba.sms.models.Course;
import sba.sms.utils.HibernateUtil;

public class CourseService  {
	//-----------------------------------------------------------------
	public void createCourse(Course course) {

		Transaction tx = null;
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session=sessionfactory.openSession();
		try {

			tx = session.beginTransaction();
			session.merge(course);
			tx.commit();

		} catch(HibernateException ex){
			ex.printStackTrace();
			tx.rollback();
		}finally {
			session.close();
		}
	}
	//------------------------------------------------------------------
	public Course getCourseById(int courseId) {
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session=sessionfactory.openSession();
		Course c=null;
		try {
		 c = session.get(Course.class, courseId);
		
		} catch(
		HibernateException ex){
			ex.printStackTrace();
			
			
		}finally {
			session.close();
		}
		
		return c;
		
	}
	//------------------------------------------------------------------
	public	List<Course> getAllCourses() {
		
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session=sessionfactory.openSession();
		List<Course> Lc = new LinkedList<Course>();
		try {
			Lc = session.createQuery("FROM Course",Course.class).getResultList();
		
		} catch(
		HibernateException ex){
			ex.printStackTrace();
			
			
		}finally {
			session.close();
		}
		
		return Lc;
		
	}
	//------------------------------------------------------------------

}
