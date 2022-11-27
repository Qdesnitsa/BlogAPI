package com.springboot.blog.repository;

import com.springboot.blog.entity.DonationLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationLikeRepository extends JpaRepository<DonationLike, Long> {
}
