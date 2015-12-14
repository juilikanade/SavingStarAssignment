package com.savingstar.item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * The class representing the item,has different item attributes
 * @author juilipanse-kanade
 *
 */
public class Item {

	String upc;
	List<String> listDescriptions;

	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUpc() {
		return upc;
	}

	public void setUpc(String upc) {
		this.upc = upc;
	}

	public List<String> getListDescriptions() {
		return listDescriptions;
	}

	public String getDescriptions() {
		return listDescriptions.stream().collect(Collectors.joining(","));

	}

	public void setListDescriptions(List<String> listDescriptions) {
		this.listDescriptions = listDescriptions;
	}

	@Override
	public String toString() {
		return  upc;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((listDescriptions == null) ? 0 : listDescriptions.hashCode());
		result = prime * result + ((upc == null) ? 0 : upc.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (listDescriptions == null) {
			if (other.listDescriptions != null)
				return false;
		} else if (!listDescriptions.equals(other.listDescriptions))
			return false;
		if (upc == null) {
			if (other.upc != null)
				return false;
		} else if (!upc.equals(other.upc))
			return false;
		return true;
	}

	public void printDetails() {
		System.out.println("upc:"+upc+" descriptions:[ "+getDescriptions()+" ]");
		
	}

}
