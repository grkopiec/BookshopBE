package pl.bookshop.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/test")
public class ExampleController {
	@Autowired
	private String propertyValue;
	
	@RequestMapping(method = RequestMethod.GET)
	public String test() {
		return propertyValue;
	}
}
