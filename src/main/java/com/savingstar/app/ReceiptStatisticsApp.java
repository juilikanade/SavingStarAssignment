package com.savingstar.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.item.inventory.service.InventoryService;
import com.item.inventory.service.ItemInventoryInfo;
import com.savingstar.item.Item;
import com.savingstar.util.SampleDataCreator;
/**
 * The main class which gives the receipt statistics
 * @author juilipanse-kanade
 *
 */
public class ReceiptStatisticsApp {
	
	private SampleDataCreator creator = new SampleDataCreator();
	private Map<Item, Long> mapQty = new HashMap<Item, Long>();
	private List<Item> listItems;
	private InventoryService service = new InventoryService();
	private Map<String, ItemInventoryInfo> mapFromInventoryService = null;

	private TreeMap<String, List<Item>> mapDescriptions;

	public ReceiptStatisticsApp() {
		// Fetch the data
		listItems = creator.getData();
		System.out.println("List of Items - > " );
		listItems.forEach(item->{
			item.printDetails();
		});
		
	}

	/**
	 * The method which processes the fetched data
	 */
	private void processData() {

		// Group the data according to description
		mapDescriptions = groupDataByDescriptions();

		System.out.println("\nGrouping of items by description - > "
				+ mapDescriptions);

		// fetch the item information for the descriptions all at once - this is
		// considered for
		// 10 items-could be done in batches
		mapFromInventoryService = service.fetchItemInfo(mapDescriptions.keySet());

		System.out
				.println("\nInformation for quantity and price returned from external service -> "
						+ mapFromInventoryService);

		// Distribute equally the qty received for each dscription between the
		// items
		mapDescriptions.forEach((strDesc, listItem) -> {
			distributeQty(strDesc, listItem);
		});

		System.out
				.println("\nResult of distributing the quantities equally among all the items -> ");

		mapQty.keySet().forEach(item -> {
			System.out.println(item.getUpc() + "--" + mapQty.get(item));
		});

	}

	/**
	 * The method which groups data by the descriptions
	 * @return
	 */
	private TreeMap<String, List<Item>> groupDataByDescriptions() {
		TreeMap<String, List<Item>> map = new TreeMap<String, List<Item>>(
				String.CASE_INSENSITIVE_ORDER);
		List<Item> listUpc = null;

		//This nested for loop could be avoided by Collectors.groupBy method- custom collector may be required
		for (Item item : listItems) {
			List<String> listDesc = item.getListDescriptions();
			for (String desc : listDesc) {
				listUpc = map.getOrDefault(desc, new ArrayList<Item>());
				listUpc.add(item);
				map.put(desc, listUpc);
			}
		}
		return map;
	}

	/**
	 * The method which divided the received quantity equally among the items
	 * @param strDesc
	 * @param listItem
	 */
	private void distributeQty(String strDesc, List<Item> listItem) {

		ItemInventoryInfo itemInfo = (ItemInventoryInfo) mapFromInventoryService
				.get(strDesc.toLowerCase());
		long qty = itemInfo.getQty();
		//The case where the received quantities cant be equally divided is not handled in the code
		long qtyPerItem = qty / listItem.size();
		listItem.forEach((item) -> {
			long qtyInMap = mapQty.getOrDefault(item, (long) 0);
			mapQty.put(item, qtyInMap + qtyPerItem);

		});

	}
	
	
	public static void main(String[] args) {
		new ReceiptStatisticsApp().processData();
	}

	

}
