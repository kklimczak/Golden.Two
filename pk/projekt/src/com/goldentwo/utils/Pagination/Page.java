package com.goldentwo.utils.Pagination;

import java.util.List;

public class Page <T> {
	private List<T> content;
	private int totalElements;
	private int totalPages;
	private int size;
	private int currentPage;
	private Sort sort;
	private Filter filter;

	public Page(List<T> content, int totalElements, int totalPages, int size, int currentPage, Sort sort, Filter filter) {
		this.content = content;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
		this.size = size;
		this.currentPage = currentPage;
		this.sort = sort;
		this.filter = filter;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	@Override
	public String toString() {
		return "totalElements: " + totalElements + ", totalPages: " + totalPages + ", size: " + size
				+ ", currentPage: " + currentPage + ", sort: " + sort + ", filter: " + filter;
	}
	
	
}
