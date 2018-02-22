package pl.bookshop.repositoriesmongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domainsmongo.UserDetails;

@Repository
public interface UsersDetailsRepository extends MongoRepository<UserDetails, String> {
	public List<UserDetails> findByUserIdIn(List<Long> usersIds);
}
