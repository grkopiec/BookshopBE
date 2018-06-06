package pl.bookshop.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.bookshop.components.UserUtils;
import pl.bookshop.domains.jpa.User;
import pl.bookshop.domains.mongo.UserDetails;
import pl.bookshop.mvc.objects.UserData;
import pl.bookshop.repositories.jpa.UsersRepository;
import pl.bookshop.repositories.mongo.UsersDetailsRepository;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {
	@Autowired
	private UsersRepository usersRepository;
	@Autowired
	private UsersDetailsRepository usersDetailsRepository;
	@Autowired
	private UserUtils userUtils;

	@Override
	public List<UserData> findAll() {
		List<User> users = usersRepository.findAll();
		List<Long> usersIds = users.stream().map(User::getId).collect(Collectors.toList());
		List<UserDetails> usersDetails = usersDetailsRepository.findByUserIdIn(usersIds);
		
		List<UserData> usersData = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			UserData userData = new UserData();
			userData.setUser(users.get(i));
			userData.setUserDetails(usersDetails.get(i));
			usersData.add(userData);
		}
		
		return usersData;
	}

	@Override
	public UserDetails findOne(Long id) {
		return usersDetailsRepository.findByUserId(id).orElse(null);
	}

	@Override
	public User findByUsername(String name) {
		return usersRepository.findByUsername(name);
	}

	@Override
	public Boolean isExist(UserData userData) {
		String username = userData.getUser().getUsername();
		if (usersRepository.findByUsername(username) == null) {
			return false;
		}
		return true;
	}

	@Override
	public void create(UserData userData) {
		String password = userData.getUser().getPassword();
		String encodedPassword = userUtils.encodePassword(password);
		userData.getUser().setPassword(encodedPassword);
		User user = userData.getUser();
		user = usersRepository.save(user);
		
		userData.getUserDetails().setUserId(user.getId());
		UserDetails userDetails = userData.getUserDetails();
		usersDetailsRepository.save(userDetails);
	}

	@Override
	public UserDetails update(Long id, UserDetails userDetails) {
		Optional<UserDetails> updatingUserDetails = usersDetailsRepository.findByUserId(id);
		
		return updatingUserDetails
				.map(o -> {
					userDetails.setId(o.getId());
					userDetails.setUserId(id);
					return usersDetailsRepository.save(userDetails);
				})
				.orElse(null);
	}

	@Override
	public void changePassword(Long id, String newPassword) {
		String encodedPassword = userUtils.encodePassword(newPassword);
		usersRepository.setPassword(id, encodedPassword);
	}

	@Override
	public void delete(Long id) {
		usersRepository.deleteById(id);
		usersDetailsRepository.deleteByUserId(id);
	}
}
