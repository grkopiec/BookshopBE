package pl.bookshop.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.bookshop.domains.jpa.User;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
	@Modifying
	@Query("UPDATE User u SET u.password = ?2 WHERE u.id = ?1")
	public void setPassword(Long id, String password);
}