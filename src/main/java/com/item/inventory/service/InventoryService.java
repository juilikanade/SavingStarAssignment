package com.item.inventory.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 * This class acts as the external service which provides the quantities for list of descriptions
 * @author juilipanse-kanade
 *
 */
public class InventoryService {

	List<ItemInventoryInfo> listRepo = new ArrayList<ItemInventoryInfo>();
	ItemInventoryInfo itemInfo = null;

	public InventoryService() {
		createRepo();
	}

	@SuppressWarnings("unchecked")
	/**
	 * This method creates the repo by reading data from json file
	 */
	private void createRepo() {

		ClassLoader classLoader = getClass().getClassLoader();
		JSONParser parser = new JSONParser();

		try {
			InputStream stream = classLoader
					.getResourceAsStream("inventory.csv");
			Reader reader = new InputStreamReader(stream);
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			JSONArray arrItems = (JSONArray) jsonObject.get("items");

			arrItems.forEach(jsonItem -> {
				ItemInventoryInfo item = new ItemInventoryInfo();
				JSONObject jsonObjectTemp = (JSONObject) ((JSONObject) jsonItem);
				String desc = (String) jsonObjectTemp.get("description");
				long qty = Long.parseLong(jsonObjectTemp.get("qty").toString());
				double price = Double.parseDouble(jsonObjectTemp.get("price")
						.toString());
				item.setDescription(desc);
				item.setPrice(price);
				item.setQty(qty);

				listRepo.add(item);

			});

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * This method returns map of quantities for descriptions.
	 * For convenience ItemInventoryInfo is used to represent item returned from service.
	 * @param setDesc
	 * @return
	 */
	public Map<String, ItemInventoryInfo> fetchItemInfo(Set<String> setDesc) {

		//filtering data for only the descriptions provided
		List<ItemInventoryInfo> listRet = listRepo
				.stream()
				.filter(itemInfo -> setDesc.contains(itemInfo.getDescription()))
				.collect(Collectors.toList());
		//map the data filtered by description
		HashMap<String, ItemInventoryInfo> result = (HashMap<String, ItemInventoryInfo>) listRet
				.stream().collect(
						Collectors.toMap(ItemInventoryInfo::getDescription,
								Function.identity()));

		return result;
	}

}
