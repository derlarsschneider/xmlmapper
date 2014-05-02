package de.consutor.xmlmapper.xml.shiporder;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRegistry;

import de.consutor.xmlmapper.xml.shiporder.Shiporder.Item;

@XmlRegistry
public class ShiporderFactory extends ObjectFactory {
	public Item createShiporderItem(String title, String note,
			BigInteger quantity, BigDecimal price) {
		Item item = createShiporderItem();
		item.setNote(note);
		item.setPrice(price);
		item.setQuantity(quantity);
		item.setTitle(title);
		return item;
	}

}
