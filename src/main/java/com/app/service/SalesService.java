package com.app.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ProductDAO;
import com.app.dao.SalesDAO;
import com.app.dto.CustomerResponse;
import com.app.dto.ProductSalesDTO;
import com.app.dto.SaleInvoiceDTO;
import com.app.dto.SalesDTO;
import com.app.dto.SalesResponse;
import com.app.entity.Product;
import com.app.entity.ProductSales;
import com.app.entity.SaleNo;
import com.app.entity.Sales;
import com.app.entity.Transaction;
import com.app.enumeration.TransactionType;
import com.app.repository.CustomerRepository;
import com.app.repository.ProductRepositry;
import com.app.repository.ProductSalesRepository;
import com.app.repository.SaleNoRepository;
import com.app.repository.SalesRepository;
import com.app.repository.TransactionRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class SalesService {

	private @NonNull CustomerRepository customerRepository;

	private @NonNull SalesDAO salesDAO;

	private @NonNull ProductDAO productDao;

	private @NonNull ProductRepositry productRepositry;

	private @NonNull SalesRepository salesRepository;

	private @NonNull ProductSalesRepository productSalesRepository;

	private @NonNull SaleNoRepository saleNoRepository;

	private @NonNull TransactionRepository transactionRepository;

	public void saveOrUpdate(ProductSales sales) throws Exception {
		SaleNo saleNo = saleNoRepository.findAll().get(0);
		sales.setSalesNo(saleNo.getPrefix() + "-" + saleNo.getSuffix());
		Set<UUID> productSales = new HashSet<>();
		for (Sales product : sales.getSales()) {
			UUID productId = product.getProductId();
			if (productSales.contains(productId)) {
				throw new Exception("Duplicate Product found for the same Sales");
			}
			productSales.add(productId);
		}

		for (Sales productt : sales.getSales()) {
			UUID productId = productt.getProductId();
			Product productQuantity = salesDAO.get(productId);
			if (productQuantity == null) {
				throw new Exception("Product not found  ");
			}

			int saleQuantity = productt.getQuantity();
			int availableQuantity = productQuantity.getQuantity();

			if (saleQuantity > availableQuantity) {
				throw new Exception("Sale quantity exceeds available quantity for this  product : "
						+ productQuantity.getProductName() + "  available quantity  " + productQuantity.getQuantity());
			}

			productQuantity.setQuantity(availableQuantity - saleQuantity);
			salesDAO.saveOrUpdate(productQuantity);
		}
		for (Sales productSale : sales.getSales()) {
			UUID productId = productSale.getProductId();
			String transactionDate = sales.getSalesDate();
			UUID poultryId = sales.getPoultryId();
System.out.println(productId);
System.out.println(transactionDate);
System.out.println(poultryId);
       List<Transaction> transactions=transactionRepository.findAll();
       
			Transaction transaction = salesDAO.findTotalByProductId(productId,transactionDate,poultryId,TransactionType.EXPORT);
			
			System.err.println("-----------------------------------------------------");
			//System.out.println(transaction.getTotal());
			if (transaction != null) {
				transaction.setTotal(productSale.getQuantity() + transaction.getTotal());
			    transactionRepository.saveAndFlush(transaction);
			}
		     else {
		        transaction = new Transaction();
		        transaction.setProductId(productSale.getProductId());
		        transaction.setTransactionDate(sales.getSalesDate());
		        transaction.setPoultryId(sales.getPoultryId());
		        transaction.setTransactionType(TransactionType.EXPORT);
		        transaction.setTotal(productSale.getQuantity());
		        transactionRepository.saveAndFlush(transaction);
		        System.err.println("-----------------------------------------------------");
		    }

		
			
		}
		
		saleNo.setSuffix(saleNo.getSuffix() + 1);
		saleNoRepository.save(saleNo);
				productSalesRepository.save(sales);
	}

	public List<SalesDTO> getAll() {
		return salesDAO.findAll();
	}

	public void update(ProductSales sale) throws Exception {
		int totalAdditionalQuantity = 0;
		int totalReductionQuantity = 0;

		for (Sales product : sale.getSales()) {
			UUID productId = product.getProductId();

			if (null != product.getId()) {
				Product existingProduct = salesDAO.get(productId);
				Sales productSales = salesDAO.getBy(product.getId());
				int initialQuantity = productSales.getQuantity();
				int updatedQuantity = product.getQuantity();

				if (updatedQuantity > initialQuantity) {
					int additionalQuantity = updatedQuantity - initialQuantity;
					totalAdditionalQuantity += additionalQuantity;

					if (existingProduct.getQuantity() < additionalQuantity) {
						throw new Exception("Sale quantity exceeds available quantity for this product: "
								+ existingProduct.getProductName() + " available quantity: "
								+ existingProduct.getQuantity());
					}

					existingProduct.setQuantity(existingProduct.getQuantity() - additionalQuantity);

				} else if (updatedQuantity < initialQuantity) {

					int reductionQuantity = initialQuantity - updatedQuantity;
					totalReductionQuantity += reductionQuantity;
					existingProduct.setQuantity(existingProduct.getQuantity() + reductionQuantity);

				}

				salesDAO.saveOrUpdate(existingProduct);
			} else {

				for (Sales productt : sale.getSales()) {
					UUID productI = productt.getProductId();
					Product productQuantity = salesDAO.get(productI);
					if (productQuantity == null) {
						throw new Exception("Product not found  ");
					}

					int saleQuantity = productt.getQuantity();
					int availableQuantity = productQuantity.getQuantity();

					if (saleQuantity > availableQuantity) {
						throw new Exception("Sale quantity exceeds available quantity for this  product : "
								+ productQuantity.getProductName() + "  available quantity  "
								+ productQuantity.getQuantity());
					}

					productQuantity.setQuantity(availableQuantity - saleQuantity);
					salesDAO.saveOrUpdate(productQuantity);
				}
			}
		}
		productSalesRepository.saveAndFlush(sale);
	}

	public ProductSalesDTO findById(UUID id) {
		return salesDAO.findById(id);
	}

	public int calculateUpdatedQuantity(int currentQuantity, int quantityToAddOrSubtract) {
		return currentQuantity + quantityToAddOrSubtract;
	}

	public void delete(UUID id) {
		productSalesRepository.deleteById(id);
	}

	public Object getSales(String saleNo) {
		return salesDAO.getSales(saleNo);
	}

	public List<SalesResponse> getSalesPdf() {
		return salesDAO.getSalesPdf();
	}

	public Object getSalesInvoice(String salesNo) {
		return salesDAO.getSalesInvoice(salesNo);
	}

	public ProductSales findBySalesNo(String salesNo) {
		return productSalesRepository.findBySalesNo(salesNo);
	}

	public List<SalesResponse> getSalesPdf(UUID id) {
		return salesDAO.getSalesPdf(id);
	}

	public ProductSales findBySalesNoExcludeId(String salesNo, UUID id) {
		return salesDAO.findBySalesNoExcludeId(salesNo, id);

	}

	public Object findByPoultryId(UUID poultryId) {
		return salesDAO.findByPoultryId(poultryId);
	}

	public List<ProductSalesDTO> findBySalesId(UUID id) {
		return salesDAO.findBySalesId(id);
	}

	public CustomerResponse findByCustomerId(UUID customerId) {
		return salesDAO.findByCustomerId(customerId);
	}

	public void deleteById(UUID id) {
		salesRepository.deleteById(id);
	}

	public SaleInvoiceDTO findBySale(UUID id) {
		return salesDAO.findBySale(id);
	}

	public Object getTransactionAll() {
		// TODO Auto-generated method stub
		return salesDAO.getTransactionAll();
	}

	public Object getTransaction(String poultryName, String startDate, String endDate) {
		// TODO Auto-generated method stub
		return salesDAO.getAllTranscation(poultryName,startDate,endDate);
	}

}
