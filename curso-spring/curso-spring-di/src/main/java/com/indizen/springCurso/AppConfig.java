package com.indizen.springCurso;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean(name="movieFinder")
    public MovieFinder movieFinder() {
        return new MovieFinderImpl();
    }

	@Bean(name="movieListen")
	public MovieListen movieListen(){
		MovieListen movieListen = new MovieListen();
		movieListen.setMovieFinder(movieFinder());
		return movieListen;
	}
}
