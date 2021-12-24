package org.popins.dev;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.popins.dev.model.Movie;


@Path("/movies")
public class MovieResource {
	
	@Inject
	MovieRepository movieRepository;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovies() {
		List<Movie> listAll = movieRepository.listAll();
		return Response.ok(listAll).build();
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieById(@PathParam("id") Long id) {
		return movieRepository.findByIdOptional(id)
			     .map(movie -> Response.ok(movie).build())
			     .orElse(Response.status(Response.Status.NOT_FOUND).build());
	}
	
	@GET
	@Path("country/{country}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieByCountry(@PathParam("country") String country) {
		List<Movie> movies = movieRepository.findByCountry(country);
		return Response.ok(movies).build();
	}
	
	@GET
	@Path("title/{title}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMovieByTitle(@PathParam("title") String title) {
		return movieRepository.find("title", title)
			     .singleResultOptional()
			     .map(movie -> Response.ok(movie).build())
			     .orElse(Response.status(Response.Status.NOT_FOUND).build());
	}
	
	@POST
	@Transactional
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response persistMovie(Movie movie) {
		movieRepository.persist(movie);
		return movieRepository.isPersistent(movie) ? Response.created(URI.create("/movies/"+movie.getId())).build()
				                    : Response.status(Response.Status.BAD_REQUEST).build();
	}
	
	@DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteMovieBydId(@PathParam("id") Long id) {
		 boolean deleted = movieRepository.deleteById(id);
	        if(deleted) {
	            return Response.noContent().build();
	        }
	        return Response.status(Response.Status.BAD_REQUEST).build();
	}
}
