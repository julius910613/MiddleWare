package org.jboss.as.quickstarts.kitchensink.rest;

import org.jboss.as.quickstarts.kitchensink.data.*;
import org.jboss.as.quickstarts.kitchensink.model.Customer;
import org.jboss.as.quickstarts.kitchensink.service.*;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 05/11/13
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */

@Path("/customers")
@RequestScoped
public class CustomerResourceRESTService {
    @Inject
    CustomerRegistration registration;
    @Inject
    private Logger log;
    @Inject
    private Validator validator;
    @Inject
    private CustomerRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Customer> listAllMembers() {
        return repository.findAllOrderedByName();
    }

    @GET
    @Path("/{driverLicenseID:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer lookupMemberById(@PathParam("driverLicenseID") long id) {
        Customer customer = repository.findById(id);
        if (customer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return customer;
    }

    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(Customer customer) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            validateCustomer(customer);

            registration.register(customer);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {
            log.info("ffghgh");
            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());

        } catch (ValidationException e) {
            log.info("wgsgsgsg");
            // Handle the unique constrain violation
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("Email", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            log.info("1241344");
            // Handle generic exceptions
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }
    private void validateCustomer(Customer customer) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }

        // Check the uniqueness of the email address
        if (driverLisenceAlreadyExists(customer.getDriverLicenseID())) {
            throw new ValidationException("Unique Driver License ID Violation");
        }
    }

    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
            log.info(violation.getPropertyPath().toString());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    public boolean driverLisenceAlreadyExists(String driverlicense) {
        Customer customer = null;
        try {
            customer = repository.findByDriverLisence(driverlicense);
        } catch (NoResultException e) {
            // ignore
        }
        return customer != null;
    }

}
