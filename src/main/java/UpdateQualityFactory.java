public class UpdateQualityFactory implements IUpdateQualityFactory {

	public IUpdateQuality createUpdateQuality(String productName) {

		if (productName == null) {
			throw new RuntimeException("productName is not provided");
		}

		IUpdateQuality updateQuality = null;
		if (productName.equals("Aged Brie")) {
			updateQuality = new AgedBrieQuality();

		} else if (productName.startsWith("Sulfuras")) {
			updateQuality = new SulfarasQuality();

		} else if (productName.startsWith("Backstage")) {
			updateQuality = new BackstageQuality();

		} else if (productName.startsWith("Conjured")) {
			updateQuality = new ConjuredQuality();

		} else {
			updateQuality = new StandardUpdateQuality();
		}

		return updateQuality;
	}
}

class StandardUpdateQuality implements IUpdateQuality {

	public final static int QUALITY_DECREASE = 1;
	
	@Override
	public void updateQuality(Item item) {
		item.setSellIn(item.getSellIn() - 1);
		int decrease = (item.getSellIn() < 0) ? 2*QUALITY_DECREASE : QUALITY_DECREASE;
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
			throw new IllegalStateException(
					"Sulfaras should have never to be sold");
		}
		if (item.getQuality() != 80) {
			throw new IllegalStateException(
					"Sulfaras should have its quality equal to 80, it never alters");
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
			} else if (item.getSellIn() < 10) {
				item.setQuality(item.getQuality() + 2);
			} else {
				item.setQuality(item.getQuality() + 1);
			}
		}
	}
}

class ConjuredQuality implements IUpdateQuality {

	public final static int QUALITY_DECREASE = 2*StandardUpdateQuality.QUALITY_DECREASE;
	
	@Override
	public void updateQuality(Item item) {
		item.setSellIn(item.getSellIn() - 1);

		int decrease = (item.getSellIn() < 0) ? 2*QUALITY_DECREASE : QUALITY_DECREASE; // Quality is decreased twice as normal
		int updatedQuality = item.getQuality() - decrease;
		item.setQuality(updatedQuality > 0 ? updatedQuality : 0);
	}
}