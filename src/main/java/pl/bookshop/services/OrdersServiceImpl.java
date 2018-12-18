package pl.bookshop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.repositories.jpa.OrdersRepository;
import pl.bookshop.repositories.mongo.OrderItemsRepository;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderItemsRepository orderItemsRepository;

	@Override
	public List<Order> findAll() {
		return ordersRepository.findAll();
		
	}

	@Override
	public Order findOne(Long id) {
		return ordersRepository.findById(id).orElse(null);
	}

	@Override
	public List<OrderItem> findItems(Long id) {
		return orderItemsRepository.findByOrderId(id);
	}

	@Override
	public void create(OrderData orderData) {
		Order orderToSave = orderData.getOrder();
		Order savedOrder = ordersRepository.save(orderToSave);
		
		List<OrderItem> orderItems = orderData.getOrderItems();
		orderItems
				.forEach(o -> o.setOrderId(savedOrder.getId()));
		orderItemsRepository.saveAll(orderItems);
	}

	@Override
	public void delete(Long id) {
		orderItemsRepository.deleteByOrderId(id);
		ordersRepository.deleteById(id);
	}
}
