package com.itsecasia.example.dto.requests;


public class SearchPayloadRequestDTO {

	String q;

	String filter;

	String[] attributesToHighlight;

	String[] sort;

	int limit = 100;

	String matchingStrategy = "all";
	
	boolean isAllField;

	public String getQ() {
		return this.q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getFilter() {
		return this.filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String[] getAttributesToHighlight() {
		return this.attributesToHighlight;
	}

	public void setAttributesToHighlight(String[] attributesToHighlight) {
		this.attributesToHighlight = attributesToHighlight;
	}

	public String[] getSort() {
		return this.sort;
	}

	public void setSort(String[] sort) {
		this.sort = sort;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getMatchingStrategy() {
		return this.matchingStrategy;
	}

	public void setMatchingStrategy(String matchingStrategy) {
		this.matchingStrategy = matchingStrategy;
	}

	public boolean isAllField() {
		return isAllField;
	}

	public void setIsAllField(boolean isAllField) {
		this.isAllField = isAllField;
	}

	
}
