package com.app.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_product_sales")
public class ProductSales extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;

	@GeneratedValue(strategy = GenerationType.AUTO, generator = "sales_no")
	@SequenceGenerator(name = "salesNo", sequenceName = "sales_no", allocationSize = 1, initialValue = 1)
	@Column(name = "sales_no", nullable = false, unique = true)
	private String salesNo;

	@Column(name = "sub_total")
	private Double subTotal;

	@Column(name = "discount")
	private Double discount;

	@Column(name = "total_payable")
	private Double totalPayable;

	@JsonFormat(pattern = "yyyy/MM/dd")
	@Column(name = "sales_date")
	private String salesDate;

	@JsonFormat(pattern = "yyyy/MM/dd")
	@Column(name = "delivery_date")
	private Date deliveryDate;

	@Type(type = "uuid-char")
	@Column(name = "poultry_id")
	private UUID poultryId;

	@Type(type = "uuid-char")
	@Column(name = "customer_id")
	private UUID customerId;

	@Column(name = "sales_notes")
	private String salesNote;

	@JsonManagedReference
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_sales_id", referencedColumnName = "id", nullable = false)
	private List<Sales> sales;

	@PrePersist
	@PreUpdate
	public void calculateTotals() {
		
		subTotal = sales.stream().mapToDouble(sale -> sale.calculateAmount()).sum();
		totalPayable = subTotal - discount;
		
	}


}
