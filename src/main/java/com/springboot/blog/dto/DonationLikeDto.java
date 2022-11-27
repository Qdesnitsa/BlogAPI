package com.springboot.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Api(value = "DonationLike model information")
@Data
public class DonationLikeDto {

    @ApiModelProperty(value = "DonationLike id")
    private Long id;

    @ApiModelProperty(value = "DonationLike user")
    @NotEmpty
    @JsonIgnore
    private User user;

    @ApiModelProperty(value = "DonationLike post")
    @NotEmpty
    @JsonIgnore
    private Post post;
}
