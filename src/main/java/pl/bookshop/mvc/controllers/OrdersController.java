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
import pl.bookshop.mvc.objects.ChangeStatus;
import pl.bookshop.mvc.objects.OrderData;
import pl.bookshop.mvc.objects.OrderElements;
import pl.bookshop.services.OrdersService;
//TODO check if proper user call his own order
//TODO references in table can be NOT NULL
//TODO reference from order to user can not be null
//TODO should be used DAO objects
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
	@RequestMapping(path = "/user/{id}")
	public ResponseEntity<List<Order>> findForUser(@PathVariable Long id) {
		List<Order> orders = ordersService.findForUser(id);

		if (orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(orders, HttpStatus.OK);
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
	@RequestMapping(path = "/items/{id}")
	public ResponseEntity<OrderElements> findItems(@PathVariable Long id) {
		OrderElements orderElements = ordersService.findItems(id);

		if (orderElements.getOrderItems().isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(orderElements, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody @Valid OrderData orderData) {
		ordersService.create(orderData);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/change-status/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Void> changeStatus(@PathVariable Long id, @RequestBody @Valid ChangeStatus changeStatus) {
		ordersService.changeStatus(id, changeStatus.getOrderStatus());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/mark-as-paid/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Void> markAsPaid(@PathVariable Long id) {
		ordersService.markAsPaid(id);
		return new ResponseEntity<>(HttpStatus.OK);
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
