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

import com.app.dto.CategoryDTO;
import com.app.entity.Category;
import com.app.enumeration.Status;

@Repository
public class CategoryDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Category findByNameExcludeId(String category, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Category.class);
		crit.add(Restrictions.eq("category", category));
		crit.add(Restrictions.ne("id", id));
		List<Category> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	
}
	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT c.id,c.category\r\n"
				+ "FROM tb_category c\r\n"
				+ "ORDER BY c.category ASC ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<CategoryDTO> list = new ArrayList<CategoryDTO>();
		CategoryDTO obj = null;
		for (Object[] row : rows) {
			obj = new CategoryDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setCategory(row[1].toString());
			
			list.add(obj);
		}
		
		return list;
	}

}
