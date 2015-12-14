package com.item.inventory.service;
/**
 * The class which is used by the external service to represent item
 * @author juilipanse-kanade
 *
 */
public class ItemInventoryInfo {

	long qty;
	double price;
	String description;

	public long getQty() {
		return qty;
	}

	public void setQty(long qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return " [qty=" + qty + ", price=" + price + "]";
	}

}
