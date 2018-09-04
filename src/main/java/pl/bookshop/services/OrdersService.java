package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.mvc.objects.OrderData;

public interface OrdersService {
	public List<Order> findAll();
	public Order findOne(Long id);
	public List<OrderItem> findItems(Long id);
	public void create(OrderData orderData);
	public void delete(Long id);
}
