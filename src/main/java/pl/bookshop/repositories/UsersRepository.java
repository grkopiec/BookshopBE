package pl.bookshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domains.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}
