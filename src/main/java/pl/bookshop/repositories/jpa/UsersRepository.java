package pl.bookshop.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domains.jpa.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}
