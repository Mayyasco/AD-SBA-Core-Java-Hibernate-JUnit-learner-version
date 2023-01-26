package sba.sms.services;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class StudentService {
	public void createStudent(Student student) {
		// ------------------------------------------------------------------
		Transaction tx = null;
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session = sessionfactory.openSession();
		try {

			tx = session.beginTransaction();
			session.merge(student);
			tx.commit();

		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
	}

	// ------------------------------------------------------------------
	public List<Student> getAllStudents() {

		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session = sessionfactory.openSession();
		List<Student> Ls = new LinkedList<Student>();
		try {
			Ls = session.createQuery("FROM Student", Student.class).getResultList();

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}

		return Ls;

	}

	// ------------------------------------------------------------------
	public Student getStudentByEmail(String email) {
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session = sessionfactory.openSession();
		Student s = null;
		try {
			s = session.get(Student.class, email);

		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}

		return s;

	}

	// ------------------------------------------------------------------
	public List<Course> getStudentCourses(String email) {
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session = sessionfactory.openSession();
		List<Course> Lc = new LinkedList<Course>();
		Student s = new Student();
		try {
			s = session.createQuery("FROM Student where email='" + email + "'", Student.class).getSingleResult();
			Lc = s.getCourses();
		} catch (HibernateException ex) {
			ex.printStackTrace();

		} finally {
			session.close();
		}

		return Lc;

	}

	// ------------------------------------------------------------------
	public boolean validateStudent(String email, String password) {
		Student s = getStudentByEmail(email);
		if (s == null)
			return false;
		if (!s.getPassword().equals(password))
			return false;
		return true;
	}

	// ------------------------------------------------------------------
	public void registerStudentToCourse(String email, int courseId) {
		Transaction tx = null;
		SessionFactory sessionfactory = HibernateUtil.getSessionFactory();
		Session session = sessionfactory.openSession();
		try {
			Student s = session.get(Student.class, email);
			Course c = session.get(Course.class, courseId);
			if (s.getCourses().contains(c)) {
				//System.out.println("already register in this Course");
			} else {
				tx = session.beginTransaction();
				s.addCourse(c);
				session.merge(s);
				tx.commit();
			}

		} catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
	}
	// ------------------------------------------------------------------
}
