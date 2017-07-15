package pl.bookshop.criteria;

import pl.bookshop.enums.OrderBy;

public class ProductsCriteria {
	private String category;
	private String name;
	private Double priceFrom;
	private Double priceTo;
	private OrderBy orderBy;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(Double priceFrom) {
		this.priceFrom = priceFrom;
	}

	public Double getPriceTo() {
		return priceTo;
	}

	public void setPriceTo(Double priceTo) {
		this.priceTo = priceTo;
	}

	public OrderBy getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(OrderBy orderBy) {
		this.orderBy = orderBy;
	}
}
