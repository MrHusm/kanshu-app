package com.yxsd.kanshu.base.contants;

/**
 * 
 * @author qiong.wang
 *
 */
public enum SearchEnum {
	title("title"), author_penname("author_penname"), author_name("author_name"), intro("intro"), category_sec_name(
			"category_sec_name"), category_thr_name("category_thr_name");

	private String searchField;

	private SearchEnum(String searchField) {
		this.searchField = searchField;
	}

	public static SearchEnum getBySearchEnum(String searchField) {
		SearchEnum[] arr$ = values();
		int len$ = arr$.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			SearchEnum searchEnum = arr$[i$];
			if (searchEnum.searchField == searchField) {
				return searchEnum;
			}
		}

		return null;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

}
