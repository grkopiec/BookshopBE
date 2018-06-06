package pl.bookshop.services;

import java.util.List;

import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.objects.UserData;

public interface UsersService {
	public List<UserData> findAll();
	public UserDetails findOne(Long id);
	public User findByUsername(String name);
	
	/**
	 * This method extracts username from received object ant try find it in database
	 * 
	 * @param product object that contains name which will be compare with existing
	 * @return {@code true} if name exists, {@code false} in other case
	 */
	public Boolean isExist(UserData userData);
	public void create(UserData UserData);
	public UserDetails update(Long id, UserDetails userDetails);
	public void changePassword(Long id, String newPassword);
	public void delete(Long id);
}
