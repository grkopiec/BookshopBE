package pl.bookshop.repositories.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.bookshop.domains.jpa.Order;

@Repository
public interface OrdersRepository extends JpaRepository<Order, Long> {}
