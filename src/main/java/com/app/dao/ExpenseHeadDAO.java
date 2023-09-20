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

import com.app.dto.ExpenseHeadResponseDTO;
import com.app.entity.ExpenseHead;

@Repository
public class ExpenseHeadDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT e.id,e.expense_type " 
		+ "FROM tb_expense_head e "
		+"WHERE e.status='ACTIVE'"
		+ "ORDER BY e.expense_type ASC ";;

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ExpenseHeadResponseDTO> list = new ArrayList<ExpenseHeadResponseDTO>();
		ExpenseHeadResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ExpenseHeadResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseType(row[1].toString());
			list.add(obj);
		}

		return list;
	}

	public Object findById(UUID id) {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT e.id,e.expense_type\r\n" + "FROM tb_expense_head e WHERE e.id='" + id + "'";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ExpenseHeadResponseDTO> list = new ArrayList<ExpenseHeadResponseDTO>();
		ExpenseHeadResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ExpenseHeadResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseType(row[1].toString());
			list.add(obj);
		}

		return list;
	}

	public ExpenseHead findByNameExcludeId(String expenseType, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ExpenseHead.class);
		crit.add(Restrictions.eq("expenseType", expenseType));
		crit.add(Restrictions.ne("id", id));
		List<ExpenseHead> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}
}
