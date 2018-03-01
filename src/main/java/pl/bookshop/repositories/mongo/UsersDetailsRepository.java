package pl.bookshop.repositories.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domainsmongo.UserDetails;

@Repository
public interface UsersDetailsRepository extends MongoRepository<UserDetails, String> {
	public Optional<UserDetails> findByUserId(Long userId);
	public List<UserDetails> findByUserIdIn(List<Long> usersIds);
	public void deleteByUserId(Long userId);
}
