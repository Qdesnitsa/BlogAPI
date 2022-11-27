package com.springboot.blog.service;

import com.springboot.blog.dto.DonationDto;

public interface DonationService {
    DonationDto createDonation(Long donationLikeId);
}
