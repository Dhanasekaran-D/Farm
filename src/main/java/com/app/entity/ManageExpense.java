package com.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_manage_expense")

public class ManageExpense extends RecordModifier implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "descriptions")
	private String description;
	
   @JsonFormat(pattern = "yyyy/MM/dd")
	@Column(name = "date")
	private String date;
	
	@Column(name = "amount")
	private Integer amount;

	@Type(type = "uuid-char")
	@Column(name = "expense_head_id")
	private UUID expenseHeadId;
	
	@Type(type = "uuid-char")
	@Column(name = "poultry_id")
	private UUID poultryId;

}
