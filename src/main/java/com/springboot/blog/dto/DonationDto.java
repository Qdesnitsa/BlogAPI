package com.springboot.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.blog.entity.DonationLike;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Api(value = "Donation model information")
@Data
public class DonationDto {
    @ApiModelProperty(value = "Donation id")
    private Long id;

    @ApiModelProperty(value = "Corresponding donationLike")
    @NotEmpty
    @JsonIgnore
    private DonationLike donationLike;

    @ApiModelProperty(value = "Donation amount")
    @NotEmpty
    @JsonIgnore
    private BigDecimal donationSum;
}
