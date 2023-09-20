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

import com.app.dto.BrredPoultry;
import com.app.dto.PoultryBreedDTO;
import com.app.dto.PoultryBreedMappingDto;
import com.app.dto.PoultryBreedResponse;
import com.app.entity.PoultryBreed;
import com.app.entity.PoultryMapping;

@Repository
public class PoultryBreedDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "  SELECT pm.id as poultryMappingId, p.id as poultryId, p.poultry_name,\r\n"
				+ "SUM(bm.total_count) as total_count\r\n"
				+ "FROM tb_poultry_mapping pm\r\n"
				+ "INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id\r\n"
				+ "INNER JOIN tb_poultry p ON p.id=pm.poultry_id\r\n"
				+ "WHERE pm.status='ACTIVE'\r\n"
				+ "GROUP BY pm.id, p.id, p.poultry_name";
				

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoultryBreedResponse> list = new ArrayList<PoultryBreedResponse>();
		PoultryBreedResponse obj = null;
		for (Object[] row : rows) {
			obj = new PoultryBreedResponse();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setPoultryId(UUID.fromString(row[1].toString()));
			obj.setPoultryName(row[2].toString());
			obj.setTotalCount(Integer.valueOf(row[3].toString()));
			
			list.add(obj);
		}
		
		return list;
	}
	
	public Object findById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT pm.id as poultryMappingId, p.id as poultryId,p.poultry_name, bm.id as breedMappingId, b.id AS breedId, b.breed_name, bm.total_count,bm.poultrymapping_id \r\n"
				+ "FROM tb_poultry_mapping pm \r\n"
				+ "INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id \r\n"
				+ "INNER JOIN tb_breed b ON b.id=bm.breed_id \r\n"
				+ "INNER JOIN tb_poultry p ON p.id=pm.poultry_id "
				+ "Where pm.id='"+id+"'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoultryBreedDTO> list = new ArrayList<PoultryBreedDTO>();
		PoultryBreedDTO obj = null;
		for (Object[] row : rows) {
			obj = new PoultryBreedDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setPoultryId(UUID.fromString(row[1].toString()));
			obj.setPoultryName(row[2].toString());
			obj.setBreedMappingId(UUID.fromString(row[3].toString()));
			obj.setBreedId(UUID.fromString(row[4].toString()));
			obj.setBreedName(row[5].toString());
			obj.setTotalCount(Integer.valueOf(row[6].toString()));
			obj.setPoultryBreedMappingId(UUID.fromString(row[7].toString()));
			
			list.add(obj);
		}
		
		return list;
	}
	public PoultryBreedMappingDto findByPoultryId(UUID id) {
	    Session session = sessionFactory.getCurrentSession();
	    String sqlQuery = "SELECT pm.id as poultryMappingId, p.id as poultryId,p.poultry_name\r\n"
	    		+ " FROM tb_poultry_mapping pm \r\n"
	    		+ "	INNER JOIN tb_poultry p ON p.id=pm.poultry_id \r\n"
	    		+ " Where pm.id='"+id+"'";

	    Query query = session.createSQLQuery(sqlQuery);
	    List<Object[]> rows = query.list();
	    PoultryBreedMappingDto obj = new PoultryBreedMappingDto();
	    for (Object[] row : rows) {
	      //  obj = new PoultryBreedMappingDto();
	        obj.setId(UUID.fromString(row[0].toString()));
	        obj.setPoultryId(UUID.fromString(row[1].toString()));
	        obj.setPoultryName(row[2].toString());
	    }
	    String sqlQuerys = "SELECT  bm.id as breedMappingId, b.id AS breedId, b.breed_name, bm.total_count,bm.poultrymapping_id \r\n"
	    		+ "FROM tb_poultry_mapping pm \r\n"
	    		+ "	INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id \r\n"
	    		+ "INNER JOIN tb_breed b ON b.id=bm.breed_id \r\n"
	    		+ "	Where bm.poultrymapping_id='"+id+"'";

	    Query querys = session.createSQLQuery(sqlQuerys);
	    List<Object[]> rowss = querys.list();
	    List<BrredPoultry> breedList = new ArrayList<BrredPoultry>();
	    BrredPoultry object = null;
	    for (Object[] row : rowss) {
	        object = new BrredPoultry();
	        object.setId(UUID.fromString(row[0].toString()));
	        object.setBreedId(UUID.fromString(row[1].toString()));
	        object.setBreedName(row[2].toString());
	        object.setTotalCount(Integer.valueOf(row[3].toString()));
	        object.setPoultryBreedMappingId(UUID.fromString(row[4].toString()));
	        
	        breedList.add(object);
	    }
	    obj.setBreedList(breedList);
	    return obj;
	}
	public List<PoultryBreedDTO> getPoultryBreedByPoultryId(UUID breedMappingId) {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery =" SELECT pm.poultry_id, p.poultry_name, p.address, p.phone_no, d.district_name, s.state_name, c.name, "
				+ "	 pm.id, b.id AS breed_id, b.breed_name, bm.total_count "
				+ "	 FROM tb_poultry_mapping pm  INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id = pm.id "
				+ "	INNER JOIN tb_breed b ON bm.breed_id = b.id "
				+ "	INNER JOIN tb_poultry p ON pm.poultry_id = p.id "
				+ "	INNER JOIN tb_district d ON d.id = p.district_id "
				+ "	INNER JOIN tb_state s ON s.id = d.state_id "
				+ "	INNER JOIN tb_country c ON c.id = d.country_id 	"
				+ " WHERE bm.poultrymapping_id = '" + breedMappingId + "'";
				


		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoultryBreedDTO> list = new ArrayList<PoultryBreedDTO>();
		PoultryBreedDTO obj = null;
		for (Object[] row : rows) {
			obj = new PoultryBreedDTO();
			obj.setPoultryId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setAddress(row[2].toString());
			obj.setPhoneNo(row[3].toString());
			obj.setDistrictName(row[4].toString());
			obj.setStateName(row[5].toString());
			obj.setCountryName(row[6].toString());
			obj.setId(UUID.fromString(row[7].toString()));
			obj.setBreedId(UUID.fromString(row[8].toString()));
			obj.setBreedName(row[9].toString());
			obj.setTotalCount(Integer.valueOf(row[10].toString()));
			
			list.add(obj);

		}
		return list;
	}

	public Object getPoultryBreed(String poultryName) {
	    Session session = sessionFactory.getCurrentSession();
	    String sqlQuery = "SELECT pm.id as poultryMappingId, p.id as poultryId, p.poultry_name\r\n"
	        + " FROM tb_poultry_mapping pm \r\n"
	        + "	INNER JOIN tb_poultry p ON p.id=pm.poultry_id "
	    	+ "WHERE pm.status='ACTIVE' AND p.poultry_name LIKE '%"+ poultryName +"%'\r\n";

	    Query query = session.createSQLQuery(sqlQuery);
	    List<Object[]> rows = query.list();
	    List<PoultryBreedMappingDto> resultList = new ArrayList<>();

	    for (Object[] row : rows) {
	        PoultryBreedMappingDto obj = new PoultryBreedMappingDto();
	        obj.setId(UUID.fromString(row[0].toString()));
	        obj.setPoultryId(UUID.fromString(row[1].toString()));
	        obj.setPoultryName(row[2].toString());

	        String sqlQuerys = "SELECT  bm.id as breedMappingId, b.id AS breedId, b.breed_name, bm.total_count,bm.poultrymapping_id \r\n"
	            + "FROM tb_poultry_mapping pm \r\n"
	            + "	INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id \r\n"
	            + "INNER JOIN tb_breed b ON b.id=bm.breed_id \r\n"
	            + "	Where bm.poultrymapping_id='" + obj.getId() + "'";

	        Query querys = session.createSQLQuery(sqlQuerys);
	        List<Object[]> rowss = querys.list();
	        List<BrredPoultry> breedList = new ArrayList<>();
	        BrredPoultry object = null;

	        for (Object[] rows1 : rowss) {
	            object = new BrredPoultry();
	            object.setId(UUID.fromString(rows1[0].toString()));
	            object.setBreedId(UUID.fromString(rows1[1].toString()));
	            object.setBreedName(rows1[2].toString());
	            object.setTotalCount(Integer.valueOf(rows1[3].toString()));
	            object.setPoultryBreedMappingId(UUID.fromString(rows1[4].toString()));

	            breedList.add(object);
	        }

	        obj.setBreedList(breedList);
	        resultList.add(obj);
	    }

	    return resultList;
	}

	public Object countByPoultryId(UUID poultryId, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(PoultryMapping.class);
		crit.add(Restrictions.eq("poultryId", poultryId));
		crit.add(Restrictions.ne("id", id));
		List<PoultryMapping> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}


	public PoultryBreed getBreed(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		PoultryBreed obj = session.get(PoultryBreed.class, poultryId);
		return obj;
	
	}
	
	public List<PoultryBreedMappingDto> get() {
	    Session session = sessionFactory.getCurrentSession();
	    String sqlQuery = "SELECT pm.id as poultryMappingId, p.id as poultryId, p.poultry_name\r\n"
	        + " FROM tb_poultry_mapping pm \r\n"
	        + "	INNER JOIN tb_poultry p ON p.id=pm.poultry_id ";

	    Query query = session.createSQLQuery(sqlQuery);
	    List<Object[]> rows = query.list();
	    List<PoultryBreedMappingDto> resultList = new ArrayList<>();

	    for (Object[] row : rows) {
	        PoultryBreedMappingDto obj = new PoultryBreedMappingDto();
	        obj.setId(UUID.fromString(row[0].toString()));
	        obj.setPoultryId(UUID.fromString(row[1].toString()));
	        obj.setPoultryName(row[2].toString());

	        String sqlQuerys = "SELECT  bm.id as breedMappingId, b.id AS breedId, b.breed_name, bm.total_count,bm.poultrymapping_id \r\n"
	            + "FROM tb_poultry_mapping pm \r\n"
	            + "	INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id \r\n"
	            + "INNER JOIN tb_breed b ON b.id=bm.breed_id \r\n"
	            + "	Where bm.poultrymapping_id='" + obj.getId() + "'";

	        Query querys = session.createSQLQuery(sqlQuerys);
	        List<Object[]> rowss = querys.list();
	        List<BrredPoultry> breedList = new ArrayList<>();
	        BrredPoultry object = null;

	        for (Object[] rows1 : rowss) {
	            object = new BrredPoultry();
	            object.setId(UUID.fromString(rows1[0].toString()));
	            object.setBreedId(UUID.fromString(rows1[1].toString()));
	            object.setBreedName(rows1[2].toString());
	            object.setTotalCount(Integer.valueOf(rows1[3].toString()));
	            object.setPoultryBreedMappingId(UUID.fromString(rows1[4].toString()));

	            breedList.add(object);
	        }

	        obj.setBreedList(breedList);
	        resultList.add(obj);
	    }

	    return resultList;
	}
	public PoultryBreedMappingDto getById(UUID poultryId) {
	    Session session = sessionFactory.getCurrentSession();
	    String sqlQuery = "SELECT pm.id as poultryMappingId, p.id as poultryId,p.poultry_name\r\n"
	    		+ " FROM tb_poultry_mapping pm \r\n"
	    		+ "	INNER JOIN tb_poultry p ON p.id=pm.poultry_id \r\n"
	    		+ " Where p.id='"+poultryId+"'";

	    Query query = session.createSQLQuery(sqlQuery);
	    List<Object[]> rows = query.list();
	    PoultryBreedMappingDto obj = new PoultryBreedMappingDto();
	    for (Object[] row : rows) {
	      //  obj = new PoultryBreedMappingDto();
	        obj.setId(UUID.fromString(row[0].toString()));
	        obj.setPoultryId(UUID.fromString(row[1].toString()));
	        obj.setPoultryName(row[2].toString());
	    }
	    String sqlQuerys = "SELECT  bm.id as breedMappingId, b.id AS breedId, b.breed_name, bm.total_count,bm.poultrymapping_id \r\n"
	    		+ "FROM tb_poultry_mapping pm \r\n"
	    		+ "	INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id \r\n"
	    		+ "INNER JOIN tb_breed b ON b.id=bm.breed_id \r\n"
	    		+ "	Where bm.poultrymapping_id='"+obj.getId()+"'";

	    Query querys = session.createSQLQuery(sqlQuerys);
	    List<Object[]> rowss = querys.list();
	    List<BrredPoultry> breedList = new ArrayList<BrredPoultry>();
	    BrredPoultry object = null;
	    for (Object[] row : rowss) {
	        object = new BrredPoultry();
	        object.setId(UUID.fromString(row[0].toString()));
	        object.setBreedId(UUID.fromString(row[1].toString()));
	        object.setBreedName(row[2].toString());
	        object.setTotalCount(Integer.valueOf(row[3].toString()));
	        object.setPoultryBreedMappingId(UUID.fromString(row[4].toString()));
	        
	        breedList.add(object);
	    }
	    obj.setBreedList(breedList);
	    return obj;
	}

}
