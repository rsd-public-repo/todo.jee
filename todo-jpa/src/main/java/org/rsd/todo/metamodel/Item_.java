package org.rsd.todo.metamodel;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import org.rsd.todo.model.Item;

@StaticMetamodel(Item.class)
public class Item_ {
	public static volatile SingularAttribute<Item, Long> _id;
	public static volatile SingularAttribute<Item, String> details;
	public static volatile SingularAttribute<Item, Boolean> done;
}
