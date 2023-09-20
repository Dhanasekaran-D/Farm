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

import com.app.dto.BreedDTO;
import com.app.dto.BreedResponseDTO;
import com.app.entity.Breed;
import com.app.entity.Country;
import com.app.entity.StateEntity;
import com.app.enumeration.Status;

@Repository
public class BreedDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT b.id as id, b.breed_name, b.descriptions, bt.id as breedTypeId, bt.breed_type, b.status\r\n"
				+ "FROM tb_breed b\r\n"
				+ "INNER JOIN tb_breed_type bt ON bt.id = b.breed_type_id\r\n"
				+ "WHERE b.status = 'ACTIVE'\r\n"
				+ "ORDER BY b.breed_name ASC";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BreedResponseDTO> list = new ArrayList<BreedResponseDTO>();
		BreedResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new BreedResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedName(row[1].toString());
			obj.setDescriptions(row[2].toString());
			obj.setBreedTypeId(UUID.fromString(row[3].toString()));
			obj.setBreedTypeName(row[4].toString());
			obj.setStatus(Status.valueOf(row[5].toString()));
			list.add(obj);
		}
		return list;
	}

	
	public Object findById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT b.id as id,b.breed_name, b.descriptions,bt.id as breedTypeId,bt.breed_type,b.status  "
				+ "FROM tb_breed b INNER JOIN tb_breed_type bt ON bt.id=b.breed_type_id WHERE b.id='" + id + "' ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BreedResponseDTO> list = new ArrayList<BreedResponseDTO>();
		BreedResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new BreedResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedName(row[1].toString());
			obj.setDescriptions(row[2].toString());
			obj.setBreedTypeId(UUID.fromString(row[3].toString()));
			obj.setBreedTypeName(row[4].toString());
			obj.setStatus(Status.valueOf(row[5].toString()));
			list.add(obj);
		}
		return list;
	}

	public Object findByPoultryId(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT b.id as id,b.breed_name as breedName,p.id as poultryId,p.poultry_name as poultryName "
				+ "FROM tb_breed b INNER JOIN tb_poultry p " + "ON b.poultry_id=p.id AND p.id='" + poultryId + "'";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BreedResponseDTO> list = new ArrayList<BreedResponseDTO>();
		BreedResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new BreedResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedName(row[1].toString());
			
			list.add(obj);
		}
		return list;
	}


	public Object getAllBreed(String poultryName,String breedName ) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery ="SELECT b.id, b.breed_name, bt.breed_type, bt.breed_count, p.poultry_name, p.address, p.phone_no, d.district_name, s.state_name, c.name\r\n"
				+ "FROM tb_breed b\r\n"
				+ "INNER JOIN tb_poultry p ON b.poultry_id = p.id\r\n"
				+ "INNER JOIN tb_breed_type bt ON b.id = bt.breed_id\r\n"
				+ "INNER JOIN tb_district d ON p.district_id = d.id\r\n"
				+ "INNER JOIN tb_state s ON d.state_id = s.id\r\n"
				+ "INNER JOIN tb_country c ON d.country_id = c.id\r\n"
				+ "WHERE p.poultry_name LIKE '%"+poultryName+"%' AND b.breed_name LIKE '%"+breedName+"%'";
	
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<BreedDTO> list = new ArrayList<BreedDTO>();
		BreedDTO obj = null;
		for (Object[] row : rows) {
			obj = new BreedDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setBreedName(row[1].toString());
			obj.setBreedType(row[2].toString());
			obj.setBreedCount(Integer.valueOf(row[3].toString()));
			obj.setPoultryName(row[4].toString());
			obj.setAddress(row[5].toString());
			obj.setPhoneNo(row[6].toString());
			obj.setDistrictName(row[7].toString());
			obj.setStateName(row[8].toString());
			obj.setCountryName(row[9].toString());
			
			list.add(obj);
		}
		return list;
	
	}


	public Breed findByNameExcludeId(String breedName, UUID id, UUID breedTypeId) {
			Session session = sessionFactory.getCurrentSession();
			Criteria crit = session.createCriteria(Breed.class);
			crit.add(Restrictions.eq("breedName", breedName));
			crit.add(Restrictions.eq("breedTypeId", breedTypeId));
			crit.add(Restrictions.ne("id", id));
			List<Breed> list = crit.list();
			if (null != list && !list.isEmpty()) {
				return list.get(0);
			} else {

			}
			return null;
		
	}
}
