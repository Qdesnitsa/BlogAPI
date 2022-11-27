package com.springboot.blog.service.impl;

import com.springboot.blog.dto.DonationDto;
import com.springboot.blog.entity.Donation;
import com.springboot.blog.entity.DonationLike;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.DonationLikeRepository;
import com.springboot.blog.repository.DonationRepository;
import com.springboot.blog.service.DonationService;
import com.springboot.blog.util.AppConstant;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class DonationServiceImpl implements DonationService {
    private DonationRepository donationRepository;
    private DonationLikeRepository donationLikeRepository;
    private ModelMapper mapper;

    public DonationServiceImpl(DonationRepository donationRepository, DonationLikeRepository donationLikeRepository, ModelMapper mapper) {
        this.donationRepository = donationRepository;
        this.donationLikeRepository = donationLikeRepository;
        this.mapper = mapper;
    }

    @Override
    public DonationDto createDonation(Long donationLikeId) {
        DonationLike donationLike = donationLikeRepository
                .findById(donationLikeId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id",donationLikeId));
        Donation donation = new Donation();
        donation.setDonationLike(donationLike);
        donation.setDonationSum(AppConstant.DEFAULT_DONATION);
        Donation newDonation = donationRepository.save(donation);
        return mapToDto(DonationDto.class, newDonation);
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
