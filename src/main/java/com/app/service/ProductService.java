package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.ProductDAO;
import com.app.dto.PoultryResponse;
import com.app.dto.ProductDTO;
import com.app.dto.ProductResponse;
import com.app.entity.Product;
import com.app.repository.ProductRepositry;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@Service
@Transactional
@AllArgsConstructor(onConstructor_ = { @Autowired })
public class ProductService {
	private @NonNull ProductRepositry productRepositry;
	private @NonNull ProductDAO productDAO;

	public void save(Product product) {
		productRepositry.save(product);
	}

	public List<ProductResponse> getAll() {
		return productDAO.findAll();
	}

	public List<ProductResponse> get(UUID id) {
		return productDAO.getById(id);
	}

//	public Object getBypoultryId(UUID poultryId) {
//		return productDAO.getByPoultryId(poultryId);
//	}

	public void update(Product product) {
		productRepositry.saveAndFlush(product);

	}

	public void delete(UUID productId) {
		productRepositry.deleteById(productId);

	}

	public Product findByName(UUID breedId, UUID categoryId, UUID poultryId) {
		// TODO Auto-generated method stub
		return productRepositry.findByBreedIdAndCategoryIdAndPoultryId(breedId,categoryId,poultryId);
	}

	public Optional<Product> getid(UUID id) {
		return productRepositry.findById(id);
	}

	public Product findByNameExcludeId(UUID breedId, UUID categoryId, UUID poultryId, UUID id) {
		return productDAO.findByNameExcludeId(breedId,categoryId,poultryId,id);
	}

	public Object getpoultryId(UUID poultryId) {
		return productDAO.getpoultryId(poultryId);
	}



	
	public Object getProduct() {
		return productDAO.getProduct();
	}

	public Object getProductQuantity() {
		
		return productDAO.getProductSale();
	}

	

	




}
