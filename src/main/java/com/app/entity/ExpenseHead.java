package com.app.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.lang.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_expense_head")
public class ExpenseHead extends RecordModifier implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	
	@NonNull
	@Column(name = "expense_type")
	private String expenseType;

	}
