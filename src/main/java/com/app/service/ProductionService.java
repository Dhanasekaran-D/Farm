package com.app.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ProductionDAO;
import com.app.dao.SalesDAO;
import com.app.dto.ProductionDTO;
import com.app.dto.ProductionRespose;
import com.app.entity.Breed;
import com.app.entity.Category;
import com.app.entity.Product;
import com.app.entity.Production;
import com.app.entity.Sales;
import com.app.entity.Transaction;
import com.app.enumeration.Status;
import com.app.enumeration.TransactionType;
import com.app.repository.BreedRepository;
import com.app.repository.CategoryRepository;
import com.app.repository.ProductRepositry;
import com.app.repository.ProductionRepositry;
import com.app.repository.TransactionRepository;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional

@AllArgsConstructor(onConstructor_ = { @Autowired })
public class ProductionService {
	@Autowired
	private @NonNull ProductionRepositry productionRepositry;

	private @NonNull ProductRepositry productRepositry;

	private @NonNull BreedRepository breedRepository;

	@Autowired
	private @NonNull CategoryRepository categoryRepository;

	@Autowired
	private @NonNull ProductionDAO productionDAO;
	
	private @NonNull SalesDAO salesDAO;
	
	private @NonNull TransactionRepository transactionRepository;

	public void save(ProductionDTO productiondto) {
		Production production = new Production();
		production.setId(productiondto.getId());
		production.setPoultryId(productiondto.getPoultryId());
		production.setBreedId(productiondto.getBreedId());
		production.setCategoryId(productiondto.getCategoryId());
		production.setCount(productiondto.getCount());
		production.setDate(productiondto.getDate());
		production.setExpiryDate(productiondto.getExpiryDate());
		Production save =productionRepositry.save(production);
		Product productobj = productRepositry.findByBreedIdAndCategoryIdAndPoultryId(productiondto.getBreedId(),
				productiondto.getCategoryId(), productiondto.getPoultryId());
		if (null != productobj) {
		productobj.setBreedId(productiondto.getBreedId());
		productobj.setCategoryId(productiondto.getCategoryId());
		productobj.setPoultryId(productiondto.getPoultryId());
		productobj.setPrice(productiondto.getPrice());
		productobj.setQuantity(productiondto.getCount() + productobj.getQuantity());
		Breed breed = breedRepository.findBreedNameById(productiondto.getBreedId());
		Category category = categoryRepository.findCategoryById(productiondto.getCategoryId());
		productobj.setProductName(breed.getBreedName() + " " + category.getCategory());
		}else {
			productobj = new Product();
			productobj.setBreedId(productiondto.getBreedId());
			productobj.setCategoryId(productiondto.getCategoryId());
			productobj.setPoultryId(productiondto.getPoultryId());
			productobj.setPrice(productiondto.getPrice());
			productobj.setQuantity(productiondto.getCount());
			Breed breed = breedRepository.findBreedNameById(productiondto.getBreedId());
			Category category = categoryRepository.findCategoryById(productiondto.getCategoryId());
			productobj.setProductName(breed.getBreedName() + " " + category.getCategory());
		}
	Product saveProduct=	productRepositry.saveAndFlush(productobj);
      
			Transaction transaction = salesDAO.findTotalByProductIdProduction(productiondto.getPoultryId(),productiondto.getDate(),TransactionType.IMPORT,saveProduct.getId());
			
			System.err.println("-----------------------------------------------------");
			//System.out.println(transaction.getTotal());
			if (transaction != null) {
				transaction.setTotal(productiondto.getCount()+ transaction.getTotal());
			    transactionRepository.saveAndFlush(transaction);
			}
		     else {
		        transaction = new Transaction();
		        transaction.setProductId(saveProduct.getId());
		        transaction.setTransactionDate(productiondto.getDate());
		        transaction.setPoultryId(productiondto.getPoultryId());
		        transaction.setTransactionType(TransactionType.IMPORT);
		        transaction.setTotal(productiondto.getCount());
		        transactionRepository.saveAndFlush(transaction);
		        System.err.println("-----------------------------------------------------");
		    }

		
			
		}
	

	public List<ProductionRespose> getAll() {

		return productionDAO.findAll();
	}

	public List<ProductionRespose> findById(UUID id) {

		return productionDAO.findById(id);
	}

	public void delete(UUID id) {
		productionRepositry.deleteById(id);
	}

	public void update(Production production) {
//		Production production = new Production();
//		production.setId(productiondto.getId());
//		production.setPoultryId(productiondto.getPoultryId());
//		production.setBreedId(productiondto.getBreedId());
//		production.setCategoryId(productiondto.getCategoryId());
//		production.setCount(productiondto.getCount());
//		production.setDate(productiondto.getDate());
//		production.setExpiryDate(productiondto.getExpiryDate());
//
//		int totalAdditionalQuantity = 0;
//		int totalReductionQuantity = 0;
//
//		Product productobj = productRepositry.findByBreedIdAndCategoryIdAndPoultryId(productiondto.getBreedId(),
//				productiondto.getCategoryId(), productiondto.getPoultryId());
//
//		if (null != productobj) {
//			Production initialQuantity = productionRepositry.findCountById(
//					productiondto.getId());
//			int updatedQuantity = productiondto.getCount();
//			if (null != initialQuantity) {
//				if (updatedQuantity > initialQuantity.getCount()) {
//					int additionalQuantity = updatedQuantity - initialQuantity.getCount();
//					totalAdditionalQuantity += additionalQuantity;
//					productobj.setQuantity(productobj.getQuantity() + additionalQuantity);
//
//				} else if (updatedQuantity < initialQuantity.getCount()) {
//					int reductionQuantity = initialQuantity.getCount() - updatedQuantity;
//					totalReductionQuantity += reductionQuantity;
//					productobj.setQuantity(productobj.getQuantity() - reductionQuantity);
//				}
//				// productionRepositry.save(production);
//
//			} else {
//				productobj = new Product();
//				productobj.setBreedId(productiondto.getBreedId());
//				productobj.setCategoryId(productiondto.getCategoryId());
//				productobj.setPoultryId(productiondto.getPoultryId());
//				productobj.setPrice(productiondto.getPrice());
//				productobj.setQuantity(productiondto.getCount());
//				Breed breed = breedRepository.findBreedNameById(productiondto.getBreedId());
//				Category category = categoryRepository.findCategoryById(productiondto.getCategoryId());
//				productobj.setProductName(breed.getBreedName() + " " + category.getCategory());
//			}
//		}
//		productRepositry.saveAndFlush(productobj);
		productionRepositry.save(production);
	}

	public Production findByBreedIdAndPoultryIdAndCategoryIdAndDate(UUID breedId, UUID poultryId, UUID categoryId,
			String date) {

		return productionRepositry.findByBreedIdAndPoultryIdAndCategoryIdAndDate(breedId, poultryId, categoryId, date);
	}

	public Production findByBreedIdAndPoultryIdAndCategoryIdAndDateExcludeId(UUID breedId, UUID poultryId,
			UUID categoryId, String date, UUID id) {
		return productionDAO.findByBreedIdAndPoultryIdAndCategoryIdAndDateExcludeId(breedId, poultryId, categoryId,
				date, id);
	}

	public void updateProductionStatus() {
		List<Production> productions = productionRepositry.findAll();
		LocalDate currentDate = LocalDate.now().minusDays(2);

		for (Production production : productions) {
			Date expiryDate = production.getExpiryDate();

			// Convert Date to LocalDate
			Instant instant = expiryDate.toInstant();
			ZoneId zoneId = ZoneId.systemDefault();
			LocalDate convertedExpiryDate = instant.atZone(zoneId).toLocalDate();

			boolean isActive = !convertedExpiryDate.isEqual(currentDate);
			if (!isActive) {
				production.setStatus(Status.INACTIVE);
				productionRepositry.save(production);
			}
		}

	}
}
