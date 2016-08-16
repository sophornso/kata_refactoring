package com.gildedrose.qualitystrategy;

import com.gildedrose.Item;

enum ProductType implements IUpdateQuality {

	STANDARD,

	AGE_BRIE {
		@Override
		public void updateQuality(Item item) {
			item.setSellIn(item.getSellIn() - 1);
			if (item.getQuality() < 50) {
				item.setQuality(item.getQuality() + 1);
			}
		}
	},

	SULFURAS {
		@Override
		public void updateQuality(Item item) {
			if (item.getSellIn() != 0) {
				throw new IllegalStateException("Sulfaras should have never to be sold");
			}
			if (item.getQuality() != 80) {
				throw new IllegalStateException("Sulfaras should have its quality equal to 80, it never alters");
			}
		}
	},

	BACKSTAGE {
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

	CONJURED {

		@Override
		public void updateQuality(Item item) {
			item.setSellIn(item.getSellIn() - 1);

			// Quality is decreased twice as normal
			int decrease = (item.getSellIn() < 0) ? 2 * getQualityDecrementUnit() : getQualityDecrementUnit();
			int updatedQuality = item.getQuality() - decrease;
			item.setQuality(updatedQuality > 0 ? updatedQuality : 0);
		}

		@Override
		public int getQualityDecrementUnit() {
			return 2 * STANDARD.getQualityDecrementUnit();
		}
	};

	public int getQualityDecrementUnit() {
		return 1;
	}

	@Override
	public void updateQuality(Item item) {
		item.setSellIn(item.getSellIn() - 1);
		int decrease = (item.getSellIn() < 0) ? 2 * getQualityDecrementUnit() : getQualityDecrementUnit();
		int updatedQuality = item.getQuality() - decrease;
		item.setQuality(updatedQuality > 0 ? updatedQuality : 0);
	}

}
