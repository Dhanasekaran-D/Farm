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

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="tb_breed")

public class Breed extends RecordModifier implements Serializable{
private static final long serialVersionUID = 1L;

@Id
@GeneratedValue(generator = "UUID")
@GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
@Type(type = "uuid-char")
@Column(name = "id", updatable = false, nullable = false)
private UUID id;

@Column(name = "breed_name")
private String breedName;

@Type(type = "uuid-char")
@Column(name = "breed_type_id")
private UUID breedTypeId;

@Column(name = "descriptions")
private String description;
}
