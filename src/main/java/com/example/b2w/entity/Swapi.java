package com.example.b2w.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Swapi {

	private Integer count;

	private Object next;

	private Object previous;

	private List<Planeta> results = null;
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Object getNext() {
		return next;
	}

	public void setNext(Object next) {
		this.next = next;
	}

	public Object getPrevious() {
		return previous;
	}

	public void setPrevious(Object previous) {
		this.previous = previous;
	}

	public List<Planeta> getResults() {
		return results;
	}

	public void setResults(List<Planeta> results) {
		this.results = results;
	}

	public Map<String, Object> getAdditionalProperties() {
		return additionalProperties;
	}

	public void setAdditionalProperties(Map<String, Object> additionalProperties) {
		this.additionalProperties = additionalProperties;
	}



}
