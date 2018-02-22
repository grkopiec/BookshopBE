package pl.bookshop.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.domains.User;
import pl.bookshop.mvc.controllers.objects.UserData;
import pl.bookshop.services.UsersService;

@RestController
@RequestMapping(path = "/users")
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@RequestMapping
	public ResponseEntity<List<UserData>> findAll() {
		List<UserData> users = usersService.findAll();
		
		if (users.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserData>>(users, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}")
	public ResponseEntity<User> findOne(@PathVariable Long id) {
		User user = usersService.findOne(id);
		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody User user) {
		if (usersService.isExist(user) == true) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		usersService.create(user);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
		if (usersService.isExist(user) == true) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		User updatedUser = usersService.update(id, user);
		
		if (updatedUser == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		User user = usersService.findOne(id);
		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		usersService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
