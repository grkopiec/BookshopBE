package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.User;
import pl.bookshop.mvc.controllers.objects.UserData;

public interface UsersService {
	public List<UserData> findAll();
	public User findOne(Long id);
	public User findByUsername(String name);
	
	/**
	 * This method extracts username from received object ant try find it in database
	 * 
	 * @param product object that contains name which will be compare with existing
	 * @return {@code true} if name exists, {@code false} in other case
	 */
	public Boolean isExist(User User);
	public void create(User User);
	public User update(Long id, User User);
	public void delete(Long id);
}
