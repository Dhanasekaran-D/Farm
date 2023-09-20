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

import com.app.dto.CustomerResponse;
import com.app.entity.Category;
import com.app.entity.Customer;

@Repository
public class CustomerDAO {
	@Autowired
	private SessionFactory sessionFactory;

public List<CustomerResponse> findAll() {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery ="	SELECT cs.id,cs.name,cs.address_line,cs.postal_code,cs.email,cs.phone_number,\r\n"
				+ " d.id as districtId ,d.district_name as districtName,\r\n"
				+ "s.id as stateId,s.state_name,c.id as countryId,c.name as countryName \r\n"
				+ "FROM tb_customer cs \r\n"
				+ "INNER JOIN tb_district d ON  d.id=cs.district_id \r\n"
				+ "INNER JOIN tb_state s ON s.id=d.state_id \r\n"
				+ "INNER JOIN tb_country c ON c.id=d.country_id" 
				+ " ORDER BY cs.name ASC ";
		
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CustomerResponse> list = new ArrayList<CustomerResponse>();
		CustomerResponse obj = null;
		for (Object[] columns : rows) {
			obj = new CustomerResponse();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setName(columns[1].toString());
			obj.setAddress(columns[2].toString());
			obj.setPostelCode(columns[3].toString());
			obj.setEmail(columns[4].toString());
			obj.setPhoneNo(columns[5].toString());
			obj.setDistrictId(UUID.fromString(columns[6].toString()));
			obj.setDistrictName(columns[7].toString());
			obj.setStateId(UUID.fromString(columns[8].toString()));
			obj.setStateName(columns[9].toString());
			obj.setCountryId(UUID.fromString(columns[10].toString()));
			obj.setCountryName(columns[11].toString());
			list.add(obj);
		}
		return list;
	}

public List<CustomerResponse> findById(UUID id) {
	
	Session session = sessionFactory.getCurrentSession();
	String sqlQuery ="	SELECT cs.id,cs.name,cs.address_line,cs.postal_code,cs.email,cs.phone_number,\r\n"
			+ " d.id as districtId ,d.district_name as districtName,\r\n"
			+ "s.id as stateId,s.state_name,c.id as countryId,c.name as countryName \r\n"
			+ "FROM tb_customer cs  "
			+ "INNER JOIN tb_district d ON  d.id=cs.district_id \r\n"
			+ "INNER JOIN tb_state s ON s.id=d.state_id \r\n"
			+ "INNER JOIN tb_country c ON c.id=d.country_id WHERE cs.id ='"+id+"'" ;
	
	Query query = session.createSQLQuery(sqlQuery);
	List<Object[]> rows = query.list();
	List<CustomerResponse> list = new ArrayList<CustomerResponse>();
	CustomerResponse obj = null;
	for (Object[] columns : rows) {
		obj = new CustomerResponse();
		obj.setId(UUID.fromString(columns[0].toString()));
		obj.setName(columns[1].toString());
		obj.setAddress(columns[2].toString());
		obj.setPostelCode(columns[3].toString());
		obj.setEmail(columns[4].toString());
		obj.setPhoneNo(columns[5].toString());
		obj.setDistrictId(UUID.fromString(columns[6].toString()));
		obj.setDistrictName(columns[7].toString());
		obj.setStateId(UUID.fromString(columns[8].toString()));
		obj.setStateName(columns[9].toString());
		obj.setCountryId(UUID.fromString(columns[10].toString()));
		obj.setCountryName(columns[11].toString());
		list.add(obj);
	}
	return list;
}

public Customer findByNameExcludeId(String phoneNumber, UUID id) {
	Session session = sessionFactory.getCurrentSession();
	Criteria crit = session.createCriteria(Customer.class);
	crit.add(Restrictions.eq("phoneNumber", phoneNumber));
	crit.add(Restrictions.ne("id", id));
	List<Customer> list = crit.list();
	if (null != list && !list.isEmpty()) {
		return list.get(0);
	} else {

	}
	return null;
}}
