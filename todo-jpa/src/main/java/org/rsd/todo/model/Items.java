package org.rsd.todo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@SuppressWarnings("serial")
@XmlRootElement
@XmlSeeAlso(Item.class)
public class Items extends ArrayList<Item> {

  // ======================================
  // =            Constructors            =
  // ======================================

  public Items() {
    super();
  }

  public Items(Collection<? extends Item> c) {
    super(c);
  }

  // ======================================
  // =          Getters & Setters         =
  // ======================================

  @XmlElement(name = "Item")
  public List<Item> getItems() {
    return this;
  }

  public void setItems(List<Item> items) {
    this.addAll(items);
  }
}