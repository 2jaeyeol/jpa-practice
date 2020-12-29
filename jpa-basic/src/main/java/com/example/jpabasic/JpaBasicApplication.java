package com.example.jpabasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

@SpringBootApplication
public class JpaBasicApplication {
	public static void main(String[] args) {
//		SpringApplication.run(JpaBasicApplication.class, args);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
		Member findMember = em.find(Member.class, 1L);
		System.out.println(findMember.getId());
			List<Member> result = em.createQuery("select m from Member as m",Member.class)
					.getResultList();
			for (Member member : result) {
				System.out.println();
			}

			tx.commit();
		}catch (Exception e){
			tx.rollback();
		}finally {
			em.close();
		}

		emf.close();
	}
}