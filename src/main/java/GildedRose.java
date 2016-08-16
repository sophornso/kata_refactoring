import java.util.ArrayList;
import java.util.List;


public class GildedRose {

	private List<Item> items;
	
	private IUpdateQualityFactory updateQualityFactory = new UpdateQualityFactory();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        System.out.println("OMGHAI!");
        
        List<Item> items = new ArrayList<Item>();
        items.add(new Item("+5 Dexterity Vest", 10, 20));
        items.add(new Item("Aged Brie", 2, 0));
        items.add(new Item("Elixir of the Mongoose", 5, 7));
        items.add(new Item("Sulfuras, Hand of Ragnaros", 0, 80));
        items.add(new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20));
        items.add(new Item("Conjured Mana Cake", 3, 6));

        GildedRose gildedRose = new GildedRose(items);
        gildedRose.updateQuality();
}


	public GildedRose(List<Item> items) {
		this.items = items;
	}
	
	 public void updateQuality() {
	     for (Item item : items) {
	    	 IUpdateQuality updateQuality = updateQualityFactory.createUpdateQuality(item.name);
	    	 updateQuality.updateQuality(item);
	     }
	        
	 }


	public List<Item> getItems() {
		return items;
	}

}