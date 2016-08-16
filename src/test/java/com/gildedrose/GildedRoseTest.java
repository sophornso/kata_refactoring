package com.gildedrose;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hamcrest.core.StringContains;
import org.junit.Test;

public class GildedRoseTest {

	/**
	 * At the end of each day our system lowers both values for every item
	 */
	@Test
	public void decreaseSellinAndQualityEachDay() {
		List<Item> items = Arrays.asList(new Item("foo", 10, 20), new Item("fii", 5, 10));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item foo = app.getItems().get(0);
		assertEquals("foo", foo.name);
		assertEquals(9, foo.getSellIn());
		assertEquals(19, foo.getQuality());

		Item fii = app.getItems().get(1);
		assertEquals("fii", fii.name);
		assertEquals(4, fii.getSellIn());
		assertEquals(9, fii.getQuality());
	}

	/**
	 * Once the sell by date has passed, Quality degrades twice as fast
	 */
	@Test
	public void onceSellDatePassedQualityDegradeTwice() {
		List<Item> items = Arrays.asList(new Item("foo", 10, 20), new Item("sell date passed product", 0, 10));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item foo = app.getItems().get(0);
		assertEquals("foo", foo.name);
		assertEquals(9, foo.getSellIn());
		assertEquals(19, foo.getQuality());

		Item sellDatePassedProduct = app.getItems().get(1);
		assertEquals("sell date passed product", sellDatePassedProduct.name);
		assertEquals(-1, sellDatePassedProduct.getSellIn());
		assertEquals(8, sellDatePassedProduct.getQuality());
	}

	/**
	 * The Quality of an item is never negative
	 */
	@Test
	public void qualityIsNeverNegative() {
		List<Item> items = Arrays.asList(new Item("foo", 10, 0), new Item("sell date passed product", 0, 0));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item foo = app.getItems().get(0);
		assertEquals("foo", foo.name);
		assertEquals(9, foo.getSellIn());
		assertEquals(0, foo.getQuality());

		Item sellDatePassedProduct = app.getItems().get(1);
		assertEquals("sell date passed product", sellDatePassedProduct.name);
		assertEquals(-1, sellDatePassedProduct.getSellIn());
		assertEquals(0, sellDatePassedProduct.getQuality());
	}

	/**
	 * "Aged Brie" actually increases in Quality the older it gets
	 */
	@Test
	public void agedBrieIncreaseQualityTheOlderItGets() {
		List<Item> items = Arrays.asList(new Item("Aged Brie", 10, 5));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item agedBrie = app.getItems().get(0);
		assertEquals("Aged Brie", agedBrie.name);
		assertEquals(9, agedBrie.getSellIn());
		assertEquals(6, agedBrie.getQuality());
	}

	/**
	 * The Quality of an item is never more than 50
	 */
	@Test
	public void qualityIsNeverMoreThan50() {
		List<Item> items = Arrays.asList(new Item("Aged Brie", 10, 50));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item agedBrie = app.getItems().get(0);
		assertEquals("Aged Brie", agedBrie.name);
		assertEquals(9, agedBrie.getSellIn());
		assertEquals(50, agedBrie.getQuality());
	}

	/**
	 * "Sulfuras", being a legendary item, never has to be sold or decreases in
	 * Quality
	 */
	@Test
	public void sulfurasLegendaryItemHasNeverSoldOrDecreaseInQuality() {
		List<Item> items = Arrays.asList(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item sulfuras = app.getItems().get(0);
		assertTrue(new StringContains("Sulfuras").matches(sulfuras.name));
		assertEquals(0, sulfuras.getSellIn());
		assertEquals(80, sulfuras.getQuality());
	}

	/**
	 * "Backstage passes", like aged brie, increases in Quality as it's SellIn
	 * value approaches; Quality increases by 2 when there are 10 days or less
	 * and by 3 when there are 5 days or less but Quality drops to 0 after the
	 * concert
	 */
	@Test
	public void backstageLeft10DaysQualityDecreaseBy2() {
		List<Item> items = Arrays.asList(new Item("Backstage passes to a TAFKAL80ETC concert", 10, 40));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item backstage = app.getItems().get(0);
		assertEquals("Backstage passes to a TAFKAL80ETC concert", backstage.name);
		assertEquals(9, backstage.getSellIn());
		assertEquals(42, backstage.getQuality());
	}

	/**
	 * "Backstage passes", like aged brie, increases in Quality as it's SellIn
	 * value approaches; Quality increases by 3 when there are 5 days or less
	 */
	@Test
	public void backstageLeft5DaysQualityDecreaseBy3() {
		List<Item> items = Arrays.asList(new Item("Backstage passes to a TAFKAL80ETC concert", 5, 40));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item backstage = app.getItems().get(0);
		assertEquals("Backstage passes to a TAFKAL80ETC concert", backstage.name);
		assertEquals(4, backstage.getSellIn());
		assertEquals(43, backstage.getQuality());
	}

	/**
	 * "Backstage passes", like aged brie, increases in Quality as it's SellIn
	 * value approaches; Quality drops to 0 after the concert
	 */
	@Test
	public void backstageWithNoMoreDaysDropItsQualityTo0() {
		List<Item> items = Arrays.asList(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 40));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item backstage = app.getItems().get(0);
		assertEquals("Backstage passes to a TAFKAL80ETC concert", backstage.name);
		assertEquals(-1, backstage.getSellIn());
		assertEquals(0, backstage.getQuality());
	}

	/**
	 * New fonctionnalty: "Conjured" items degrade in Quality twice as fast as
	 * normal items
	 */
	@Test
	public void conjuredItemDecreaseItsQualityTwiceasNormalItems() {
		List<Item> items = Arrays.asList(new Item("Conjured Mana Cake", 5, 30));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
		Item conjured = app.getItems().get(0);
		assertEquals("Conjured Mana Cake", conjured.name);
		assertEquals(4, conjured.getSellIn());
		assertEquals(28, conjured.getQuality());
	}

	@Test
	public void testSetOfProductsForUpdateQuality() {

		List<Item> items = new ArrayList<Item>();
		items.add(new Item("+5 Dexterity Vest", 10, 20));
		items.add(new Item("Aged Brie", 2, 0));
		items.add(new Item("Elixir of the Mongoose", 5, 7));
		items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
		items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
		items.add(new Item("Conjured Mana Cake", 3, 6));

		GildedRose app = new GildedRose(items);
		app.updateQuality();

		Item item = app.getItems().get(0);
		assertEquals("+5 Dexterity Vest", item.name);
		assertEquals(9, item.getSellIn());
		assertEquals(19, item.getQuality());

		item = app.getItems().get(1);
		assertEquals("Aged Brie", item.name);
		assertEquals(1, item.getSellIn());
		assertEquals(1, item.getQuality());

		item = app.getItems().get(2);
		assertEquals("Elixir of the Mongoose", item.name);
		assertEquals(4, item.getSellIn());
		assertEquals(6, item.getQuality());

		item = app.getItems().get(3);
		assertEquals("Sulfuras, Hand of Ragnaros", item.name);
		assertEquals(0, item.getSellIn());
		assertEquals(80, item.getQuality());

		item = app.getItems().get(4);
		assertEquals("Backstage passes to a TAFKAL80ETC concert", item.name);
		assertEquals(14, item.getSellIn());
		assertEquals(21, item.getQuality());

		item = app.getItems().get(5);
		assertEquals("Conjured Mana Cake", item.name);
		assertEquals(2, item.getSellIn());
		assertEquals(4, item.getQuality()); // Decrease twice as normal items
	}
	
	/**
	 * Expected IllegalArgumentException when item name is null
	 */
	@Test(expected=IllegalArgumentException.class)
	public void excpetionIfProductNameIsNull() {
		List<Item> items = Arrays.asList(new Item(null, 5, 30));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
	}

	
	/**
	 * Expected IllegalStateException when Sulfuras item has its sellIn different to 0
	 */
	@Test(expected=IllegalStateException.class)
	public void SulfurasExceptionWhenSellingDifferentTo0() {
		List<Item> items = Arrays.asList(new Item("Sulfuras, Hand of Ragnaros", 11, 22));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
	}
	
	/**
	 * Expected IllegalStateException when Sulfuras item has its quality different to 80
	 */
	@Test(expected=IllegalStateException.class)
	public void SulfurasExceptionWhenQualityDifferentTo80() {
		List<Item> items = Arrays.asList(new Item("Sulfuras, Hand of Ragnaros", 0, 22));
		GildedRose app = new GildedRose(items);
		app.updateQuality();
	}

}
