package com.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.app.enumeration.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_transactional")
public class Transaction extends RecordModifier implements Serializable {
	
	private static final long serialVersionUID = 1L;

    
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	
	//@JsonFormat(pattern = "yyyy/MM/dd")
	@Column(name = "transaction_date")
	private String transactionDate;
	
	@Column(name = "total")
	private Integer total;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type")
	private TransactionType transactionType;
	
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "product_id")
	private UUID productId;
	
	@Type(type = "uuid-char")
	@Column(name = "poultry_id")
	private UUID poultryId;


}
