package com.app.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.app.dto.ProductionRespose;
import com.app.entity.ProductSales;
import com.app.entity.Production;

@Repository
public class ProductionDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<ProductionRespose> findAll() {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery ="SELECT pr.id,b.id as breedId,b.breed_name,pr.`count`,pr.date,pr.expiry_date,p.id as poultryId,p.poultry_name ,\r\n"
				+ "c.id as categoryId,c.category\r\n"
				+ "FROM tb_production pr \r\n"
				+ "INNER JOIN tb_poultry p  \r\n"
				+ "INNER JOIN tb_breed b \r\n"
				+ "INNER JOIN tb_category c ON p.id=pr.poultry_id  \r\n"
				+ "AND  b.id=pr.breed_id AND c.id=pr.category_id "
				+ "where pr.status='ACTIVE'";
		
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ProductionRespose> list = new ArrayList<ProductionRespose>();
		ProductionRespose obj = null;
		for (Object[] columns : rows) {
			obj = new ProductionRespose();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setBreedId(UUID.fromString(columns[1].toString()));
			obj.setBreedName(columns[2].toString());
			obj.setCount(Integer.valueOf(columns[3].toString()));
			obj.setDate((Date)columns[4]);
			obj.setExpiryDate((Date)columns[5]);
			obj.setPoultryId(UUID.fromString(columns[6].toString()));
			obj.setPoultryName(columns[7].toString());
			obj.setCategoryId(UUID.fromString(columns[8].toString()));
			obj.setCategoryName(columns[9].toString());
			
			list.add(obj);
		}
		return list;
	}
public List<ProductionRespose> findById(UUID id) {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery ="SELECT pr.id,b.id as breedId,b.breed_name,pr.`count`,pr.date,pr.expiry_date,p.id as poultryId,p.poultry_name ,\r\n"
				+ "c.id as categoryId,c.category\r\n"
				+ "FROM tb_production pr \r\n"
				+ "INNER JOIN tb_poultry p  \r\n"
				+ "INNER JOIN tb_breed b \r\n"
				+ "INNER JOIN tb_category c ON p.id=pr.poultry_id  \r\n"
				+ "	AND  b.id=pr.breed_id AND c.id=pr.category_id"
				+ " WHERE pr.id='"+id+"'"; 
		
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ProductionRespose> list = new ArrayList<ProductionRespose>();
		ProductionRespose obj = null;
		for (Object[] columns : rows) {
			obj = new ProductionRespose();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setBreedId(UUID.fromString(columns[1].toString()));
			obj.setBreedName(columns[2].toString());
			obj.setCount(Integer.valueOf(columns[3].toString()));
			obj.setDate((Date)columns[4]);
			obj.setExpiryDate((Date)columns[5]);
			obj.setPoultryId(UUID.fromString(columns[6].toString()));
			obj.setPoultryName(columns[7].toString());
			obj.setCategoryId(UUID.fromString(columns[8].toString()));
			obj.setCategoryName(columns[9].toString());
			
			list.add(obj);
		}
		return list;
	}
//public List<ProductionRespose> findByPoultryId(UUID poultryId) {
//	
//	Session session = sessionFactory.getCurrentSession();
//	String sqlQuery ="SELECT pr.id,bt.id as breedTypeId,bt.breed_type,pr.`count`,pr.date,pr.expiry_date,p.id as poultryId,p.poultry_name "
//			+ "FROM tb_production pr "
//			+ "INNER JOIN tb_poultry p ON p.id=pr.poultry_id "
//			+ "INNER JOIN tb_breed_type bt  ON bt.id=pr.breedtype_id "
//			+ "WHERE p.id='"+poultryId+"'";
//	
//	Query query = session.createSQLQuery(sqlQuery);
//	List<Object[]> rows = query.list();
//	List<ProductionRespose> list = new ArrayList<ProductionRespose>();
//	ProductionRespose obj = null;
//	for (Object[] columns : rows) {
//		obj = new ProductionRespose();
//		obj.setId(UUID.fromString(columns[0].toString()));
//		obj.setBreedId(UUID.fromString(columns[1].toString()));
//		obj.setBreedName(columns[2].toString());
//		obj.setCount(Integer.valueOf(columns[3].toString()));
//		obj.setDate((Date)columns[4]);
//		obj.setExpiryDate((Date)columns[5]);
//		obj.setPoultryId(UUID.fromString(columns[6].toString()));
//		obj.setPoultryName(columns[7].toString());
//		obj.setCategoryId(UUID.fromString(columns[8].toString()));
//		obj.setCategoryName(columns[9].toString());
//		
//		list.add(obj);
//	}
//	return list;
//}
//public Object getAllProduction(String poultryName, String startDate, String endDate) {
//	Session session = sessionFactory.getCurrentSession();
//	String sqlQuery ="SELECT pr.id,bt.id as breedTypeId,bt.breed_type,pr.`count`,pr.date,pr.expiry_date,p.id as poultryId,p.poultry_name "
//			+ "	FROM tb_production pr "
//			+ "	INNER JOIN tb_poultry p ON p.id=pr.poultry_id "
//			+ "	INNER JOIN tb_breed_type bt  ON bt.id=pr.breedtype_id "
//			+ "WHERE  pr.date >= '"+startDate+"' AND pr.date <= '"+endDate+"' AND p.poultry_name LIKE '%"+poultryName+"%' "
//			+ "ORDER BY pr.date ";
//		
//	Query query = session.createSQLQuery(sqlQuery);
//	List<Object[]> rows = query.list();
//	List<ProductionRespose> list = new ArrayList<ProductionRespose>();
//	ProductionRespose obj = null;
//	for (Object[] columns : rows) {
//		obj = new ProductionRespose();
//		obj.setId(UUID.fromString(columns[0].toString()));
//		obj.setBreedId(UUID.fromString(columns[1].toString()));
//		obj.setBreedName(columns[2].toString());
//		obj.setCount(Integer.valueOf(columns[3].toString()));
//		obj.setDate((Date)columns[4]);
//		obj.setExpiryDate((Date)columns[5]);
//		obj.setPoultryId(UUID.fromString(columns[6].toString()));
//		obj.setPoultryName(columns[7].toString());
//		obj.setCategoryId(UUID.fromString(columns[8].toString()));
//		obj.setCategoryName(columns[9].toString());
//		
//		list.add(obj);
//	}
//	return list;
//}
public Production findByBreedIdAndPoultryIdAndCategoryIdAndDateExcludeId(UUID breedId, UUID poultryId, UUID categoryId,
		String date, UUID id) {
	Session session = sessionFactory.getCurrentSession();
	Criteria crit = session.createCriteria(Production.class);
	crit.add(Restrictions.eq("breedId", breedId));
	crit.add(Restrictions.eq("poultryId", poultryId));
	crit.add(Restrictions.eq("categoryId", categoryId));	
	crit.add(Restrictions.eq("date", date));
	crit.add(Restrictions.ne("id", id));
	List<Production> list = crit.list();
	if (null != list && !list.isEmpty()) {
		return list.get(0);
	} else {

	}
	return null;


}

}
