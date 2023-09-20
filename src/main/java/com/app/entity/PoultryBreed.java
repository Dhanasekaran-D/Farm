package com.app.entity;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_breed_mapping")
public class PoultryBreed  extends RecordModifier implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	@Type(type = "uuid-char")
	@Column(name = "id", updatable = false, nullable = false)
	private UUID id;
	

	@Type(type = "uuid-char")
	@Column(name = "breed_id")
	private UUID breedId;
	
	@Column(name = "total_count")
	private Integer totalCount;
	
	 @JsonBackReference
	 @ManyToOne
    @JoinColumn(name = "poultrymapping_id", nullable = false, insertable = false, updatable = false)
	 private PoultryMapping poultryMapping;
	
}
