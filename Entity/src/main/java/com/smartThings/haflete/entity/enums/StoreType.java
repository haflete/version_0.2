package com.smartThings.haflete.entity.enums;

public enum StoreType {
	JEWERLY("مجوهرات وذهب", "Jewerly", 1), CARS("سيارات للايجار", "Cars for rent", 2), HANY_MONE("سياحة وسفر", "Hony mone", 3), CARDS("كروت اعراس", "Cards", 4),
	SALONS("صالونات تجميل", "Salons", 5), DRESSES("فساتين", "Dresses", 6), HALL("قاعات", "Halls", 7);
	
	private String arabicName;
	private String englishName;
	private int code;

	private StoreType(String arabicName, String englishName, int code) {
		this.arabicName = arabicName;
		this.englishName = englishName;
		this.code = code;
	}

	public String getArabicName() {
		return arabicName;
	}

	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}