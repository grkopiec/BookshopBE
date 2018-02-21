package pl.bookshop.repositoriesmongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domainsmongo.UserDetails;

@Repository
public interface UsersDetailsRepository extends MongoRepository<UserDetails, String> {
}
