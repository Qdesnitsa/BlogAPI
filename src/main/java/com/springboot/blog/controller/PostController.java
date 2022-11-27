package com.springboot.blog.controller;

import com.springboot.blog.dto.PostDto;
import com.springboot.blog.dto.PostDtoVersion2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.util.AppConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(value = "CRUD Rest APIs for Post resources")
@RestController
@RequestMapping()
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiOperation(value = "Create Post REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/api/v1/posts")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get All Posts REST API")
    @GetMapping("/api/v1/posts")
    public PostResponse getAllPosts(
            @Parameter(in = ParameterIn.QUERY, description = "Number of page to display content")
            @RequestParam(value = "pageNo", defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,

            @Parameter(in = ParameterIn.QUERY, description = "Number of items to display on 1 page")
            @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,

            @Parameter(in = ParameterIn.QUERY, description = "Values to be sorted by",
                    schema = @Schema(allowableValues = {"id", "title", "description", "content"}))
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,

            @Parameter(in = ParameterIn.QUERY, description = "Direction for sorting",
                    schema = @Schema(allowableValues = {"asc", "desc"}))
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

// Through content negotiation
//    @GetMapping(value = "/api/posts/{id}", produces = "application/vnd.blogapp.v5+json")
//    public ResponseEntity<PostDto> getPostByIdV5(@PathVariable(name = "id") Long postId) {
//        return ResponseEntity.ok(postService.getPostById(postId));
//    }

// Through custom headers
//    @GetMapping(value = "/api/posts/{id}", headers = "X-API-VERSION=4")
//    public ResponseEntity<PostDto> getPostByIdV4(@PathVariable(name = "id") Long postId) {
//        return ResponseEntity.ok(postService.getPostById(postId));
//    }

// Through query parameters
//    @GetMapping(value = "/api/posts/{id}", params = "version=3")
//    public ResponseEntity<PostDto> getPostByIdV3(@PathVariable(name = "id") Long postId) {
//        return ResponseEntity.ok(postService.getPostById(postId));
//    }

    // Through URI path
    @ApiOperation(value = "Get Post By Id REST API")
    @GetMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> getPostByIdV1(@PathVariable(name = "id") Long postId) {
        return ResponseEntity.ok(postService.getPostById(postId));
    }

    @ApiOperation(value = "Get Post By Id REST API")
    @GetMapping("/api/v2/posts/{id}")
    public ResponseEntity<PostDtoVersion2> getPostByIdV2(@PathVariable(name = "id") Long postId) {
        PostDto postDto = postService.getPostById(postId);
        PostDtoVersion2 postDtoV2 = new PostDtoVersion2();
        postDtoV2.setId(postDto.getId());
        postDtoV2.setTitle(postDto.getTitle());
        postDtoV2.setDescription(postDto.getDescription());
        postDtoV2.setContent(postDto.getContent());
        postDtoV2.setComments(postDto.getComments());
        List<String> tags = new ArrayList<>();
        tags.add("Java");
        tags.add("Spring Boot");
        tags.add("AWS");
        postDtoV2.setTags(tags);
        return ResponseEntity.ok(postDtoV2);
    }

    @ApiOperation(value = "Update Post By Id REST API")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PostDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input provided"),
            @ApiResponse(responseCode = "401", description = "Authorization required"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "404", description = "Invalid ID provided")})
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/api/v1/posts/{id}")
    public ResponseEntity<PostDto> updatePost(
            @Parameter(name = "body", description = "Post data to be updated", required = true, schema = @Schema())
            @Valid @RequestBody PostDto postDto,
            @Parameter(in = ParameterIn.PATH, description = "Post id to be updated", required = true, schema = @Schema())
            @PathVariable(name = "id") Long postId) {
        PostDto postResponse = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete Post By Id REST API")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/api/v1/posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long postId) {
        postService.deletePostById(postId);
        return new ResponseEntity<>("Post entity was deleted successfully", HttpStatus.OK);
    }

    @ApiOperation(value = "Find Post By search word REST API")
    @GetMapping("/api/v1/posts/search")
    public ResponseEntity<List<PostDto>> searchPosts(@RequestParam("query") String search) {
        return ResponseEntity.ok(postService.searchPosts(search));
    }
}
