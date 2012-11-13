package com.indizen.springCurso;

import java.util.Arrays;
import java.util.List;

public class MovieFinderImpl implements MovieFinder {

	public List<Movie> findAll() {
		return Arrays.asList(new Movie [] {new Movie("El viaje de chihiro", 2002),new Movie("Avatar", 2009)});
	}
}
