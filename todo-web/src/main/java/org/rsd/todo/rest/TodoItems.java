package org.rsd.todo.rest;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;
import org.rsd.todo.dao.ItemDao;
import org.rsd.todo.model.Item;

//@Local
@Path("/items")
@Stateless
//@Produces(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_XML)
public class TodoItems {
	
	@Inject
	ItemDao itemDao;
	
	@Inject Logger log;
	
	@GET
	//@Produces("text/plain")
	//@Produces(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	//public List<Item> findItems() {
	public Response findItems() {
		log.info("getting all items");
		//return new Items(itemDao.findAll());
		return Response.ok(itemDao.findAll(), MediaType.APPLICATION_JSON).build();
	};
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)	
	public Item addItem(Item item) {
		log.info("Storing an item "+item);
		itemDao.persist(item);
		return item;
	}
	
	@DELETE
	@Path("{itemId : \\d+}")	
	public Long deleteItem(@PathParam("itemId") Long itemId) {
		log.info("Deleting an item "+itemId);
		// Find the item by id
		Item item = itemDao.find(itemId);
		itemDao.remove(item);
		return itemId;
	}
}
