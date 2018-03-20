package pl.bookshop.mvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.bookshop.domains.jpa.Category;
import pl.bookshop.services.CategoriesService;

@RestController
@RequestMapping(path = "/categories")
public class CategoriesController {
	@Autowired
	private CategoriesService categoriesService;
	
	@RequestMapping
	public ResponseEntity<List<Category>> findAll() {
		List<Category> categories = categoriesService.findAll();
		
		if (categories.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
	}
	
	@RequestMapping(path = "/{id}")
	public ResponseEntity<Category> findOne(@PathVariable Long id) {
		Category category = categoriesService.findOne(id);
		
		if (category == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> create(@RequestBody Category category) {
		if (categoriesService.isExist(category)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		categoriesService.create(category);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
		Category updatingCategory = categoriesService.findOne(id);
		
		if (updatingCategory == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		if (category.getName().equals(updatingCategory.getName()) == false) {
			if (categoriesService.isExist(category)) {
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		}
		
		Category updatedCategory = categoriesService.update(id, category);
		return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		Category category = categoriesService.findOne(id);
		
		if (category == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		categoriesService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
