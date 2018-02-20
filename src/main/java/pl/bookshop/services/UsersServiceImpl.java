package pl.bookshop.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.User;
import pl.bookshop.repositories.UsersRepository;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UserUtils userUtils;

	@Override
	public List<User> findAll() {
		return usersRepository.findAll();
	}

	@Override
	public User findOne(Long id) {
		return usersRepository.findById(id).orElse(null);
	}

	@Override
	public User findByUsername(String name) {
		return usersRepository.findByUsername(name);
	}

	@Override
	public Boolean isExist(User user) {
		if (usersRepository.findByUsername(user.getUsername()) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void create(User user) {
		String password = user.getPassword();
		String encodedPassword = userUtils.encodePassword(password);
		user.setPassword(encodedPassword);
		usersRepository.save(user);
	}

	@Override
	public User update(Long id, User user) {
		Optional<User> updatingUser = usersRepository.findById(id);
		
		return updatingUser
				.map(o -> {
					user.setId(id);
					return usersRepository.save(user);
				})
				.orElse(null);
	}

	@Override
	public void delete(Long id) {
		usersRepository.deleteById(id);
	}
}
