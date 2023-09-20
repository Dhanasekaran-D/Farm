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

import com.app.dto.PoulrtyBreedGetDTO;
import com.app.dto.ProductDTO;
import com.app.dto.ProductResponse;
import com.app.entity.Product;

@Repository
public class ProductDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<ProductResponse> findAll() {
		
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery ="SELECT p.id,p.product_name,b.id AS breedId,b.breed_name,po.id AS poultryId,po.poultry_name,p.price,c.id AS categoryId,\r\n"
				+ "c.category,p.quantity\r\n"
				+ "FROM tb_product p "
				+ "INNER JOIN tb_poultry po "
				+ "INNER JOIN tb_breed b "
				+ "INNER JOIN tb_category c ON po.id=p.poultry_id AND b.id=p.breed_id AND c.id=p.category_id "
				+ "WHERE p.status='ACTIVE'"
				+ "ORDER BY p.product_name ASC ";
				
		
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
		
			obj.setQuantity(Integer.valueOf(columns[9].toString()));
			list.add(obj);
		}
		return list;
	}

	public List<ProductResponse> getById(UUID id) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery ="	SELECT p.id,p.product_name,b.id AS breedId,b.breed_name,po.id AS poultryId,po.poultry_name,p.price,c.id AS categoryId,\r\n"
				+ "c.category,p.quantity\r\n"
				+ "FROM tb_product p\r\n"
				+ "INNER JOIN tb_poultry po\r\n"
				+ "INNER JOIN tb_breed b\r\n"
				+ "INNER JOIN tb_category c ON po.id=p.poultry_id AND b.id=p.breed_id "
				+ "AND c.id=p.category_id AND p.id='"+id+"'";
				
		
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
		//	obj.setDescriptions(columns[9].toString());
			obj.setQuantity(Integer.valueOf(columns[9].toString()));
			list.add(obj);
		}
		return list;
	}
//	public List<ProductResponse> getByPoultryId(UUID poultryId) {
//		Session session = sessionFactory.getCurrentSession();
//		String sqlQuery ="SELECT p.id,p.product_name,b.id AS breedId,b.breed_name,po.id AS poultryId,po.poultry_name,p.quantity,p.price,c.id AS categoryId,\r\n"
//				+ "c.category,p.descriptions \r\n"
//				+ "FROM tb_product p \r\n"
//				+ "INNER JOIN tb_poultry po \r\n"
//				+ "INNER JOIN tb_breed b \r\n"
//				+ "INNER JOIN  tb_category c ON po.id=p.poultry_id  AND b.id=p.breed_id \r\n"
//				+ "AND c.id=p.category_id where po.id='"+poultryId+"'";
//				
//		
//		Query query = session.createSQLQuery(sqlQuery);
//		List<Object[]> rows = query.list();
//		List<ProductResponse> list = new ArrayList<ProductResponse>();
//		ProductResponse obj = null;
//		for (Object[] columns : rows) {
//			obj = new ProductResponse();
//			obj.setId(UUID.fromString(columns[0].toString()));
//			obj.setProductName(columns[1].toString());
//			obj.setBreedId(UUID.fromString(columns[2].toString()));
//			obj.setBreedName(columns[3].toString());
//			obj.setPoultryId(UUID.fromString(columns[4].toString()));
//			obj.setPoultryName(columns[5].toString());
//			obj.setQuantity(columns[6].toString());
//			obj.setPrice(Double.valueOf(columns[7].toString()));
//			obj.setCategoryId(UUID.fromString(columns[8].toString()));
//			obj.setCategoryName(columns[9].toString());
//			obj.setDescriptions(columns[10].toString());
//			
//			list.add(obj);
//		}
//		return list;
//}

	public Product findByNameExcludeId(UUID breedId, UUID categoryId, UUID poultryId, UUID id) {
		Session session = sessionFactory.getCurrentSession();
		Criteria crit = session.createCriteria(Product.class);
		crit.add(Restrictions.eq("categoryId", categoryId));
		crit.add(Restrictions.eq("breedId", breedId));
		crit.add(Restrictions.eq("poultryId", poultryId));
		crit.add(Restrictions.ne("id", id));
		List<Product> list = crit.list();
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		} else {

		}
		return null;
	}

	public Object getpoultryId(UUID poultryId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery = "SELECT pm.poultry_id,p.poultry_name,bm.breed_id,b.breed_name FROM tb_poultry_mapping pm \r\n"
				+ "	 INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id\r\n"
				+ "	 INNER JOIN tb_poultry p ON p.id=pm.poultry_id\r\n"
				+ "	 INNER JOIN tb_breed b ON b.id=bm.breed_id\r\n"
				+ "	 WHERE pm.poultry_id='"+poultryId+"' AND pm.`status`='ACTIVE'"
				+ "ORDER BY b.breed_name ASC ";

		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		List<PoulrtyBreedGetDTO> list = new ArrayList<PoulrtyBreedGetDTO>();
		PoulrtyBreedGetDTO obj = null;
		for (Object[] row : rows) {
			obj = new PoulrtyBreedGetDTO();
			obj.setPoultryId(UUID.fromString(row[0].toString()));
			obj.setPoultryName(row[1].toString());
			obj.setBreedId(UUID.fromString(row[2].toString()));
			obj.setBreedName(row[3].toString());
			
			list.add(obj);
		}
		
		return list;
	}

	public ProductResponse findById(UUID productId) {
		Session session = sessionFactory.getCurrentSession();
		String sqlQuery ="	SELECT p.id,p.product_name,b.id AS breedId,b.breed_name,po.id AS poultryId,po.poultry_name,p.price,c.id AS categoryId,\r\n"
				+ "c.category,p.quantity\r\n"
				+ "FROM tb_product p\r\n"
				+ "INNER JOIN tb_poultry po\r\n"
				+ "INNER JOIN tb_breed b\r\n"
				+ "INNER JOIN tb_category c ON po.id=p.poultry_id AND b.id=p.breed_id "
				+ "AND c.id=p.category_id AND p.id='"+productId+"'";
				
		
		Query query = session.createSQLQuery(sqlQuery);
		List<Object[]> rows = query.list();
		//List<ProductResponse> list = new ArrayList<ProductResponse>();
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
		//	obj.setDescriptions(columns[9].toString());
			obj.setQuantity(Integer.valueOf(columns[9].toString()));
			//list.add(obj);
		}
		return obj;
	}

//	public List<ProductDTO> getAllproduct() {
//		  Session session = sessionFactory.getCurrentSession();
//		    String sqlQuery = " SELECT pr.id, po.id AS poultryId, po.poultry_name FROM tb_product pr "
//		    		+ " INNER JOIN tb_poultry po  ON po.id=pr.poultry_id WHERE pr.status='ACTIVE' "
//		    		+ " ORDER BY po.poultry_name ASC ";
//		    		
//		    Query query = session.createSQLQuery(sqlQuery);
//		    List<Object[]> rows = query.list();
//		    List<ProductDTO> resultList = new ArrayList<>();
//
//		    for (Object[] row : rows) {
//		    	ProductDTO obj = new ProductDTO();
//		        obj.setProductId(UUID.fromString(row[0].toString()));
//		        obj.setPoultryId(UUID.fromString(row[1].toString()));
//		        obj.setPoultryName(row[2].toString());
//		       
//		        String sqlQuerys = " SELECT p.id, p.product_name, p.quantity  FROM tb_product p "
//	    		+" WHERE p.status='ACTIVE' 	"
//	    		  + "	Where p.id='" + obj.getProductId() + "'"		
//	    		+ " ORDER BY p.product_name ASC " ;
//		        
//			    Query querys = session.createSQLQuery(sqlQuerys);
//			    List<Object[]> ro = query.list();
//			    List<Product> resultLists = new ArrayList<>();
//			    for (Object[] row1 : ro) {
//			    	Product obj1 = new Product();
//			    	  obj1.setId(UUID.fromString(row1[0].toString()));
//			    	  obj1.setProductName(row1[1].toString());
//			    	  obj1.setQuantity(Integer.valueOf(row1[2].toString()));
//			    	  
//			    	  resultLists.add(obj1);
//			    	  
//			    }
//              obj.setProducts(resultLists);
//              resultList.add(obj);
//		    }
//		return resultList;
//	}

	

	public List<ProductDTO> getProducts() {
		 Session session = sessionFactory.getCurrentSession();
		 String sqlQuerys = " SELECT p.id, p.product_name, po.id AS poultryId, po.poultry_name, p.quantity "
		 		+ " FROM tb_product p INNER JOIN tb_poultry po ON po.id=p.poultry_id"
		    		+" WHERE p.status='ACTIVE' 	"
	    		+ " ORDER BY p.product_name ASC " ;
		 
		  Query querys = session.createSQLQuery(sqlQuerys);
		    List<Object[]> ro = querys.list();
		    List<ProductDTO> resultLists = new ArrayList<>();
		    for (Object[] row1 : ro) {
		    	ProductDTO obj1 = new ProductDTO();
		    	  obj1.setProductId(UUID.fromString(row1[0].toString()));
		    	  obj1.setProductName(row1[1].toString());
		    	  obj1.setPoultryId(UUID.fromString(row1[2].toString()));
		    	  obj1.setPoultryName(row1[3].toString());
		    	  obj1.setQuantity(Integer.valueOf(row1[4].toString()));
		    	  
		    	  resultLists.add(obj1);
		    }    	  
		
		return resultLists;
	}

	public List<ProductDTO> getProduct() {
		Session session = sessionFactory.getCurrentSession();
		 String sqlQuery = " SELECT  po.id AS poultryId,po.poultry_name FROM tb_product p "
	    		+ " INNER JOIN tb_poultry po  ON po.id=p.poultry_id  "
	    		+ "	GROUP BY  po.id, po.poultry_name\r\n"
	    		+ "	ORDER BY po.poultry_name ASC ";
		 Query query = session.createSQLQuery(sqlQuery);
		    List<Object[]> rows = query.list();
		    List<ProductDTO> resultList = new ArrayList<>();
		    for (Object[] row : rows) {
		    	ProductDTO obj = new ProductDTO();
		      //  obj.setProductId(UUID.fromString(row[0].toString()));
		        obj.setPoultryId(UUID.fromString(row[0].toString()));
		        obj.setPoultryName(row[1].toString());
		        
		        String sqlQuerys =" SELECT pa.id, pa.product_name, pa.quantity  FROM tb_product pa "
		        		+ "INNER JOIN tb_poultry p ON p.id = pa.poultry_id\r\n"
		        		+ "WHERE pa.status = 'ACTIVE' AND p.id = '"+obj.getPoultryId()+"'\r\n"
		        		+ "GROUP BY pa.id, pa.product_name\r\n"
		        		+ " ORDER BY pa.product_name ASC " ;
		        Query querys = session.createSQLQuery(sqlQuerys);
		        List<Object[]> rowss = querys.list();
		        List<Product> breedList = new ArrayList<>();
		        Product object = null;
		        
		        for (Object[] rows1 : rowss) {
		            object = new Product();
		            object.setId(UUID.fromString(rows1[0].toString()));
		            object.setProductName(rows1[1].toString());
		            object.setQuantity(Integer.valueOf(rows1[2].toString()));
		           

		            breedList.add(object);
		        }

			        obj.setProducts(breedList);
			        resultList.add(obj);
			    }

			    return resultList;
		    }
			
	public List<ProductDTO> getProductSale() {
	    Session session = sessionFactory.getCurrentSession();
	    String sqlQuery = "SELECT po.id AS poultryId, po.poultry_name " +
	                      "FROM tb_product p " +
	                      "INNER JOIN tb_poultry po ON po.id = p.poultry_id " +
	                      "GROUP BY po.id, po.poultry_name " +
	                      "ORDER BY po.poultry_name ASC";

	    Query query = session.createSQLQuery(sqlQuery);
	    List<Object[]> rows = query.list();
	    List<ProductDTO> resultList = new ArrayList<>();

	    for (Object[] row : rows) {
	        ProductDTO obj = new ProductDTO();
	        obj.setPoultryId(UUID.fromString(row[0].toString()));
	        obj.setPoultryName(row[1].toString());

	        String sqlQuerys = "SELECT pa.id, pa.product_name, " +
	                           "COALESCE(SUM(pa.quantity), 0) AS total_product_quantity, " +
	                           "COALESCE(SUM(s.quantity), 0) AS total_sales_quantity " +
	                           "FROM tb_product pa " +
	                           "LEFT JOIN tb_sales s ON s.product_id = pa.id " +
	                           "INNER JOIN tb_poultry p ON p.id = pa.poultry_id " +
	                           "WHERE pa.status = 'ACTIVE' AND p.id = '"+obj.getPoultryId()+"' " +
	                           "GROUP BY pa.id, pa.product_name " +
	                           "ORDER BY pa.product_name ASC";

	        Query querys = session.createSQLQuery(sqlQuerys);
	        //querys.setParameter("poultryId", obj.getPoultryId());

	        List<Object[]> rowss = querys.list();
	        List<Product> productList = new ArrayList<>();

	        for (Object[] rows1 : rowss) {
	            Product product = new Product();
	            product.setId(UUID.fromString(rows1[0].toString()));
	            product.setProductName(rows1[1].toString());
	            product.setQuantity(Integer.valueOf(rows1[2].toString()));
	            product.setSaleQuantity(Integer.valueOf(rows1[3].toString()));

	            productList.add(product);
	        }

	        obj.setProducts(productList);
	        resultList.add(obj);
	    }

	    return resultList;
	}


	
//
//	public List<PoultryBreedMappingDto> get() {
//	    Session session = sessionFactory.getCurrentSession();
//	    String sqlQuery = "SELECT pm.id as poultryMappingId, p.id as poultryId, p.poultry_name\r\n"
//	        + " FROM tb_poultry_mapping pm \r\n"
//	        + "	INNER JOIN tb_poultry p ON p.id=pm.poultry_id ";
//
//	    Query query = session.createSQLQuery(sqlQuery);
//	    List<Object[]> rows = query.list();
//	    List<PoultryBreedMappingDto> resultList = new ArrayList<>();
//
//	    for (Object[] row : rows) {
//	        PoultryBreedMappingDto obj = new PoultryBreedMappingDto();
//	        obj.setId(UUID.fromString(row[0].toString()));
//	        obj.setPoultryId(UUID.fromString(row[1].toString()));
//	        obj.setPoultryName(row[2].toString());
//
//	        String sqlQuerys = "SELECT  bm.id as breedMappingId, b.id AS breedId, b.breed_name, bm.total_count,bm.poultrymapping_id \r\n"
//	            + "FROM tb_poultry_mapping pm \r\n"
//	            + "	INNER JOIN tb_breed_mapping bm ON bm.poultrymapping_id=pm.id \r\n"
//	            + "INNER JOIN tb_breed b ON b.id=bm.breed_id \r\n"
//	            + "	Where bm.poultrymapping_id='" + obj.getId() + "'";
//
//	        Query querys = session.createSQLQuery(sqlQuerys);
//	        List<Object[]> rowss = querys.list();
//	        List<BrredPoultry> breedList = new ArrayList<>();
//	        BrredPoultry object = null;
//
//	        for (Object[] rows1 : rowss) {
//	            object = new BrredPoultry();
//	            object.setId(UUID.fromString(rows1[0].toString()));
//	            object.setBreedId(UUID.fromString(rows1[1].toString()));
//	            object.setBreedName(rows1[2].toString());
//	            object.setTotalCount(Integer.valueOf(rows1[3].toString()));
//	            object.setPoultryBreedMappingId(UUID.fromString(rows1[4].toString()));
//
//	            breedList.add(object);
//	        }
//
//	        obj.setBreedList(breedList);
//	        resultList.add(obj);
//	    }
//
//	    return resultList;
//	}
//	

	
}
