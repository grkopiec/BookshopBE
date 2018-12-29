package pl.bookshop.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.jpa.Product;
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.mvc.objects.OrderElements;
import pl.bookshop.repositories.jpa.OrdersRepository;
import pl.bookshop.repositories.jpa.ProductsRepository;
import pl.bookshop.repositories.mongo.OrderItemsRepository;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private ProductsRepository productsRepository;
	@Autowired
	private OrdersRepository ordersRepository;
	@Autowired
	private OrderItemsRepository orderItemsRepository;

	@Override
	public List<Order> findAll() {
		return ordersRepository.findAll();
	}

	@Override
	public List<Order> findForUser(Long id) {
		return ordersRepository.findByUserId(id);
	}

	@Override
	public Order findOne(Long id) {
		return ordersRepository.findById(id).orElse(null);
	}

	@Override
	public OrderElements findItems(Long id) {
		List<OrderItem> orderItems = orderItemsRepository.findByOrderId(id);
		List<Long> productIds = orderItems.stream()
				.map(OrderItem::getProductId)
				.collect(Collectors.toList());
		List<Product> products = productsRepository.findByIdIn(productIds);
		
		OrderElements orderElements = new OrderElements();
		orderElements.setOrderItems(orderItems);
		orderElements.setProducts(products);
		return orderElements;
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
