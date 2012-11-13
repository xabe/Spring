package com.indizen.springCurso;

public class Movie {
	private String title;
	private int year;
	
	public Movie() {
	}
	
	public Movie(String title,int year) {
		this.title = title;
		this.year = year;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "Title: "+getTitle()+" Year: "+getYear();
	}
}
