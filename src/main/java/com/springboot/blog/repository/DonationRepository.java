package com.springboot.blog.repository;

import com.springboot.blog.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    Optional<Donation> findByDonationLikeId(Long donationLikeId);
}
