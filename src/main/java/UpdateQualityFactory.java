
public class UpdateQualityFactory {

	public static IUpdateQuality createUpdateQuality(String productName) {

		IUpdateQuality updateQuality = null;
		if ("Aged Brie".equals(productName)) {
			updateQuality = new AgedBrieQuality();

		} else if ("Sulfuras, Hand of Ragnaros".equals(productName)) {
			updateQuality = new SulfarasQuality();

		} else if ("Backstage passes to a TAFKAL80ETC concert".equals(productName)) {
			updateQuality = new BackstageQuality();

		} else {
			updateQuality = new StandardUpdateQuality();
		}

		return updateQuality;
	}
}

class StandardUpdateQuality implements IUpdateQuality {

	@Override
	public void updateQuality(Item item) {
		item.setSellIn(item.getSellIn() - 1);
		int decrease = (item.getSellIn() < 0) ? 2 : 1;
		int newQuality = item.getQuality() - decrease;
		item.setQuality(newQuality > 0 ? newQuality : 0);
	}

}

class AgedBrieQuality implements IUpdateQuality {

	@Override
	public void updateQuality(Item item) {
		item.setSellIn(item.getSellIn() - 1);

		if (item.getQuality() < 50) {
			item.setQuality(item.getQuality() + 1);
		}
	}
}

class SulfarasQuality implements IUpdateQuality {

	@Override
	public void updateQuality(Item item) {
		if (item.getSellIn() != 0) {
			throw new IllegalStateException("Sulfaras should have never to be sold");
		}
		if (item.getQuality() != 80) {
			throw new IllegalStateException("Sulfaras should have its quality equal to 80, it never alters");
		}
	}
}

class BackstageQuality implements IUpdateQuality {

	@Override
	public void updateQuality(Item item) {
		item.setSellIn(item.getSellIn() - 1);
		
		if (item.getSellIn() < 0) {
			item.setQuality(0);
		} else if (item.getQuality() < 50) {
		    if (item.getSellIn() < 5) {
				item.setQuality(item.getQuality() + 3);
			}  else if (item.getSellIn() < 10) {
				item.setQuality(item.getQuality() + 2);
			} else {
				item.setQuality(item.getQuality() + 1);
			}
		}
	}
}