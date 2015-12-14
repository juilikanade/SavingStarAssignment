package com.savingstar.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.savingstar.item.Item;
/**
 * The class reads data from json file which forms the input for this program
 * @author juilipanse-kanade
 *
 */
public class SampleDataCreator {

	List<Item> list = new ArrayList<Item>();

	@SuppressWarnings("unchecked")
	/**
	 * The method which reads data from json file and forms the input for the program
	 * @return
	 */
	public List<Item> getData() {
		ClassLoader classLoader = getClass().getClassLoader();
		JSONParser parser = new JSONParser();

		try {

			InputStream stream = classLoader
					.getResourceAsStream("receipts.csv");
			Reader reader = new InputStreamReader(stream);
			JSONObject jsonObject = (JSONObject) parser.parse(reader);

			JSONArray arrItems = (JSONArray) jsonObject.get("items");
			arrItems.forEach(jsonItem -> {
				Item item = new Item();

				JSONObject jsonObjectTemp = (JSONObject) ((JSONObject) jsonItem)
						.get("item");
				String upc = (String) jsonObjectTemp.get("upc");

				JSONArray arrDesc = (JSONArray) jsonObjectTemp
						.get("descriptions");
				ArrayList listDescriptions = new ArrayList();
				arrDesc.forEach(desc -> {
					listDescriptions.add((String) desc);
				});

				item.setUpc(upc);
				item.setListDescriptions(listDescriptions);

				list.add(item);

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

		return list;
	}

}
