/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import cst8218.masa0019.mavenproject3.persistence.Contact;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Homeoffice
 */
@Stateless
@Path("cst8218.masa0019.mavenproject3.persistence.contact")
public class ContactFacadeREST extends AbstractFacade<Contact> {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public ContactFacadeREST() {
        super(Contact.class);
    }



    @Context UriInfo uriInfo; 
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createPost (Contact entity) throws URISyntaxException {
                System.out.println("we are editing post");
        super.create(entity);
        URI location = new URI(uriInfo.getRequestUri().getPath() + "/" + entity.getId());
        return Response.status(Response.Status.CREATED).location(location).entity(entity).build();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response edit(@PathParam("id") Long id, Contact entity) {
            try {
            Contact contact = super.find(id);
            em.clear();
            if ( contact != null ) {
                      System.out.println("we are editing put "+ id );
                contact.setBirthday(entity.getBirthday());
                contact.setEmail(entity.getEmail());
                contact.setFirstname(entity.getFirstname());
                contact.setHomephone(entity.getHomephone());
                contact.setLastname(entity.getLastname());
                contact.setMobilephone(entity.getMobilephone());
                super.edit(contact);
            } else {
                throw new Exception ("no id");
            }
            return Response.status(Response.Status.OK).build(); 
            //return Response.status(Response.Status.OK).entity(contact).build(); //debug   
        } catch (Exception e) {
            return  Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Contact find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Contact> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Contact> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
