package nl.spacedebris.monolith.repository.web;

import io.quarkus.security.Authenticated;
import nl.spacedebris.monolith.service.PaymentService;
import nl.spacedebris.monolith.repository.web.dto.PaymentDto;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Authenticated
@Path("/payments")
@Tag(name = "payment", description = "All the payment methods")
public class PaymentResource {

    @Inject
    PaymentService paymentService;

    @RolesAllowed("admin")
    @GET
    public List<PaymentDto> findAll() {
        return this.paymentService.findAll();
    }

    @GET
    @Path("/{id}")
    public PaymentDto findById(@PathParam("id") Long id) {
        return this.paymentService.findById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public PaymentDto create(PaymentDto orderItemDto) {
        return this.paymentService.create(orderItemDto);
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        this.paymentService.delete(id);
    }

    @GET
    @Path("/price/{price}")
    public List<PaymentDto> findPaymentsByAmountRangeMax(@PathParam("price") double max) {
        return this.paymentService.findByPriceRange(max);
    }
}