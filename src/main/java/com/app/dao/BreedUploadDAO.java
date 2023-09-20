package com.app.dao;

import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class BreedUploadDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Object getAll(UUID breedId) {
			Session session = sessionFactory.getCurrentSession();
			BreedDAO obj = session.get(BreedDAO.class, breedId);
			return obj;
		};
	}


