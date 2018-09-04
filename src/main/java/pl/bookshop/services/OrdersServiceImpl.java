package pl.bookshop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.domains.mongo.OrderItem;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.repositories.jpa.OrdersRepository;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrdersRepository ordersRepository;

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
		// TODO find order items and return
		return null;
	}

	@Override
	public void create(OrderData orderData) {
		//TODO save order data
	}

	@Override
	public void delete(Long id) {
		ordersRepository.deleteById(id);
	}
}
