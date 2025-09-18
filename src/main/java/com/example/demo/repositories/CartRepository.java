package com.example.demo.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.CartItem;

import jakarta.transaction.Transactional;

@Repository
public interface CartRepository extends JpaRepository<CartItem, Integer> {
	// we are using two date in parameter so spring can't understand this so we only have to do this manually (custom Query)
	@Query("SELECT c FROM CartItem c WHERE c.user.userId= :userId  AND c.product.productId= :productId")
	Optional<CartItem> findByUserAndProduct(int userId, int productId); 
	
	@Query("SELECT COALESCE(SUM(c.quantity ), 0) FROM CartItem c WHERE c.user.userId = :userId")
	int getTotalCount(int userId);
		
	@Query("SELECT c FROM CartItem c JOIN FETCH c.product p LEFT JOIN FETCH ProductImage pi ON p.productId=pi.product.productId WHERE c.user.userId = :userId")
	List<CartItem> findCartItemsWithProductDetails(int userId);

	
	@Query("UPDATE CartItem c set c.quantity= :quantity WHERE c.id= :cartItemId")
	void updateCartItemQuantity(int cartItemId, int quantity);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM  CartItem c WHERE c.user.userId= :userId AND c.product.productId = :productId")
	void deleteCartItem(int userId, int productId);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM  CartItem c WHERE c.user.userId= :userId")
	void deleteAllCartItemsByUserId(int userId);
}
