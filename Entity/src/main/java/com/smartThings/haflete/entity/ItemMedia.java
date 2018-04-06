package com.smartThings.haflete.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.smartThings.haflete.entity.enums.MediaType;
import com.smartThings.haflete.entity.enums.UploadedMethod;
import com.smartThings.haflete.entity.util.SuperEntity;

@Entity
@Table(name = "ITEM_Media")
public class ItemMedia extends SuperEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column
	private String name;

	@Column
	private String url;
	
	@Column
	private String fullDir;
	
	@Column
	private String thumbUrl;
	
	@Column
	private String thumbFullDir;
	
	@Enumerated(EnumType.STRING)
	private MediaType type;
	
	@Enumerated(EnumType.STRING)
	private UploadedMethod uploadedMethod;
	
	@ManyToOne
	@JoinColumn(name = "ITEM_ID")
	private Item item;
	
	@Column 
	private String ext;
	
	@Column
	private int size;
	
	@Transient
	private byte[] contents;

	public UploadedMethod getUploadedMethod() {
		return uploadedMethod;
	}

	public void setUploadedMethod(UploadedMethod uploadedMethod) {
		this.uploadedMethod = uploadedMethod;
	}

	public String getFullDir() {
		return fullDir;
	}

	public void setFullDir(String fullDir) {
		this.fullDir = fullDir;
	}

	public String getThumbFullDir() {
		return thumbFullDir;
	}

	public void setThumbFullDir(String thumbFullDir) {
		this.thumbFullDir = thumbFullDir;
	}

	public String getThumbUrl() {
		return thumbUrl;
	}

	public void setThumbUrl(String thumbUrl) {
		this.thumbUrl = thumbUrl;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
