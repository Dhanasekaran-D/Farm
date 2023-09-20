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

import com.app.dto.PoultryResponse;
import com.app.entity.PoultryEntity;
import com.app.entity.PoultryMapping;


@Repository
public class PoultryDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<PoultryResponse> findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id,p.poultry_name,p.phone_no,p.address,d.id as districtId,d.district_name,s.id as stateId ,s.state_name ,"
				+ "c.id as countryId ,c.name"
				+ " FROM tb_poultry p "
				+ "INNER JOIN tb_district d ON d.id=p.district_id "
				+"INNER JOIN tb_state s ON s.id=d.state_id "
				+"INNER JOIN tb_country c ON c.id=d.country_id "
				+"WHERE p.status='ACTIVE'"
				+ "ORDER BY p.poultry_name ASC ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoultryResponse> list = new ArrayList<PoultryResponse>();
		PoultryResponse obj = null;
		for (Object[] row : rows) {
			obj = new PoultryResponse();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setPhoneNo(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setDistrictId(UUID.fromString(row[4].toString()));
			obj.setDistrictName(row[5].toString());
			obj.setStateId(UUID.fromString(row[6].toString()));
			obj.setStateName(row[7].toString());
			obj.setCountryId(UUID.fromString(row[8].toString()));
			obj.setCountryName(row[9].toString());
			

			list.add(obj);
		}
		return list;
	}

	public List<PoultryResponse> findByDistrictId(UUID districtId) {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id,p.poultry_name,p.phone_no,p.address,d.id as districtId,d.district_name,s.id as stateId ,s.state_name ,"
				+ "c.id as countryId ,c.name "
				+ " FROM tb_poultry p "
				+ "INNER JOIN tb_district d ON d.id=p.district_id AND d.id='"+districtId+"'"
				+"INNER JOIN tb_state s ON s.id=d.state_id "
				+"INNER JOIN tb_country c ON c.id=d.country_id ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoultryResponse> list = new ArrayList<PoultryResponse>();
		PoultryResponse obj = null;
		for (Object[] row : rows) {
			obj = new PoultryResponse();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setPhoneNo(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setDistrictId(UUID.fromString(row[4].toString()));
			obj.setDistrictName(row[5].toString());
			obj.setStateId(UUID.fromString(row[6].toString()));
			obj.setStateName(row[7].toString());
			obj.setCountryId(UUID.fromString(row[8].toString()));
			obj.setCountryName(row[9].toString());
			

			list.add(obj);
		}
		return list;
	}

	public List<PoultryResponse> findById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id,p.poultry_name,p.phone_no,p.address,d.id as districtId,d.district_name,s.id as stateId ,s.state_name ,"
				+ "c.id as countryId ,c.name "
				+ "FROM tb_poultry p "
				+ "INNER JOIN tb_district d ON d.id=p.district_id AND p.id='"+id+"' "
				+"INNER JOIN tb_state s ON s.id=d.state_id "
				+"INNER JOIN tb_country c ON c.id=d.country_id ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoultryResponse> list = new ArrayList<PoultryResponse>();
		PoultryResponse obj = null;
		for (Object[] row : rows) {
			obj = new PoultryResponse();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setPhoneNo(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setDistrictId(UUID.fromString(row[4].toString()));
			obj.setDistrictName(row[5].toString());
			obj.setStateId(UUID.fromString(row[6].toString()));
			obj.setStateName(row[7].toString());
			obj.setCountryId(UUID.fromString(row[8].toString()));
			obj.setCountryName(row[9].toString());
			

			list.add(obj);
		}
		return list;
	}
	public Object getAllPoultry(String key) {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id,p.poultry_name,p.phone_no,p.address,d.id as districtId,d.district_name,s.id as stateId ,s.state_name,"
				+ "c.id as countryId ,c.name "
				+ " FROM tb_poultry p "
				+ "INNER JOIN tb_district d ON d.id=p.district_id "
				+"INNER JOIN tb_state s ON s.id=d.state_id "
				+"INNER JOIN tb_country c ON c.id=d.country_id WHERE p.poultry_name LIKE '%"+key+"%'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoultryResponse> list = new ArrayList<PoultryResponse>();
		PoultryResponse obj = null;
		for (Object[] row : rows) {
			obj = new PoultryResponse();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setPhoneNo(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setDistrictId(UUID.fromString(row[4].toString()));
			obj.setDistrictName(row[5].toString());
			obj.setStateId(UUID.fromString(row[6].toString()));
			obj.setStateName(row[7].toString());
			obj.setCountryId(UUID.fromString(row[8].toString()));
			obj.setCountryName(row[9].toString());
			

			list.add(obj);
		}
		return list;
	}

	public PoultryEntity findByNameExcludeId(String poultryName, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(PoultryEntity.class);
		crit.add(Restrictions.eq("poultryName", poultryName));
		crit.add(Restrictions.ne("id", id));
		List<PoultryEntity> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	

	public PoultryEntity findByAddressExcludeId(String address, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(PoultryEntity.class);
		crit.add(Restrictions.eq("address", address));
		crit.add(Restrictions.ne("id", id));
		List<PoultryEntity> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}

	public PoultryEntity getAllPoultryId(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(PoultryEntity.class);
		//crit.add(Restrictions.eq("address", address));
		crit.add(Restrictions.ne("poultryId", poultryId));
		List<PoultryEntity> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}

	//public PoultryEntity getAllPoultrys(UUID poultryId) {
	public PoultryResponse getAllPoultrys(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		
		String sqlQuery = "SELECT p.id,p.poultry_name,p.phone_no,p.address,d.id as districtId,"
				+ " d.district_name,s.id as stateId ,s.state_name,c.id as countryId ,c.name "
				+ " FROM tb_poultry p "
				+" INNER JOIN tb_district d ON d.id=p.district_id "
				+" INNER JOIN tb_state s ON s.id=d.state_id "
				+" INNER JOIN tb_country c ON c.id=d.country_id "
				+ " where p.id='"+poultryId+"' ";
				

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		//List<PoultryEntity> list = new ArrayList<PoultryEntity>();
		PoultryResponse obj = null;
		for (Object[] row : rows) {
			obj = new PoultryResponse();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setPhoneNo(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setDistrictId(UUID.fromString(row[4].toString()));
			obj.setDistrictName(row[5].toString());
			obj.setStateId(UUID.fromString(row[6].toString()));
			obj.setStateName(row[7].toString());
			obj.setCountryId(UUID.fromString(row[8].toString()));
			obj.setCountryName(row[9].toString());
			
	
	}
		
		
		return obj;

//	public PoultryEntity getPoultryId(UUID poultryId) {
//			Session session = sessionFactory.getCurrentSession();
//			PoultryEntity obj = session.get(PoultryEntity.class, poultryId);
//			return obj;
//		
//			
//		}
	
		
		
	
	}
}

	
		
		
		
	


