package com.yxsd.kanshu.search.service;

public interface IndexService {

	public void createIndex(int start, int pageSize, int end);

	public void createIndex(int start, int end);

	public void createIndex(int start);

	public void createIndex();

}
