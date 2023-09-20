
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

import com.app.dto.CustomerResponse;
import com.app.dto.ProductResponse;
import com.app.dto.ProductSalesDTO;
import com.app.dto.SaleInvoiceDTO;
import com.app.dto.SalesDTO;
import com.app.dto.SalesInvoiceDTO;
import com.app.dto.SalesResponse;
import com.app.dto.SalesResponseDTO;
import com.app.entity.Product;
import com.app.entity.ProductSales;
import com.app.entity.Sales;
import com.app.entity.Transaction;
import com.app.enumeration.TransactionType;

@Repository
public class SalesDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public ProductSalesDTO findById(UUID id) {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT ps.id,ps.sales_no,ps.sub_total,ps.discount,ps.total_payable,ps.sales_date,ps.delivery_date,\r\n"
				+ "	p.id AS poultryId,p.poultry_name,c.id AS customerId,c.name,ps.sales_notes\r\n"
				+ "	FROM tb_product_sales ps \r\n" + "	INNER JOIN tb_customer c on c.id=ps.customer_id\r\n"
				+ "	INNER JOIN tb_poultry p on p.id=ps.poultry_id \r\n" + "	WHERE ps.id='" + id + "'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		ProductSalesDTO obj = new ProductSalesDTO();
		for (Object[] columns : rows) {
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setSalesNo(columns[1].toString());
//			double subTotal = Double.parseDouble();
//			double discount = Double.parseDouble();
//			double totalPayable = Double.parseDouble();
//
//			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
//			String formattedSubTotal = decimalFormat.format(subTotal);
//			String formattedDiscount = decimalFormat.format(discount);
//			String formattedTotalPayable = decimalFormat.format(totalPayable);

			obj.setSubTotal(columns[2].toString());
			obj.setDiscount(columns[3].toString());
			obj.setTotalPayable(columns[4].toString());
			obj.setSalesDate((Date) columns[5]);
			obj.setDeliveryDate((Date) columns[6]);
			obj.setPoultryId(UUID.fromString(columns[7].toString()));
			obj.setPoultryName(columns[8].toString());
			obj.setCustomerId(UUID.fromString(columns[9].toString()));
			obj.setCustomerName(columns[10].toString());
			obj.setSalesNotes(columns[11].toString());

		}
		String sqlQuerys = "SELECT s.id as salesId,s.product_sales_id,pr.id AS productId,\r\n"
				+ "	pr.product_name,s.quantity,s.rate,s.amount\r\n" + "	FROM tb_sales s \r\n"
				+ "	INNER JOIN tb_product_sales ps ON ps.id=s.product_sales_id\r\n"
				+ "	INNER  JOIN tb_product pr ON pr.id=s.product_id\r\n" + "	 WHERE s.product_sales_id='" + id + "'";

		Query querys = session.createSQLQuery(sqlQuerys);
		List<Object[]> rowss = querys.list();
		List<SalesResponseDTO> sales = new ArrayList<SalesResponseDTO>();
		SalesResponseDTO object = null;
		for (Object[] columns : rowss) {
			object = new SalesResponseDTO();
			object.setId(UUID.fromString(columns[0].toString()));
			object.setSalesId(UUID.fromString(columns[1].toString()));
			object.setProductId(UUID.fromString(columns[2].toString()));
			object.setProductName(columns[3].toString());
			object.setQuantity(Integer.valueOf(columns[4].toString()));
			object.setRate(Integer.valueOf(columns[5].toString()));
			object.setAmount(Integer.valueOf(columns[6].toString()));
			sales.add(object);
		}
		obj.setSales(sales);
		return obj;
	}

	public List<SalesDTO> findAll() {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT ps.id, ps.sales_no, ps.sales_date, c.name AS customerName, SUM(ps.total_payable) AS total_payable, p.id AS poultryId, p.poultry_name\r\n"
				+ "FROM tb_product_sales ps\r\n" + "INNER JOIN tb_poultry p ON ps.poultry_id = p.id\r\n"
				+ "INNER JOIN tb_customer c ON ps.customer_id = c.id\r\n"
				+ "WHERE ps.status = 'ACTIVE' AND ps.sales_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)\r\n"
				+ "GROUP BY ps.id, ps.sales_no, ps.sales_date, p.id, p.poultry_name, c.name \r\n"
				+ "ORDER BY ps.sales_no desc ";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SalesDTO> list = new ArrayList<SalesDTO>();
		SalesDTO obj = null;
		for (Object[] columns : rows) {
			obj = new SalesDTO();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setSalesNo(columns[1].toString());
			obj.setSalesDate((Date) columns[2]);
			obj.setCustomerName(columns[3].toString());
			double totalPayable = Double.parseDouble(columns[4].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedTotalPayable = decimalFormat.format(totalPayable);
			obj.setTotalPayable(formattedTotalPayable);
			obj.setPoultryId(UUID.fromString(columns[5].toString()));
			obj.setPoultryName(columns[6].toString());

			list.add(obj);
		}
		return list;

	}

	public SaleInvoiceDTO findBySale(UUID id) {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT ps.id,ps.sales_no,ps.sub_total,ps.discount,ps.total_payable,ps.sales_date,ps.delivery_date,\r\n"
				+ "	p.id AS poultryId,p.poultry_name,c.id AS customerId,c.name,ps.sales_notes\r\n"
				+ "	FROM tb_product_sales ps \r\n" + "	INNER JOIN tb_customer c on c.id=ps.customer_id\r\n"
				+ "	INNER JOIN tb_poultry p on p.id=ps.poultry_id \r\n" + "	WHERE ps.id='" + id + "'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		SaleInvoiceDTO obj = new SaleInvoiceDTO();
		for (Object[] columns : rows) {
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setSalesNo(columns[1].toString());
			double subTotal = Double.parseDouble(columns[2].toString());
			double discount = Double.parseDouble(columns[3].toString());
			double totalPayable = Double.parseDouble(columns[4].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedSubTotal = decimalFormat.format(subTotal);
			String formattedDiscount = decimalFormat.format(discount);
			String formattedTotalPayable = decimalFormat.format(totalPayable);

			obj.setSubTotal(formattedSubTotal);
			obj.setDiscount(formattedDiscount);
			obj.setTotalPayable(formattedTotalPayable);
			Date saleDate = (Date) columns[5];
			DateFormat foemat = new SimpleDateFormat("dd/MM/yyyy");
			String saleDate1 = foemat.format(saleDate);
			obj.setSalesDate(saleDate1);
			Date deliveyDate = (Date) columns[6];
			String saleDate2 = foemat.format(deliveyDate);
			obj.setDeliveryDate(saleDate2);
			obj.setPoultryId(UUID.fromString(columns[7].toString()));
			obj.setPoultryName(columns[8].toString());
			obj.setCustomerId(UUID.fromString(columns[9].toString()));
			obj.setCustomerName(columns[10].toString());
			obj.setSalesNotes(columns[11].toString());

		}
		String sqlQuerys = "SELECT s.id as salesId,s.product_sales_id,pr.id AS productId,\r\n"
				+ "	pr.product_name,s.quantity,s.rate,s.amount\r\n" + "	FROM tb_sales s \r\n"
				+ "	INNER JOIN tb_product_sales ps ON ps.id=s.product_sales_id\r\n"
				+ "	INNER  JOIN tb_product pr ON pr.id=s.product_id\r\n" + "	 WHERE s.product_sales_id='" + id + "'";

		Query querys = session.createSQLQuery(sqlQuerys);
		List<Object[]> rowss = querys.list();
		List<SalesResponseDTO> sales = new ArrayList<SalesResponseDTO>();
		SalesResponseDTO object = null;
		for (Object[] columns : rowss) {
			object = new SalesResponseDTO();
			object.setId(UUID.fromString(columns[0].toString()));
			object.setSalesId(UUID.fromString(columns[1].toString()));
			object.setProductId(UUID.fromString(columns[2].toString()));
			object.setProductName(columns[3].toString());
			object.setQuantity(Integer.valueOf(columns[4].toString()));
			object.setRate(Integer.valueOf(columns[5].toString()));
			object.setAmount(Integer.valueOf(columns[6].toString()));
			sales.add(object);
		}
		obj.setSales(sales);
		return obj;
	}

	public Object getSales(String saleNo) {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT ps.id, ps.sales_no,ps.sales_date, c.name AS customerName, SUM(ps.total_payable) AS total_payable, p.id AS poultryId, p.poultry_name\r\n"
				+ "	FROM tb_product_sales ps\r\n" + "	INNER JOIN tb_poultry p ON ps.poultry_id = p.id\r\n"
				+ "	INNER JOIN tb_customer c ON ps.customer_id = c.id\r\n"
				+ "	WHERE ps.status = 'ACTIVE' and  ps.sales_no = '" + saleNo + "'\r\n"
				+ "GROUP BY ps.id, ps.sales_no, ps.sales_date, p.id, p.poultry_name,c.name ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SalesDTO> list = new ArrayList<SalesDTO>();
		SalesDTO obj = null;
		for (Object[] columns : rows) {
			obj = new SalesDTO();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setSalesNo(columns[1].toString());
			obj.setSalesDate((Date) columns[2]);
			obj.setCustomerName(columns[3].toString());
			double totalPayable = Double.parseDouble(columns[4].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedTotalPayable = decimalFormat.format(totalPayable);
			obj.setTotalPayable(formattedTotalPayable);
			obj.setPoultryId(UUID.fromString(columns[5].toString()));
			obj.setPoultryName(columns[6].toString());

			list.add(obj);
		}
		return list;

	}

	public List<SalesResponse> getSalesPdf() {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id as poultryId,p.poultry_name,p.phone_no,p.address,\r\n"
				+ "c.id AS customerId,c.name AS customerName,c.phone_number,c.email,c.address_line,c.postal_code,\r\n"
				+ "ps.id,ps.sales_no,ps.sales_date,ps.delivery_date,ps.sales_notes,ps.sub_total,ps.discount,ps.total_payable,\r\n"
				+ "pr.id AS productId,pr.product_name, \r\n" + "s.id AS saleId,s.quantity,s.rate,s.amount \r\n"
				+ "FROM  tb_product_sales ps\r\n" + "INNER JOIN tb_poultry p \r\n" + "INNER JOIN tb_customer c \r\n"
				+ "INNER JOIN tb_product pr \r\n"
				+ " INNER JOIN tb_sales s ON s.product_sales_id = ps.id AND p.id = ps.poultry_id AND \r\n"
				+ "c.id = ps.customer_id AND pr.id = s.product_id";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SalesResponse> list = new ArrayList<SalesResponse>();
		SalesResponse obj = null;
		for (Object[] columns : rows) {
			obj = new SalesResponse();
			obj.setPoultryId(UUID.fromString(columns[0].toString()));
			obj.setPoultryName(columns[1].toString());
			obj.setPhoneNo(columns[2].toString());
			obj.setAddress(columns[3].toString());
			obj.setCustomerId(UUID.fromString(columns[4].toString()));
			obj.setName(columns[5].toString());
			obj.setPhoneNumber(columns[6].toString());
			obj.setEmailId(columns[7].toString());
			obj.setAddressLine(columns[8].toString());
			obj.setPostalCode(columns[9].toString());
			obj.setId(UUID.fromString(columns[10].toString()));
			obj.setSalesNo(columns[11].toString());
			obj.setSalesDate((Date) columns[12]);
			obj.setDeliveryDate((Date) columns[13]);
			obj.setSalesNotes(columns[14].toString());
			obj.setSubTotal(Integer.valueOf(columns[15].toString()));
			obj.setDiscount(Integer.valueOf(columns[16].toString()));
			obj.setTotalPayable(Integer.valueOf(columns[17].toString()));
			obj.setProductId(UUID.fromString(columns[18].toString()));
			obj.setProductName(columns[19].toString());

			obj.setSalesId(UUID.fromString(columns[20].toString()));
			obj.setQuantity(Integer.valueOf(columns[21].toString()));
			double rate = Double.parseDouble(columns[22].toString());
			double amount = Double.parseDouble(columns[23].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedRate = decimalFormat.format(rate);
			String formattedAmount = decimalFormat.format(amount);
			obj.setRate(formattedRate);
			obj.setAmount(formattedAmount);

			list.add(obj);
		}
		return list;
	}

	public Object getSalesInvoice(String salesNo) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT p.id AS poultryId, p.poultry_name, p.address, p.phone_no,\r\n"
				+ "c.id AS customerId, c.name, c.address_line, c.phone_number,\r\n"
				+ "pr.id AS productId, pr.product_name,\r\n" + "s.id AS salesId, s.quantity, s.rate, s.amount,\r\n"
				+ "ps.id AS productSalesId, ps.sales_no, ps.sales_date, ps.delivery_date, ps.sub_total, ps.discount, ps.total_payable\r\n"
				+ "FROM tb_product_sales ps\r\n" + "INNER JOIN tb_poultry p ON p.id = ps.poultry_id\r\n"
				+ "INNER JOIN tb_customer c ON c.id = ps.customer_id\r\n"
				+ "INNER JOIN tb_sales s ON s.product_sales_id = ps.id\r\n"
				+ "INNER JOIN tb_product pr ON pr.id = s.product_id\r\n" + "WHERE ps.sales_no='" + salesNo + "'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SalesInvoiceDTO> list = new ArrayList<SalesInvoiceDTO>();
		SalesInvoiceDTO obj = null;
		for (Object[] columns : rows) {
			obj = new SalesInvoiceDTO();

			obj.setPoultryId(UUID.fromString(columns[0].toString()));
			obj.setPoultryName(columns[1].toString());
			obj.setPhoneNo(columns[2].toString());
			obj.setPoultryAddress(columns[3].toString());
			obj.setCustomerId(UUID.fromString(columns[4].toString()));
			obj.setCustomerName(columns[5].toString());
			obj.setPhoneNumber(columns[6].toString());
			obj.setCustomerAddress(columns[7].toString());

			obj.setProductId(UUID.fromString(columns[8].toString()));
			obj.setProductName(columns[9].toString());

			obj.setSalesId(UUID.fromString(columns[10].toString()));
			obj.setQuantity(Integer.valueOf(columns[11].toString()));
			obj.setRate(Double.valueOf(columns[12].toString()));
			obj.setAmount(Double.valueOf(columns[13].toString()));

			obj.setProductSalesId(UUID.fromString(columns[14].toString()));
			obj.setSalesNo(columns[15].toString());
			obj.setSalesDate((Date) columns[16]);
			obj.setDeliveryDate((Date) columns[17]);
			obj.setSubTotal(Double.valueOf(columns[18].toString()));
			obj.setDiscount(Integer.valueOf(columns[19].toString()));
			obj.setTotalPayable(Double.valueOf(columns[20].toString()));

			list.add(obj);
		}
		return list;

	}

	public List<SalesResponse> getSalesPdf(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id as poultryId,p.poultry_name,p.phone_no,p.address,\r\n"
				+ "c.id AS customerId,c.name AS customerName,c.phone_number,c.email,c.address_line,c.postal_code,\r\n"
				+ "ps.id,ps.sales_no,ps.sales_date,ps.delivery_date,ps.sales_notes,ps.sub_total,ps.discount,ps.total_payable,\r\n"
				+ "pr.id AS productId,pr.product_name, \r\n" + "s.id AS saleId,s.quantity,s.rate,s.amount \r\n"
				+ "FROM  tb_product_sales ps\r\n" + "INNER JOIN tb_poultry p \r\n" + "INNER JOIN tb_customer c \r\n"
				+ "INNER JOIN tb_product pr \r\n"
				+ " INNER JOIN tb_sales s ON s.product_sales_id = ps.id AND p.id = ps.poultry_id AND \r\n"
				+ "c.id = ps.customer_id AND pr.id = s.product_id WHERE ps.id='" + id + "'";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<SalesResponse> list = new ArrayList<SalesResponse>();
		SalesResponse obj = null;
		for (Object[] columns : rows) {
			obj = new SalesResponse();
			obj.setPoultryId(UUID.fromString(columns[0].toString()));
			obj.setPoultryName(columns[1].toString());
			obj.setPhoneNo(columns[2].toString());
			obj.setAddress(columns[3].toString());
			obj.setCustomerId(UUID.fromString(columns[4].toString()));
			obj.setName(columns[5].toString());
			obj.setPhoneNumber(columns[6].toString());
			obj.setEmailId(columns[7].toString());
			obj.setAddressLine(columns[8].toString());
			obj.setPostalCode(columns[9].toString());
			obj.setId(UUID.fromString(columns[10].toString()));
			obj.setSalesNo(columns[11].toString());
			obj.setSalesDate((Date) columns[12]);
			obj.setDeliveryDate((Date) columns[13]);
			obj.setSalesNotes(columns[14].toString());
			obj.setSubTotal(Integer.valueOf(columns[15].toString()));
			obj.setDiscount(Integer.valueOf(columns[16].toString()));
			obj.setTotalPayable(Integer.valueOf(columns[17].toString()));
			obj.setProductId(UUID.fromString(columns[18].toString()));
			obj.setProductName(columns[19].toString());

			obj.setSalesId(UUID.fromString(columns[20].toString()));
			obj.setQuantity(Integer.valueOf(columns[21].toString()));
			double rate = Double.parseDouble(columns[22].toString());
			double amount = Double.parseDouble(columns[23].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedRate = decimalFormat.format(rate);
			String formattedAmount = decimalFormat.format(amount);
			obj.setRate(formattedRate);
			obj.setAmount(formattedAmount);

			list.add(obj);
		}
		return list;
	}

	public ProductSales findBySalesNoExcludeId(String salesNo, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(ProductSales.class);
		crit.add(Restrictions.eq("salesNo", salesNo));
		crit.add(Restrictions.ne("id", id));
		List<ProductSales> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;

	}

	public Object findByPoultryId(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT p.id,p.product_name,b.id AS breedId,b.breed_name,po.id AS poultryId,po.poultry_name,p.price,c.id AS categoryId,\r\n"
				+ "c.category,p.quantity\r\n" + "FROM tb_product p\r\n" + "INNER JOIN tb_poultry po\r\n"
				+ "INNER JOIN tb_breed b\r\n"
				+ "INNER JOIN tb_category c ON po.id=p.poultry_id AND b.id=p.breed_id AND c.id=p.category_id\r\n"
				+ "WHERE p.status='ACTIVE'AND p.quantity > 0 AND po.id='" + poultryId + "'"
				+ "ORDER BY p.product_name ASC";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ProductResponse> list = new ArrayList<ProductResponse>();
		ProductResponse obj = null;
		for (Object[] columns : rows) {
			obj = new ProductResponse();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setProductName(columns[1].toString());
			obj.setBreedId(UUID.fromString(columns[2].toString()));
			obj.setBreedName(columns[3].toString());
			obj.setPoultryId(UUID.fromString(columns[4].toString()));
			obj.setPoultryName(columns[5].toString());

			obj.setPrice(Double.valueOf(columns[6].toString()));
			obj.setCategoryId(UUID.fromString(columns[7].toString()));
			obj.setCategoryName(columns[8].toString());
			// obj.setDescriptions(columns[9].toString());
			obj.setQuantity(Integer.valueOf(columns[9].toString()));
			list.add(obj);
		}
		return list;
	}

	public List<ProductSalesDTO> findBySalesId(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT ps.id,ps.sales_no,ps.sub_total,ps.discount,ps.total_payable,ps.sales_date,ps.delivery_date,\r\n"
				+ "	p.id AS poultryId,p.poultry_name,c.id AS customerId,c.name,ps.sales_notes\r\n"
				+ "	FROM tb_product_sales ps \r\n" + "	INNER JOIN tb_customer c on c.id=ps.customer_id\r\n"
				+ "	INNER JOIN tb_poultry p on p.id=ps.poultry_id \r\n" + "	WHERE ps.id='" + id + "'";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<ProductSalesDTO> list = new ArrayList<ProductSalesDTO>();
		ProductSalesDTO obj = new ProductSalesDTO();
		for (Object[] columns : rows) {
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setSalesNo(columns[1].toString());
			double subTotal = Double.parseDouble(columns[2].toString());
			double discount = Double.parseDouble(columns[3].toString());
			double totalPayable = Double.parseDouble(columns[4].toString());

			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
			String formattedSubTotal = decimalFormat.format(subTotal);
			String formattedDiscount = decimalFormat.format(discount);
			String formattedTotalPayable = decimalFormat.format(totalPayable);

			obj.setSubTotal(formattedSubTotal);
			obj.setDiscount(formattedDiscount);
			obj.setTotalPayable(formattedTotalPayable);
			obj.setSalesDate((Date) columns[5]);
			obj.setDeliveryDate((Date) columns[6]);
			obj.setPoultryId(UUID.fromString(columns[7].toString()));
			obj.setPoultryName(columns[8].toString());
			obj.setCustomerId(UUID.fromString(columns[9].toString()));
			obj.setCustomerName(columns[10].toString());
			obj.setSalesNotes(columns[11].toString());

			list.add(obj);

		}
		return null;
	}

	public CustomerResponse findByCustomerId(UUID customerId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT cs.id,cs.name,cs.address_line,cs.postal_code,cs.email,cs.phone_number,\r\n"
				+ " d.id as districtId ,d.district_name as districtName,\r\n"
				+ "s.id as stateId,s.state_name,c.id as countryId,c.name as countryName \r\n" + "FROM tb_customer cs  "
				+ "INNER JOIN tb_district d ON  d.id=cs.district_id \r\n"
				+ "INNER JOIN tb_state s ON s.id=d.state_id \r\n"
				+ "INNER JOIN tb_country c ON c.id=d.country_id WHERE cs.id ='" + customerId + "'";

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

		}
		return obj;
	}

	public Product get(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Product obj = session.get(Product.class, id);
		return obj;
	}

	public void saveOrUpdate(Product obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
	}

	public Sales getBy(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Sales obj = session.get(Sales.class, id);
		return obj;
	}

	public ProductSales findBySaleId(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		ProductSales obj = session.get(ProductSales.class, id);
		return obj;
	}

	public void updateSalesList(List<Sales> updatedSales) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(updatedSales);
	}

	public Transaction findTotalByProductId(UUID productId, String salesDate, UUID poultryId, TransactionType sale) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Transaction.class);
		crit.add(Restrictions.eq("productId", productId));
		crit.add(Restrictions.eq("transactionType", sale));
		crit.add(Restrictions.eq("poultryId", poultryId));
		crit.add(Restrictions.eq("transactionDate", salesDate));

		List<Transaction> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}

	public Object getTransactionAll() {

		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT t.id,t.transaction_date,t.transaction_type,t.poultry_id,po.poultry_name,t.product_id,p.product_name,t.total\r\n"
				+ "	 FROM tb_transactional t\r\n" 
				+ "	INNER JOIN tb_product p ON p.id = t.product_id\r\n"
				+ "	INNER JOIN tb_poultry po ON po.id=t.poultry_id "
				+ " WHERE  t.transaction_date >= DATE_SUB(CURDATE(), INTERVAL 30 DAY)"
				+ " ORDER BY t.transaction_date desc";
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<TransactionDto> list = new ArrayList<TransactionDto>();
		TransactionDto obj = null;
		for (Object[] columns : rows) {
			obj = new TransactionDto();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setTransactionDate((Date)columns[1]);
			obj.setTransactionType(TransactionType.valueOf(columns[2].toString()));
			obj.setPoultryId(UUID.fromString(columns[3].toString()));
			obj.setPoultryName(columns[4].toString());
			obj.setProductId(UUID.fromString(columns[5].toString()));
			obj.setProductName(columns[6].toString());
			obj.setTotal((int) Double.parseDouble(columns[7].toString()));
			list.add(obj);
		}
		return list;

	}

	public Transaction findTotalByProductIdProduction(UUID poultryId, String date, TransactionType import1, UUID uuid) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Transaction.class);
		crit.add(Restrictions.eq("productId", uuid));
		crit.add(Restrictions.eq("transactionType", import1));
		crit.add(Restrictions.eq("poultryId", poultryId));
		crit.add(Restrictions.eq("transactionDate", date));

		List<Transaction> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}

	public Object getAllTranscation(String poultryName, String startDate, String endDate) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "	SELECT t.id,t.transaction_date,t.transaction_type,t.poultry_id,po.poultry_name,t.product_id,p.product_name,t.total\r\n"
				+ "	 FROM tb_transactional t\r\n" 
				+ "	INNER JOIN tb_product p ON p.id = t.product_id\r\n"
				+ "	INNER JOIN tb_poultry po ON po.id=t.poultry_id "
				+" WHERE t.transaction_date >= '" + startDate +"'"
				+ " AND t.transaction_date <= '" + endDate +"'";
				//+ "AND p.poultry_name LIKE '%" + poultryName + "%'" ;
		
		if (poultryName != null && !poultryName.isEmpty()) {
			sqlQuery += " AND po.poultry_name LIKE '%" + poultryName+ "%'";
		}
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<TransactionDto> list = new ArrayList<TransactionDto>();
		TransactionDto obj = null;
		for (Object[] columns : rows) {
			obj = new TransactionDto();
			obj.setId(UUID.fromString(columns[0].toString()));
			obj.setTransactionDate((Date)columns[1]);
			obj.setTransactionType(TransactionType.valueOf(columns[2].toString()));
			obj.setPoultryId(UUID.fromString(columns[3].toString()));
			obj.setPoultryName(columns[4].toString());
			obj.setProductId(UUID.fromString(columns[5].toString()));
			obj.setProductName(columns[6].toString());
			obj.setTotal((int) Double.parseDouble(columns[7].toString()));
			list.add(obj);
		}
		return list;

	}

	}


