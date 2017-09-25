package pl.bookshop.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.domains.User;
import pl.bookshop.repositories.UsersRepository;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;

	@Override
	public List<User> findAll() {
		return usersRepository.findAll();
	}

	@Override
	public User findOne(Long id) {
		return usersRepository.findOne(id);
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
		usersRepository.save(user);
	}

	@Override
	public User update(Long id, User user) {
		User updatingUser = usersRepository.findOne(id);
		
		if (updatingUser == null) {
			return null;
		}
		user.setId(id);
		return usersRepository.save(user);
	}

	@Override
	public void delete(Long id) {
		usersRepository.delete(id);
	}
}
