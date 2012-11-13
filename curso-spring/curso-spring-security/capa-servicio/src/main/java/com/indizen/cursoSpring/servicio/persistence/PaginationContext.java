package com.indizen.cursoSpring.servicio.persistence;

import java.io.Serializable;

import com.indizen.cursoSpring.servicio.util.Constants;


/**
 * The PaginationContext class simply holds the parameters 
 * (how large is the page and on which page are we currently?) 
 * and the total number of records:
 *
 */
public class PaginationContext implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final int NO_TOTAL_COUNT = -1;
	private int skipResults = 0;
	private int maxResults = Constants.MAX_RESULTS_TABLE;
	private int totalCount = NO_TOTAL_COUNT;
	private int totalPages=0;
	private int currentPage=0;
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getCurrentPage() {
		currentPage=1+(skipResults/maxResults);
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPages() {
		totalPages=totalCount/maxResults;
		if(totalCount%maxResults>0){
			totalPages++;
		}
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getSkipResults() {
		return skipResults;
	}

	public void setSkipResults(int skipResults) {
		assert skipResults >= 0;
		this.skipResults = skipResults;
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		assert maxResults >= 0;
		this.maxResults = maxResults;
	}

	public void updateTotalCount(int totalCount) {
		assert totalCount >= 0;
		this.totalCount = totalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void nextPage() {
		if (skipResults + maxResults  < totalCount){
			skipResults += maxResults;
		}		
	}
	
	public void previousPage() {
		if (skipResults - maxResults  > 0){
			skipResults -= maxResults;
		}else{ 
			skipResults=0;	
		}
	}
	
	public void firstPage(){
		skipResults=0;
	}

	public void lastPage(){	
		skipResults = (getTotalPages() - 1) * maxResults;
	}
	
	public boolean hasTotalCount() {
		return totalCount != NO_TOTAL_COUNT;
	}
}