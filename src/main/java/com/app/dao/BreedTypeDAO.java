package com.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dto.BeerdTypeDTO;
import com.app.dto.BreedTypeResponseDTO;
import com.app.entity.Agent;
import com.app.entity.BreedType;
import com.app.enumeration.Status;



@Repository
public class BreedTypeDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT bt.id,bt.breed_type,bt.`status` \r\n"
				+ "FROM tb_breed_type bt WHERE bt.`status`='ACTIVE'"
				+ "ORDER BY bt.breed_type ASC";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BeerdTypeDTO> list = new ArrayList<BeerdTypeDTO>();
		BeerdTypeDTO obj = null;
		for (Object[] row : rows) {
			obj = new BeerdTypeDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedType(row[1].toString());
			obj.setStatus(Status.valueOf(row[2].toString()));
			list.add(obj);
		}
		
		return list;
	}

	public Object findById(UUID id) {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT bt.id AS id,bt.breed_type,bt.breed_count,b.id AS breedId,b.breed_name AS breedName,p.id AS poultry_id,\r\n"
				+ "p.poultry_name AS poultryName FROM tb_breed_type bt \r\n"
				+ "	INNER JOIN tb_breed b  ON b.id =bt.breed_id AND bt.id='"+id+"'"
				+ "INNER  JOIN tb_poultry p ON p.id=bt.poultry_id" ;

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BreedTypeResponseDTO> list = new ArrayList<BreedTypeResponseDTO>();
		BreedTypeResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new BreedTypeResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedType(row[1].toString());
			obj.setBreedcount(row[2].toString());
			obj.setBreedId(UUID.fromString(row[3].toString()));
			obj.setBreedName(row[4].toString());
			obj.setPoultryId(UUID.fromString(row[5].toString()));
			obj.setPoultryName(row[6].toString());
			list.add(obj);
		}
		
		return list;
	
	}

	public List<BreedTypeResponseDTO> findByBreedId(UUID breedId) {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT bt.id AS id,bt.breed_type,bt.breed_count,b.id AS breedId,b.breed_name AS breedName,p.id AS poultry_id,"
				+ "p.poultry_name AS poultryName FROM tb_breed_type bt "
				+ "	INNER JOIN tb_breed b  ON b.id =bt.breed_id AND b.id='"+breedId+"'"
				+ "INNER  JOIN tb_poultry p ON p.id=bt.poultry_id" ;

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BreedTypeResponseDTO> list = new ArrayList<BreedTypeResponseDTO>();
		BreedTypeResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new BreedTypeResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedType(row[1].toString());
			obj.setBreedcount(row[2].toString());
			obj.setBreedId(UUID.fromString(row[3].toString()));
			obj.setBreedName(row[4].toString());
			obj.setPoultryId(UUID.fromString(row[5].toString()));
			obj.setPoultryName(row[6].toString());
			list.add(obj);
		}
		
		return list;
	
	}

	public Object findByPoultryId(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT bt.id AS id,bt.breed_type,bt.breed_count,b.id AS breedId,b.breed_name AS breedName,p.id AS poultry_id,"
				+ "p.poultry_name AS poultryName "
				+ "FROM tb_breed_type bt "
				+ "	INNER JOIN tb_breed b  ON b.id =bt.breed_id AND bt.poultry_id='"+poultryId+"'"
				+ "INNER  JOIN tb_poultry p ON p.id=bt.poultry_id" ;

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BreedTypeResponseDTO> list = new ArrayList<BreedTypeResponseDTO>();
		BreedTypeResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new BreedTypeResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedType(row[1].toString());
			obj.setBreedcount(row[2].toString());
			obj.setBreedId(UUID.fromString(row[3].toString()));
			obj.setBreedName(row[4].toString());
			obj.setPoultryId(UUID.fromString(row[5].toString()));
			obj.setPoultryName(row[6].toString());
			list.add(obj);
		}
		
		return list;
	
	}

	public BreedType findByNameExcludeId(String breedType, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(BreedType.class);
		crit.add(Restrictions.eq("breedType", breedType));
		crit.add(Restrictions.ne("id", id));
		List<BreedType> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
		
	}
	
	
     

	
	


