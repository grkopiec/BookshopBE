package pl.bookshop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.domains.jpa.Order;
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
	public void create(Order order) {
		ordersRepository.save(order);
		
	}

	@Override
	public Order update(Long id, Order order) {
		order.setId(id);
		return ordersRepository.save(order);
	}

	@Override
	public void delete(Long id) {
		ordersRepository.deleteById(id);
	}
}
