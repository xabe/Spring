package com.indizen.springCurso;

import java.util.List;

import org.apache.log4j.Logger;

public class MovieListen {
	private static final Logger LOGGER = Logger.getLogger(MovieListen.class);
	private MovieFinder movieFinder;
	
	public void init(){
		LOGGER.info("Create MovieListen");
	}
	
	public void setMovieFinder(MovieFinder movieFinder) {
		this.movieFinder = movieFinder;
	}
	
	public void seeAllMovie(){
		List<Movie> movies = movieFinder.findAll();
		LOGGER.info("See all Find :");
		for(Movie movie: movies){
			LOGGER.info("\t"+movie.toString());
		}
		LOGGER.info("End all movie");
	}
	
	public void destroy(){
		LOGGER.info("Delete MovieListen");
	}
}
