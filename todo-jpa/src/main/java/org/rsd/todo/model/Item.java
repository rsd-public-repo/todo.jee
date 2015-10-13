package org.rsd.todo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllItems", query = "SELECT i FROM Item i"),
		@NamedQuery(name = "findNewItem", query = "SELECT i FROM Item i WHERE i.details = 'New Item'"),
		@NamedQuery(name = "findDoneItems", query = "SELECT i FROM Item i WHERE i.done = true"),
		@NamedQuery(name = "findActiveItems", query = "SELECT i FROM Item i WHERE i.done = false") })
public class Item implements Serializable {

	private static final long serialVersionUID = 8742040021467443919L;

	public Item() {
	}

	public Item(String details, Boolean done) {
		this.details = details;
		this.done = done;
	}

	public Long getId() {
		return _id;
	}

	public void setId(Long id) {
		this._id = id;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Boolean getDone() {
		return done;
	}

	public void setDone(Boolean done) {
		this.done = done;
	}

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long _id;
	@NotNull
	private String details;
	private Boolean done;
}
