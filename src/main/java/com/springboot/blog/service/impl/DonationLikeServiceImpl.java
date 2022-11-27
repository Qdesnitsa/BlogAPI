package com.springboot.blog.service.impl;

import com.springboot.blog.entity.DonationLike;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.dto.DonationLikeDto;
import com.springboot.blog.repository.DonationLikeRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.DonationLikeService;
import com.springboot.blog.service.DonationService;
import com.springboot.blog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DonationLikeServiceImpl implements DonationLikeService {
    private DonationLikeRepository donationLikeRepository;
    private DonationService donationService;
    private UserRepository userRepository;
    private PostService postService;
    private ModelMapper mapper;

    public DonationLikeServiceImpl(DonationLikeRepository donationLikeRepository, DonationService donationService,
                                   UserRepository userRepository, PostService postService, ModelMapper mapper) {
        this.donationLikeRepository = donationLikeRepository;
        this.donationService = donationService;
        this.userRepository = userRepository;
        this.postService = postService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public DonationLikeDto createDonationLike(Long userId, Long postId) {
        Post post = mapToEntity(Post.class,postService.getPostById(postId));
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        DonationLike donationLike = new DonationLike();
        donationLike.setPost(post);
        donationLike.setUser(user);
        DonationLike newDonationLike = donationLikeRepository.save(donationLike);
        donationService.createDonation(newDonationLike.getId());
        return mapToDto(DonationLikeDto.class, newDonationLike);
    }

    private <T, N> T mapToDto(Class<T> clazz, N donation) {
        T donationDto = mapper.map(donation, clazz);
        return donationDto;
    }

    private <T, N> T mapToEntity(Class<T> clazz, N donationDto) {
        T donation = mapper.map(donationDto, clazz);
        return donation;
    }
}
