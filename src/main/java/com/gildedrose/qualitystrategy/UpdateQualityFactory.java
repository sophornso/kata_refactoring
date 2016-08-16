package com.gildedrose.qualitystrategy;

public class UpdateQualityFactory implements IUpdateQualityFactory {

	@Override
	public IUpdateQuality createUpdateQuality(String productName) {

		if (productName == null) {
			throw new IllegalArgumentException("productName is not provided");
		}

		ProductType productType = ProductType.STANDARD;

		if (productName.equals("Aged Brie")) {
			productType = ProductType.AGE_BRIE;

		} else if (productName.startsWith("Sulfuras")) {
			productType = ProductType.SULFURAS;

		} else if (productName.startsWith("Backstage")) {
			productType = ProductType.BACKSTAGE;

		} else if (productName.startsWith("Conjured")) {
			productType = ProductType.CONJURED;
		}

		return productType;
	}

}
