package com.smartThings.haflete.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Where;

import com.smartThings.haflete.entity.enums.StoreType;
import com.smartThings.haflete.entity.util.SuperEntity;

@Entity
@Table(name = "Store")
public class Store extends SuperEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String name;
	
	@Enumerated(EnumType.STRING)
	private StoreType type;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "SELLER_ID" )
	private Seller seller;

	@OneToMany(mappedBy="store", fetch=FetchType.LAZY)
	@Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
	private List<Item> items;
	
	@OneToOne(mappedBy="store", fetch=FetchType.LAZY)
	@Cascade(value={org.hibernate.annotations.CascadeType.ALL})
	private AddressInfo addressInfo;
	
	public Store() {
		super();
		items = new ArrayList<>();
	}

	public AddressInfo getAddressInfo() {
		return addressInfo;
	}
	
	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StoreType getType() {
		return type;
	}

	public void setType(StoreType type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
}
