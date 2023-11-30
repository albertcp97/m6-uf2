package prubea;

import java.util.ArrayList;import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import model.Asignatura;
import model.Departamento;
import model.Estudiante;
import model.Pasaporte;
import model.Persona;

public class main2 {
	
	static Session session;
	static SessionFactory sessionFactory;
	static ServiceRegistry serviceRegistry;
	
	public static synchronized SessionFactory getSessionFactory () {
		if (sessionFactory == null) {

			// exception handling omitted for brevityaa

			serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg2.xml").build();

			sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
		}
		return sessionFactory;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			session = getSessionFactory().openSession();
			
			Estudiante e1= new Estudiante("e1", new ArrayList());
			Estudiante e2= new Estudiante("e2", new ArrayList());
			Estudiante e3= new Estudiante("e3", new ArrayList());
			
			Asignatura a1 =new Asignatura("mates", new ArrayList<>());
			Asignatura a2 =new Asignatura("catalan", new ArrayList<>());
			Asignatura a3 =new Asignatura("sociales", new ArrayList<>());
			
			e1.getCursos().add(a1);
			e1.getCursos().add(a3);
			
			e2.getCursos().add(a1);
			e2.getCursos().add(a2);
			e2.getCursos().add(a3);
			
			e3.getCursos().add(a3);			
			
			// abrimos PRIMERA transaccion. Eso se hace siempre.
			session.beginTransaction();
			
			session.saveOrUpdate(e1);	
			session.saveOrUpdate(e2);	
			session.saveOrUpdate(e3);	
			
			session.saveOrUpdate(a1);	
			session.saveOrUpdate(a2);	
			session.saveOrUpdate(a3);	
			
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


