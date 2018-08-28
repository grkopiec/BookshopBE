package pl.bookshop.mvc.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.services.OrdersService;
//TODO check if proper user call his own order
@RestController
@RequestMapping(path = "/orders")
public class OrdersController {
	@Autowired
	private OrdersService ordersService;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping
	public ResponseEntity<List<Order>> findAll() {
		List<Order> orders = ordersService.findAll();
		
		if (orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(path = "/{id}")
	public ResponseEntity<Order> findOne(@PathVariable Long id) {
		Order order = ordersService.findOne(id);
		
		if (order == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody @Valid Order order) {
		ordersService.create(order);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Order> update(@PathVariable Long id, @RequestBody @Valid Order order) {
		Order updatingOrder = ordersService.findOne(id);
		
		if (updatingOrder == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Order updatedOrder = ordersService.update(id, order);
		return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Order order = ordersService.findOne(id);
		
		if (order == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		ordersService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
