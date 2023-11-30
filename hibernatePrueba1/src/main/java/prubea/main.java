package prubea;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import model.Departamento;
import model.Pasaporte;
import model.Persona;

public class main {
	
	static Session session;
	static SessionFactory sessionFactory;
	static ServiceRegistry serviceRegistry;
	
	
	public static synchronized SessionFactory getSessionFactory() {
		if (sessionFactory == null) {

			// exception handling omitted for brevityaa

			serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

			sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		}
		return sessionFactory;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			session = getSessionFactory().openSession();
			Departamento info= new Departamento("Informatica", new ArrayList());
			Pasaporte pas= new Pasaporte("2132123fgd");
			Persona p= new Persona("Persona1", pas,info);

			info.getEmpleados().add(p);
			pas.setPersona(p);
			
			// abrimos PRIMERA transaccion. Eso se hace siempre.
			session.beginTransaction();
			session.saveOrUpdate(info);			
			session.saveOrUpdate(pas);
			session.saveOrUpdate(p);
			session.getTransaction().commit();

			System.out.println("todo ha salido a pedir de Milhouse");
			
		} catch (Exception sqlException) {
			sqlException.printStackTrace();
			if (null != session.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				session.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
