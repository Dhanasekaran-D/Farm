package com.app.dao;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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

import com.app.dto.ManageDTO;
import com.app.dto.ManageExpenseDTO;
import com.app.dto.ManageExpenseResponseDTO;
import com.app.entity.ManageExpense;

@Repository
public class ManageExpenseDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Object findAll() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT m.id, e.id as expenseHeadId, e.expense_type, m.descriptions, m.date, m.amount, p.id as poultryId, p.poultry_name\r\n"
				+ "FROM tb_manage_expense m\r\n" + "INNER JOIN tb_expense_head e ON e.id = m.expense_head_id\r\n"
				+ "INNER JOIN tb_poultry p ON p.id = m.poultry_id\r\n"
				+ "WHERE m.status = 'ACTIVE' AND m.date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)\r\n" + "";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageExpenseDTO> list = new ArrayList<ManageExpenseDTO>();
		ManageExpenseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseHeadId(UUID.fromString(row[1].toString()));
			obj.setExpenseType(row[2].toString());
			obj.setDescription(row[3].toString());

			obj.setDate((Date) row[4]);
			double amount = Double.parseDouble(row[5].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(formattedAmount);
			obj.setPoultryId(UUID.fromString(row[6].toString()));
			obj.setPoultryName(row[7].toString());
			list.add(obj);
		}

		return list;
	}

	public Object findById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT m.id,e.id AS expenseHeadId,e.expense_type,m.descriptions,m.date,m.amount,p.id AS poultryId,p.poultry_name\r\n"
				+ "	FROM tb_manage_expense m INNER JOIN tb_expense_head e ON e.id=m.expense_head_id AND m.id='" + id
				+ "'" + "	INNER JOIN tb_poultry p  ON p.id=m.poultry_id";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageExpenseDTO> list = new ArrayList<ManageExpenseDTO>();
		ManageExpenseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseHeadId(UUID.fromString(row[1].toString()));
			obj.setExpenseType(row[2].toString());
			obj.setDescription(row[3].toString());
			obj.setDate((Date) row[4]);
//			double amount = Double.parseDouble();
//
//			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
//			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(row[5].toString());
			obj.setPoultryId(UUID.fromString(row[6].toString()));
			obj.setPoultryName(row[7].toString());
			list.add(obj);
		}

		return list;
	}

	public List<ManageExpenseResponseDTO> findByExpenseHeadId(UUID expenseHeadId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT m.id,e.id as expenseHeadId,e.expense_type,m.descriptions,m.date,m.amount,p.id as poultryId,p.poultry_name"
				+ "	FROM tb_manage_expense m " + "INNER JOIN tb_expense_head e ON e.id=m.expense_head_id  AND e.id='"
				+ expenseHeadId + "' " + "INNER JOIN tb_poultry p ON p.id=m.poultry_id ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageExpenseResponseDTO> list = new ArrayList<ManageExpenseResponseDTO>();
		ManageExpenseResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseHeadId(UUID.fromString(row[1].toString()));
			obj.setExpenseType(row[2].toString());
			obj.setDescription(row[3].toString());
			Date date = (Date) row[4];
			DateFormat foemat = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = foemat.format(date);
			obj.setDate(date1);
			double amount = Double.parseDouble(row[5].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(formattedAmount);
			obj.setPoultryId(UUID.fromString(row[6].toString()));
			obj.setPoultryName(row[7].toString());
			list.add(obj);
		}

		return list;
	}

	public Object findByPoultryId(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT m.id,e.id AS expenseHeadId,e.expense_type,m.descriptions,m.date,m.amount,p.id AS poultryId,p.poultry_name\r\n"
				+ "FROM tb_manage_expense m INNER JOIN tb_expense_head e ON e.id=m.expense_head_id \r\n"
				+ "INNER JOIN tb_poultry p  ON p.id=m.poultry_id AND p.id='" + poultryId + "'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageExpenseResponseDTO> list = new ArrayList<ManageExpenseResponseDTO>();
		ManageExpenseResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseHeadId(UUID.fromString(row[1].toString()));
			obj.setExpenseType(row[2].toString());
			obj.setDescription(row[3].toString());
			Date date = (Date) row[4];
			DateFormat foemat = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = foemat.format(date);
			obj.setDate(date1);
			double amount = Double.parseDouble(row[5].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(formattedAmount);
			obj.setPoultryId(UUID.fromString(row[6].toString()));
			obj.setPoultryName(row[7].toString());
			list.add(obj);
		}

		return list;
	}

	public List<ManageExpenseDTO> getAllBreeds(String poultryName, String startDate, String endDate) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT m.id, e.expense_type, m.descriptions, m.date, m.amount, p.poultry_name "
				+ "FROM tb_manage_expense m " + "INNER JOIN tb_expense_head e ON e.id = m.expense_head_id "
				+ "INNER JOIN tb_poultry p ON p.id = m.poultry_id " + "WHERE m.date >= '" + startDate
				+ "' AND m.date <= '" + endDate + "'";

		// Check if the key parameter is provided
		if (poultryName != null && !poultryName.isEmpty()) {
			sqlQuery += " AND p.poultry_name LIKE '%" + poultryName + "%'";
		}
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageExpenseDTO> list = new ArrayList<ManageExpenseDTO>();
		ManageExpenseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseType(row[1].toString());
			obj.setDescription(row[2].toString());
			obj.setDate((Date) row[3]);
			double amount = Double.parseDouble(row[4].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(formattedAmount);
			obj.setPoultryName(row[5].toString());
			list.add(obj);
		}

		return list;
	}

	public List<ManageExpenseResponseDTO> getExpensePdf(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT m.id,e.id as expenseHeadId,e.expense_type,m.descriptions,m.date,m.amount,p.id AS poultryId,p.poultry_name\r\n"
				+ "FROM tb_manage_expense m\r\n" + "INNER JOIN tb_expense_head e ON e.id=m.expense_head_id\r\n"
				+ "INNER JOIN tb_poultry p ON p.id=m.poultry_id" + " WHERE p.id = '" + poultryId + "'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageExpenseResponseDTO> list = new ArrayList<ManageExpenseResponseDTO>();
		ManageExpenseResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseHeadId(UUID.fromString(row[1].toString()));
			obj.setExpenseType(row[2].toString());
			obj.setDescription(row[3].toString());
			Date date = (Date) row[4];
			DateFormat foemat = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = foemat.format(date);
			obj.setDate(date1);
			double amount = Double.parseDouble(row[5].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(formattedAmount);
			obj.setPoultryId(UUID.fromString(row[6].toString()));
			obj.setPoultryName(row[7].toString());
			list.add(obj);
		}

		return list;
	}

	public ManageExpense findByExpenseHeadIdAndPoultryIdAndDateExcludeId(UUID expenseHeadId, UUID poultryId,
			String date, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ManageExpense.class);
		crit.add(Restrictions.eq("expenseHeadId", expenseHeadId));
		crit.add(Restrictions.eq("poultryId", poultryId));
		crit.add(Restrictions.eq("date", date));
		crit.add(Restrictions.ne("id", id));
		List<ManageExpense> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;

	}

	public ManageExpense findByMonthAndYearAndDate(int month, int year, int date) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ManageExpense.class);
		crit.add(Restrictions.eq("month", month));
		crit.add(Restrictions.eq("year", year));
		crit.add(Restrictions.eq("date", date));
		List<ManageExpense> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;

	}
//	public boolean existsByMonthAndYearAndPoultryId(int month, int year, UUID poultryId, UUID id) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria crit = session.createCriteria(ManageExpense.class);
//		crit.add(Restrictions.eq("month", month));
//		crit.add(Restrictions.eq("poultryId", poultryId));
//		crit.add(Restrictions.eq("year", year));
//		crit.add(Restrictions.ne("id", id));
//		List<ManageExpense> list = crit.list();
//		if (null != list && !list.isEmpty()) {
//			return list.get(0);
//		} else {
//
//		}
//		return null;
//
//	}

	public ManageExpense findByexpenseId(UUID id) {

		Session session = sessionFactory.getCurrentSession();
		ManageExpense obj = session.get(ManageExpense.class, id);
		return obj;
	}

//	public ManageExpense findByExpenseHeadIdAndPoultryIdAndDateExcludeIdID(UUID expenseHeadId, UUID poultryId,
//			String date, UUID id) {
//		Session session = sessionFactory.getCurrentSession();
//		Criteria crit = session.createCriteria(ManageExpense.class);
//		crit.add(Restrictions.eq("expenseHeadId", "1d007f5c-087f-4415-b98a-ed4b49474d1b"));
//		crit.add(Restrictions.eq("poultryId", poultryId));
//		crit.add(Restrictions.eq("date", date));
//		crit.add(Restrictions.ne("id", id));
//		List<ManageExpense> list = crit.list();
//		if (null != list && !list.isEmpty()) {
//			return list.get(0);
//		} else {
//
//		}
//		return null;
//
//	}
	
	
	public List<ManageExpenseResponseDTO> getExpensePdf(String poultryName, String startDate, String endDate) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = " SELECT m.id,e.id as expenseHeadId,e.expense_type,m.descriptions,m.date,m.amount,p.id AS poultryId,p.poultry_name "
				+ " FROM tb_manage_expense m " + " INNER JOIN tb_expense_head e ON e.id=m.expense_head_id "
				+ " INNER JOIN tb_poultry p ON p.id=m.poultry_id ";
		  if( null !=poultryName ) {
        	  sqlQuery = sqlQuery + " WHERE m.date >= '" + startDate+ "' AND m.date <= '" + endDate + "' AND p.poultry_name LIKE '%" + poultryName + "%'";
          }
		 
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageExpenseResponseDTO> list = new ArrayList<ManageExpenseResponseDTO>();
		ManageExpenseResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseHeadId(UUID.fromString(row[1].toString()));
			obj.setExpenseType(row[2].toString());
			obj.setDescription(row[3].toString());
			Date date = (Date) row[4];
			DateFormat foemat = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = foemat.format(date);
			obj.setFromDate(date1);
			double amount = Double.parseDouble(row[5].toString());
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(formattedAmount);
			obj.setPoultryId(UUID.fromString(row[6].toString()));
			obj.setPoultryName(row[7].toString());
			list.add(obj);
		}
		return list;
	}



	public ManageExpenseResponseDTO getPdf(String poultryName, String startDate, String endDate) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT m.id, e.expense_type, m.descriptions, m.date, SUM(m.amount) AS total_amount, p.poultry_name, p.id AS poultryId "
				+ "FROM tb_manage_expense m " + "INNER JOIN tb_expense_head e ON e.id = m.expense_head_id "
				+ "INNER JOIN tb_poultry p ON p.id = m.poultry_id "
				+ "WHERE m.date >= :startDate AND m.date <= :endDate AND p.poultry_name LIKE :poultryName "
				+ "GROUP BY m.id, e.expense_type, m.descriptions, m.date, p.poultry_name";
		Query query = session.createSQLQuery(sqlQuery).setParameter("startDate", startDate)
				.setParameter("endDate", endDate).setParameter("poultryName", "%" + poultryName + "%");
		List<Object[]> rows = query.list();
		List<ManageExpenseResponseDTO> list = new ArrayList<>();
		double totalAmount = 0.0;
		ManageExpenseResponseDTO obj = null;
		for (Object[] row : rows) {
			obj = new ManageExpenseResponseDTO();
			obj.setId(UUID.fromString(row[0].toString()));
			obj.setExpenseType(row[1].toString());
			obj.setDescription(row[2].toString());
			Date date = (Date) row[3];
			DateFormat foemat = new SimpleDateFormat("dd/MM/yyyy");
			String date1 = foemat.format(date);
			obj.setDate(date1);
			double amount = Double.parseDouble(row[4].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedAmount = decimalFormat.format(amount);
			obj.setAmount(formattedAmount);
			obj.setPoultryId(UUID.fromString(row[6].toString()));
			obj.setPoultryName(row[5].toString());

			double amountt = Double.parseDouble(obj.getAmount().replace(",", ""));
			totalAmount += amountt;
			String formattedTotalAmount = decimalFormat.format(totalAmount);
			obj.setTotalAmount(formattedTotalAmount);
		}
		return obj;
	}

//	public Object findAllexpense(String timeRange) {
//	    Session session = sessionFactory.getCurrentSession();
//	    String sqlQuery = "SELECT m.id, e.id as expenseHeadId, e.expense_type, m.descriptions, m.date, m.amount, p.id as poultryId, p.poultry_name "
//	            + "FROM tb_manage_expense m "
//	            + "INNER JOIN tb_expense_head e ON e.id = m.expense_head_id "
//	            + "INNER JOIN tb_poultry p ON p.id = m.poultry_id "
//	            + "WHERE m.status = 'ACTIVE' ";
//
//	    // Calculate the date range based on the specified time range
//	    Date currentDate = new Date();
//	    Calendar calendar = Calendar.getInstance();
//	    calendar.setTime(currentDate);
//
//	    if (timeRange.equals("week")) {
//	        calendar.add(Calendar.WEEK_OF_YEAR, -1);
//	    } else if (timeRange.equals("month")) {
//	        calendar.add(Calendar.MONTH, -1);
//	    } else if (timeRange.equals("year")) {
//	        calendar.add(Calendar.YEAR, -1);
//	    }
//
//	    Date startDate = calendar.getTime();
//
//	    // Append the date range condition to the SQL query
//	    sqlQuery += "AND m.date >= :startDate";
//
//	    Query query = session.createSQLQuery(sqlQuery);
//	    query.setParameter("startDate", startDate);
//
//	    List<Object[]> rows = query.list();
//	    List<ManageExpenseDTO> list = new ArrayList<>();
//	    ManageExpenseDTO obj = null;
//
//	    for (Object[] row : rows) {
//	        obj = new ManageExpenseDTO();
//	        obj.setId(UUID.fromString(row[0].toString()));
//	        obj.setExpenseHeadId(UUID.fromString(row[1].toString()));
//	        obj.setExpenseType(row[2].toString());
//	        obj.setDescription(row[3].toString());
//	        obj.setDate((Date) row[4]);
//	        double amount = Double.parseDouble(row[5].toString());
//	        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
//	        String formattedAmount = decimalFormat.format(amount);
//	        obj.setAmount(formattedAmount);
//	        obj.setPoultryId(UUID.fromString(row[6].toString()));
//	        obj.setPoultryName(row[7].toString());
//	        list.add(obj);
//	    }
//
//	    return list;
//	}

	public List<ManageDTO> getAllExpense() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id, p.poultry_name, weekly_expense.total_amount AS weekly_expense, "
				+ "monthly_expense.total_amount AS monthly_expense, yearly_expense.total_amount AS yearly_expense "
				+ "FROM tb_poultry p " + "LEFT JOIN (SELECT m.poultry_id, SUM(m.amount) AS total_amount "
				+ "           FROM tb_manage_expense m " + "           WHERE m.date >= CURDATE() - INTERVAL 7 DAY "
				+ "           GROUP BY m.poultry_id) AS weekly_expense ON p.id = weekly_expense.poultry_id "
				+ "LEFT JOIN (SELECT m.poultry_id, SUM(m.amount) AS total_amount "
				+ "           FROM tb_manage_expense m "
				+ "           WHERE DATE_FORMAT(m.date, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m') "
				+ "           GROUP BY m.poultry_id) AS monthly_expense ON p.id = monthly_expense.poultry_id "
				+ "LEFT JOIN (SELECT m.poultry_id, SUM(m.amount) AS total_amount "
				+ "           FROM tb_manage_expense m "
				+ "           WHERE DATE_FORMAT(m.date, '%Y') = DATE_FORMAT(CURDATE(), '%Y') "
				+ "           GROUP BY m.poultry_id) AS yearly_expense ON p.id = yearly_expense.poultry_id "
				+ " ORDER BY p.poultry_name ASC ";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ManageDTO> list = new ArrayList<>();
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		for (Object[] row : rows) {
			ManageDTO obj = new ManageDTO();
			obj.setPoultryId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setCurrentWeek(row[2] != null ? decimalFormat.format(row[2]) : "0.00"); // Handling null values and
																						// formatting as decimal
			obj.setCurrentMonth(row[3] != null ? decimalFormat.format(row[3]) : "0.00"); // Handling null values and
																							// formatting as decimal
			obj.setCurrentYear(row[4] != null ? decimalFormat.format(row[4]) : "0.00"); // Handling null values and
																						// formatting as decimal

			list.add(obj);
		}

		return list;
	}
}
