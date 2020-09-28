package com.example.proekt_emt.persistance;

import com.example.proekt_emt.model.Product;
import com.example.proekt_emt.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Wishlist findByUserUsername(String username);
}
