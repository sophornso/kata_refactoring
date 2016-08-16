
enum ProductType implements IUpdateQuality {
	
	STANDARD,
	AGE_BRIE{
		@Override
		public void updateQuality(Item item) {
			item.setSellIn(item.getSellIn() - 1);
			if (item.getQuality() < 50) {
				item.setQuality(item.getQuality() + 1);
			}
		}
	},
	SULFURAS{
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
	},
	BACKSTAGE{
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
	},
	CONJURED{
		
		@Override
		public void updateQuality(Item item) {
			item.setSellIn(item.getSellIn() - 1);

			int decrease = (item.getSellIn() < 0) ? 2*getQualityDecrementUnit() : getQualityDecrementUnit(); // Quality is decreased twice as normal
			int updatedQuality = item.getQuality() - decrease;
			item.setQuality(updatedQuality > 0 ? updatedQuality : 0);
		}
		
		@Override
		public int getQualityDecrementUnit() {
			return 2*STANDARD.getQualityDecrementUnit();
		}
	};
	
	
	
	public int getQualityDecrementUnit() {
		return 1;
	}
	
	@Override
	public void updateQuality(Item item) {
		item.setSellIn(item.getSellIn() - 1);
		int decrease = (item.getSellIn() < 0) ? 2*getQualityDecrementUnit() : getQualityDecrementUnit();
		int newQuality = item.getQuality() - decrease;
		item.setQuality(newQuality > 0 ? newQuality : 0);
	}
	
	
	
	public static ProductType instanceOf(String productName) {
		
		ProductType productType = STANDARD;
		
		if (productName.equals("Aged Brie")) {
			productType = AGE_BRIE;

		} else if (productName.startsWith("Sulfuras")) {
			productType = SULFURAS;

		} else if (productName.startsWith("Backstage")) {
			productType = BACKSTAGE;

		} else if (productName.startsWith("Conjured")) {
			productType = CONJURED;
		} 

		return productType;
		
	}
}
