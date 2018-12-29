package pl.bookshop.repositories.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pl.bookshop.domains.jpa.Order;
import pl.bookshop.enums.OrderStatus;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {
	public List<Order> findByUserId(Long id);
	@Modifying
	@Query("UPDATE Order o SET o.status = ?2 WHERE o.id = ?1")
	public void setStatus(Long id, OrderStatus status);
}
