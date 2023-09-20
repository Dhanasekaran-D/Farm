package com.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dto.DistrictResponce;
import com.app.entity.DistrictEntity;
import com.app.entity.Product;
@Repository
public class DistrictDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<DistrictResponce> findAll() {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT d.id,d.district_name,d.short_name, s.id as stateId,s.state_name,c.id as countryId,c.name  "
				+ " FROM tb_district d INNER JOIN tb_state s ON s.id=d.state_id INNER JOIN tb_country c ON c.id=d.country_id";
				
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<DistrictResponce> list = new ArrayList<DistrictResponce>();
		DistrictResponce obj = null;
		for (Object[] columns : rows) {
			obj = new DistrictResponce();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setDistrictName(columns[1].toString());
			obj.setShortName(columns[2].toString());
			obj.setStateId(UUID.fromString(columns[3].toString()));
			obj.setStateName(columns[4].toString());
			obj.setCountryId(UUID.fromString(columns[5].toString()));
			obj.setCountryName((columns[6].toString()));
			list.add(obj);
		}
		return list;
	}
	public List<DistrictEntity> getDistrictByStateId(UUID stateId) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(DistrictEntity.class);
		crit.add(Restrictions.eq("stateId", stateId));
		 crit.addOrder(Order.asc("districtName"));
		List<DistrictEntity> districtList = crit.list();
		if(null != districtList && !districtList.isEmpty()) {
			return districtList;
		}
		return null;
	}
	public DistrictEntity findByDistrictName(String districtName, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(DistrictEntity.class);
		crit.add(Restrictions.eq("districtName", districtName));
		crit.add(Restrictions.ne("id", id));
		List<DistrictEntity> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
	
	public DistrictEntity findByPinCode(String pinCode, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(DistrictEntity.class);
		crit.add(Restrictions.eq("pinCode", pinCode));
		crit.add(Restrictions.ne("id", id));
		List<DistrictEntity> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
}
