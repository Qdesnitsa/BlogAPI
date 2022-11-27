package com.springboot.blog.controller;

import com.springboot.blog.dto.DonationLikeDto;
import com.springboot.blog.service.DonationLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "CRUD REST APIs for DonationLike")
@RestController
@RequestMapping("/api/v1/likes")
public class DonationLikeController {
    private DonationLikeService donationLikeService;

    public DonationLikeController(DonationLikeService donationLikeService) {
        this.donationLikeService = donationLikeService;
    }

    @ApiOperation(value = "Create DonationLike REST API")
    @GetMapping("/posts/{postId}/users/{userId}")
    public ResponseEntity<DonationLikeDto> createDonationLike(@PathVariable Long postId,
                                                              @PathVariable Long userId) {
        return new ResponseEntity<>(donationLikeService.createDonationLike(userId, postId), HttpStatus.CREATED);
    }
}
