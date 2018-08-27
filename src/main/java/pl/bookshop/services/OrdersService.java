package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.jpa.Order;

public interface OrdersService {
	public List<Order> findAll();
	public Order findOne(Long id);
	public void create(Order Category);
	public Order update(Long id, Order Category);
	public void delete(Long id);
}
