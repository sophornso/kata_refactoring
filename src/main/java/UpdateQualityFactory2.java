
public class UpdateQualityFactory2 implements IUpdateQualityFactory {

	@Override
	public IUpdateQuality createUpdateQuality(String productName) {

		if (productName == null) {
			throw new RuntimeException("productName is not provided");
		}

		return ProductType.instanceOf(productName);
	}

}


