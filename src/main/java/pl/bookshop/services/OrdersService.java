package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.enums.OrderStatus;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.mvc.objects.OrderElements;

public interface OrdersService {
	public List<Order> findAll();
	public List<Order> findForUser(Long id);
	public Order findOne(Long id);
	public OrderElements findItems(Long id);
	public void create(OrderData orderData);
	public void changeStatus(Long id, OrderStatus orderStatus);
	public void markAsPaid(Long id);
	public void delete(Long id);
}
