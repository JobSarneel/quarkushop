package nl.spacedebris.monolith.repository.web;

import io.quarkus.security.Authenticated;
import nl.spacedebris.monolith.service.CustomerService;
import nl.spacedebris.monolith.repository.web.dto.CustomerDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/customers")
@Authenticated
@Tag(name = "customer", description = "All the customer methods")
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @GET
    public List<CustomerDto> findAll() {
        return this.customerService.findAll();
    }

    @GET
    @Path("/{id}")
    public CustomerDto findById(@PathParam("id") Long id) {
        return this.customerService.findById(id);
    }

    @GET
    @Path("/active")
    public List<CustomerDto> findAllActive() {
        return this.customerService.findAllActive();
    }

    @GET
    @Path("/inactive")
    public List<CustomerDto> findAllInactive() {
        return this.customerService.findAllInactive();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public CustomerDto create(CustomerDto customerDto) {
        return this.customerService.create(customerDto);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.customerService.delete(id);
    }
}