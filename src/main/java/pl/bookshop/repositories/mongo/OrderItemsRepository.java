package pl.bookshop.repositories.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domains.mongo.OrderItem;

@Repository
public interface OrderItemsRepository extends MongoRepository<OrderItem, String> {
	public List<OrderItem> findByOrderId(Long orderId);
	public void deleteByOrderId(Long OrderId);
}
