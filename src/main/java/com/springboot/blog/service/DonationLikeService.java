package com.springboot.blog.service;

import com.springboot.blog.dto.DonationLikeDto;

public interface DonationLikeService {
    DonationLikeDto createDonationLike(Long userId, Long postId);
}
