package org.jboss.as.quickstarts.kitchensink.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

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

import org.jboss.as.quickstarts.kitchensink.data.ContractRepository;
import org.jboss.as.quickstarts.kitchensink.model.Customer;
import org.jboss.as.quickstarts.kitchensink.model.contract;
import org.jboss.as.quickstarts.kitchensink.service.ContractRegistration;

/**
 * Created with IntelliJ IDEA.
 * User: cmdadmin
 * Date: 12/11/13
 * Time: 14:17
 * To change this template use File | Settings | File Templates.
 */

@Path("/contracts")
@RequestScoped
public class ContractRESTService {

    @Inject
    ContractRegistration registration;
    @Inject
    private Logger log;
    @Inject
    private Validator validator;
    @Inject
    private ContractRepository repository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<contract> listAllMembers() {
        return repository.findAllOrderedByCustomerName();
    }

    @GET
    @Path("/{contractID:[0-9][0-9]*}")
    @Produces(MediaType.APPLICATION_JSON)
    public contract lookupMemberById(@PathParam("contractID") long id) {
        contract contract = repository.findById(id);
        if( contract == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return contract;
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{deleteContractID:[0-9][0-9]*}")
    public Response deleteContract(@PathParam("deleteContractID") long id){
       log.info("123");
        Response.ResponseBuilder builder = null;
        contract contract = repository.findById(id);
        try{
            registration.delete(contract);
            builder = Response.ok();
        } catch(Exception e){
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("", "Wrong");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        }

        return builder.build();
    }

    @DELETE
    @Path("/{contractDate}/{personID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteContractForConnectionError(@PathParam("contractDate") String dayOfContract, @PathParam("personID") Long customerID) {
        Response.ResponseBuilder builder = null;
        contract contractForDelete = repository.findByContractDate(customerID , dayOfContract);
        try{
            registration.delete(contractForDelete);
            builder = Response.ok();
        } catch(Exception e){
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("", "Wrong");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * Creates a new member from the values provided. Performs validation, and will return a JAX-RS response with either 200 ok,
     * or with a map of fields, and related errors.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createMember(contract contract) {

        Response.ResponseBuilder builder = null;

        try {
            // Validates member using bean validation
            validateMember(contract);

            registration.register(contract);

            // Create an "ok" response
            builder = Response.ok();
        } catch (ConstraintViolationException ce) {

            // Handle bean validation issues
            builder = createViolationResponse(ce.getConstraintViolations());
            log.info("1" +builder.build());
        } catch (ValidationException e) {
            // Handle the unique constrain violation
            log.info("2" +e.getMessage());
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("", "Email taken");
            builder = Response.status(Response.Status.CONFLICT).entity(responseObj);
        } catch (Exception e) {
            // Handle generic exceptions
            log.info("3" +e.getMessage());
            Map<String, String> responseObj = new HashMap<String, String>();
            responseObj.put("error", e.getMessage());
            builder = Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
        }

        return builder.build();
    }

    /**
     * <p>
     * Validates the given Member variable and throws validation exceptions based on the type of error. If the error is standard
     * bean validation errors then it will throw a ConstraintValidationException with the set of the constraints violated.
     * </p>
     * <p>
     * If the error is caused because an existing member with the same email is registered it throws a regular validation
     * exception so that it can be interpreted separately.
     * </p>
     *
     * @param contract Taxi to be validated
     * @throws ConstraintViolationException If Bean Validation errors exist
     * @throws ValidationException If member with the same email already exists
     */
    private void validateMember(contract contract) throws ConstraintViolationException, ValidationException {
        // Create a bean validator and check for issues.
        Set<ConstraintViolation<contract>> violations = validator.validate(contract);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(new HashSet<ConstraintViolation<?>>(violations));
        }
        if (customerHasBookedAtSameTime(contract.getCustomer().getId(), contract.getContractDate())) {
            throw new ValidationException("A customer can only make one booking one day");
        }


    }

    /**
     * Creates a JAX-RS "Bad Request" response including a map of all violation fields, and their message. This can then be used
     * by clients to show violations.
     *
     * @param violations A set of violations that needs to be reported
     * @return JAX-RS response containing all violations
     */
    private Response.ResponseBuilder createViolationResponse(Set<ConstraintViolation<?>> violations) {
        log.fine("Validation completed. violations found: " + violations.size());

        Map<String, String> responseObj = new HashMap<String, String>();

        for (ConstraintViolation<?> violation : violations) {
            responseObj.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        return Response.status(Response.Status.BAD_REQUEST).entity(responseObj);
    }

    public boolean customerHasBookedAtSameTime(Long customerID, String dateOfContract ) {
        contract contract = null;
        try {
            contract = repository.findByContractDate(customerID, dateOfContract);
        } catch (NoResultException e) {
            // ignore
        }
        return contract != null;
    }

}
