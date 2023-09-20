package com.app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dto.AgentResponseDTO;
import com.app.entity.Agent;

@Repository
public class AgentDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT a.id,a.agent_name,a.email,a.address as address,a.phone_no,p.id as poultryId,p.poultry_name,p.address as poultryaddress,\r\n"
				+ "d.id as districtId,d.district_name as districtName,s.id as stateId,s.state_name,c.id as countryId,c.name "
				+ "FROM tb_agent a "
				+ "	INNER JOIN tb_poultry p "
				+ "INNER JOIN tb_district d "
				+ "INNER JOIN tb_state s "
				+ "	INNER JOIN tb_country c ON p.id =a.poultry_id AND  d.id =a.district_id AND c.id=d.country_id AND s.id=d.state_id "
				+ "WHERE a.status='ACTIVE'" ;
				
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<AgentResponseDTO> list = new ArrayList<AgentResponseDTO>();
		AgentResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new AgentResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setAgentName(row[1].toString());
			obj.setEmail(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setPhoneNo(row[4].toString());
			obj.setPoultryId(UUID.fromString(row[5].toString()));
			obj.setPoultryName(row[6].toString());
			obj.setPoultryaddress(row[7].toString());
			obj.setDistrictId(UUID.fromString(row[8].toString()));
			obj.setDistrictName(row[9].toString());
			obj.setStateId(UUID.fromString(row[10].toString()));
			obj.setStateName(row[11].toString());
			obj.setCountryId(UUID.fromString(row[12].toString()));
			obj.setCountryName(row[13].toString());
			
			list.add(obj);
		}
		
		return list;
	}
     

	public List<AgentResponseDTO> findById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT a.id,a.agent_name,a.email,a.address as address,a.phone_no,p.id as poultryId,p.poultry_name,p.address as poultryaddress,d.id as districtId,"
				+"d.district_name as districtName,s.id as stateId,s.state_name,c.id as countryId,c.name "
				+"FROM tb_agent a "
				+"INNER JOIN tb_poultry p ON p.id=a.poultry_id "
				+"INNER JOIN tb_district d ON d.id =a.district_id "
				+"INNER JOIN tb_state s ON s.id=d.state_id "
				+"INNER JOIN tb_country c ON c.id=d.country_id AND a.id='"+id+"'";
				
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<AgentResponseDTO> list = new ArrayList<AgentResponseDTO>();
		AgentResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new AgentResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setAgentName(row[1].toString());
			obj.setEmail(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setPhoneNo(row[4].toString());
			obj.setPoultryId(UUID.fromString(row[5].toString()));
			obj.setPoultryName(row[6].toString());
			obj.setPoultryaddress(row[7].toString());
			obj.setDistrictId(UUID.fromString(row[8].toString()));
			obj.setDistrictName(row[9].toString());
			obj.setStateId(UUID.fromString(row[10].toString()));
			obj.setStateName(row[11].toString());
			obj.setCountryId(UUID.fromString(row[12].toString()));
			obj.setCountryName(row[13].toString());
			
			list.add(obj);
		}
		
		return list;
	}
	
	public List<AgentResponseDTO> findBypoultryId(UUID poultryid) {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT a.id,a.agent_name,a.email,a.address as address,a.phone_no,p.id as poultryId,p.poultry_name,p.address as poultryaddress,d.id as districtId,"
				+"d.district_name as districtName,s.id as stateId,s.state_name,c.id as countryId,c.name "
				+"FROM tb_agent a "
				+"INNER JOIN tb_poultry p ON p.id=a.poultry_id "
				+"INNER JOIN tb_district d ON d.id =a.district_id "
				+"INNER JOIN tb_state s ON s.id=d.state_id "
				+"INNER JOIN tb_country c ON c.id=d.country_id AND p.id='"+poultryid+"'";
				
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<AgentResponseDTO> list = new ArrayList<AgentResponseDTO>();
		AgentResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new AgentResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setAgentName(row[1].toString());
			obj.setEmail(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setPhoneNo(row[4].toString());
			obj.setPoultryId(UUID.fromString(row[5].toString()));
			obj.setPoultryName(row[6].toString());
			obj.setPoultryaddress(row[7].toString());
			obj.setDistrictId(UUID.fromString(row[8].toString()));
			obj.setDistrictName(row[9].toString());
			obj.setStateId(UUID.fromString(row[10].toString()));
			obj.setStateName(row[11].toString());
			obj.setCountryId(UUID.fromString(row[12].toString()));
			obj.setCountryName(row[13].toString());
			
			list.add(obj);
		}
		
		return list;
	}
	
	public List<AgentResponseDTO> findByDistrictId(UUID districtId) {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT a.id,a.agent_name,a.email,a.address as address,a.phone_no,p.id as poultryId,p.poultry_name,p.address as poultryaddress,d.id as districtId,"
				+"d.district_name as districtName,s.id as stateId,s.state_name,c.id as countryId,c.name "
				+"FROM tb_agent a "
				+"INNER JOIN tb_poultry p ON p.id=a.poultry_id "
				+"INNER JOIN tb_district d ON d.id =a.district_id "
				+"INNER JOIN tb_state s ON s.id=d.state_id "
				+"INNER JOIN tb_country c ON c.id=d.country_id AND d.id='"+districtId+"'";
				
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<AgentResponseDTO> list = new ArrayList<AgentResponseDTO>();
		AgentResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new AgentResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setAgentName(row[1].toString());
			obj.setEmail(row[2].toString());
			obj.setAddress(row[3].toString());
			obj.setPhoneNo(row[4].toString());
			obj.setPoultryId(UUID.fromString(row[5].toString()));
			obj.setPoultryName(row[6].toString());
			obj.setPoultryaddress(row[7].toString());
			obj.setDistrictId(UUID.fromString(row[8].toString()));
			obj.setDistrictName(row[9].toString());
			obj.setStateId(UUID.fromString(row[10].toString()));
			obj.setStateName(row[11].toString());
			obj.setCountryId(UUID.fromString(row[12].toString()));
			obj.setCountryName(row[13].toString());
			
			list.add(obj);
		}
		
		return list;
	}


	public Agent findByNameExcludeId(UUID poultryId, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Agent.class);
		crit.add(Restrictions.eq("poultryId", poultryId));
		crit.add(Restrictions.ne("id", id));
		List<Agent> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}


	public Agent findByUserEmailExcludeId(String emailId, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Agent.class);
		crit.add(Restrictions.eq("emailId", emailId));
		crit.add(Restrictions.ne("id", id));
		List<Agent> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}

	public Agent getByPhoneNoExcludeId(String phoneNo, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Agent.class);
		crit.add(Restrictions.eq("phoneNo", phoneNo));
		crit.add(Restrictions.ne("id", id));
		List<Agent> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}


	public Agent getByAgentNameExcludeId(String agentName, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Agent.class);
		crit.add(Restrictions.eq("agentName", agentName));
		crit.add(Restrictions.ne("id", id));
		List<Agent> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
}
	


