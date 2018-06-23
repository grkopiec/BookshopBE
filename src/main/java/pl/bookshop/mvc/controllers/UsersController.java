package pl.bookshop.mvc.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.objects.NewPassword;
import pl.bookshop.mvc.objects.UserData;
import pl.bookshop.mvc.validation.groups.AdminUser;
import pl.bookshop.mvc.validation.groups.NormalUser;
import pl.bookshop.services.UsersService;

@RestController
@RequestMapping(path = "/users")
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private UserUtils userUtils;
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping
	public ResponseEntity<List<UserData>> findAll() {
		List<UserData> usersData = usersService.findAll();
		
		if (usersData.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<UserData>>(usersData, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(path = "/{id}")
	public ResponseEntity<UserDetails> findOne(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
		if (authenticatedUser.getId() != id) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		UserDetails userDetails = usersService.findOne(id);
		
		if (userDetails == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/admin", method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody @Validated(AdminUser.class) UserData userData) {
		if (usersService.isExist(userData) == true) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
		userUtils.makeAdminUser(userData);
		usersService.create(userData);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserDetails> update(
			@PathVariable Long id, @RequestBody @Validated(NormalUser.class) UserDetails userDetails,
			@AuthenticationPrincipal User authenticatedUser) {
		if (authenticatedUser.getId() != id) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		UserDetails updatedUserDetails = usersService.update(id, userDetails);
		
		if (updatedUserDetails == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedUserDetails, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(path = "/change-password/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<Void> changePassword(
			@PathVariable Long id, @RequestBody @Valid NewPassword newPassword, @AuthenticationPrincipal User authenticatedUser) {
		if (authenticatedUser.getId() != id) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		usersService.changePassword(id, newPassword.getNewPassword());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		UserDetails user = usersService.findOne(id);
		
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		usersService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
