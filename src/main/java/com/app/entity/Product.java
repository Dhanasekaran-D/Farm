package com.app.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_product")
public class Product extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@Column(name = "product_name")
	 private String productName;

	@Type(type = "uuid-char")
	@Column(name = "breed_id")
	private UUID breedId;
	
	@Type(type = "uuid-char")
	@Column(name = "category_id")
	private UUID categoryId;
	

    @Column(name = "price")
	private Double price;
    
	

    @Column(name = "quantity")
	private Integer quantity;
	
//	@Column(name = "descriptions")
//	private String descriptions;
//	
	@Type(type = "uuid-char")
	@Column(name = "poultry_id")
	private UUID poultryId;

	@Transient
	private Integer saleQuantity;
	
}
