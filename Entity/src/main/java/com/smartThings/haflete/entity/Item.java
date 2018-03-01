package com.smartThings.haflete.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.smartThings.haflete.entity.util.SuperEntity;

@Entity
@Table(name = "Item")
public class Item extends SuperEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name = "STORE_ID")
	private Store store;
	
	@OneToMany(mappedBy="item", fetch=FetchType.LAZY)
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	private List<ItemMedia> mediaList;
	
	public Item() {
		super();
		mediaList = new ArrayList<>();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public List<ItemMedia> getMediaList() {
		return mediaList;
	}

	public void setMediaList(List<ItemMedia> mediaList) {
		this.mediaList = mediaList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
