package nl.spacedebris.monolith.repository.web;

import io.quarkus.security.Authenticated;
import nl.spacedebris.monolith.service.ReviewService;
import nl.spacedebris.monolith.repository.web.dto.ReviewDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/reviews")
@Tag(name = "review", description = "All the review methods")
public class ReviewResource {

    @Inject
    ReviewService reviewService;

    @GET
    @Path("/product/{id}")
    public List<ReviewDto> findAllByProduct(@PathParam("id") Long id) {
        return this.reviewService.findReviewsByProductId(id);
    }

    @GET
    @Path("/{id}")
    public ReviewDto findById(@PathParam("id") Long id) {
        return this.reviewService.findById(id);
    }

//    @Authenticated
    @POST
    @Path("/product/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public ReviewDto create(ReviewDto reviewDto, @PathParam("id") Long id) {
        return this.reviewService.create(reviewDto, id);
    }

  //  @RolesAllowed("admin")
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.reviewService.delete(id);
    }
}