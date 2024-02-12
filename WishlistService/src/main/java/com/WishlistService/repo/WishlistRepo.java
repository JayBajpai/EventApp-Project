package com.WishlistService.repo;

import com.WishlistService.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WishlistRepo extends JpaRepository<Wishlist, Long> {
    boolean existsByEventId(Long id);
}
