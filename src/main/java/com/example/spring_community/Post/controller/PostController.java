package com.example.spring_community.Post.controller;

import com.example.spring_community.Auth.dto.CustomUserDetails;
import com.example.spring_community.Exception.dto.DataResponseDto;
import com.example.spring_community.Exception.dto.ResponseDto;
import com.example.spring_community.Post.dto.*;
import com.example.spring_community.Post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name="Post API", description = "Post 리소스에 관한 API 입니다.")
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    @Operation(summary = "게시글 목록 조회", description = "page와 size 쿼리 파라미터를 통해 해당하는 게시글들의 목록을 조회합니다.")
    public ResponseEntity<DataResponseDto<Page<PostItemDto>>> loadPostList(Pageable pageable) {
        Page<PostItemDto> postItems = postService.readPostPage(pageable);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "READ_POSTPAGE_SUCCESS", "게시글 목록 조회에 성공했습니다.", postItems));
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 조회", description = "postId에 해당하는 게시글을 조회합니다.")
    public ResponseEntity<DataResponseDto<DetailPostDto>> loadPost(@PathVariable Long postId) {
        DetailPostDto post = postService.readPost(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "READ_POST_SUCCESS", "게시글 조회에 성공했습니다.", post));
    }

    @PostMapping
    @Operation(summary = "게시글 생성", description = "새로운 게시글을 생성합니다.")
    public ResponseEntity<DataResponseDto<NewPostDto>> createPost(@RequestBody NewPostDto newPostDto, @AuthenticationPrincipal CustomUserDetails principal) {
        NewPostDto newPost = postService.createPost(principal.getUserId(), newPostDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponseDto.of(HttpStatus.CREATED, "CREATE_POST_SUCCESS", "성공적으로 게시글을 업로드했습니다.", newPost));
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정", description = "postId에 해당하는 게시글을 수정합니다.")
    public ResponseEntity<DataResponseDto<NewPostDto>> updatePost(@PathVariable long postId, @RequestBody UpdatePostDto updatePostDto, @AuthenticationPrincipal CustomUserDetails principal) {
        NewPostDto updatedPost = postService.updatePost(principal.getUserId(), postId, updatePostDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "UPDATE_POST_SUCCESS", "성공적으로 게시글을 수정했습니다.", updatedPost));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제", description = "postId에 해당하는 게시글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable long postId, @AuthenticationPrincipal CustomUserDetails principal) {
        postService.deletePost(principal.getUserId(), postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, "DELETE_POST_SUCCESS", "성공적으로 게시글을 삭제했습니다."));
    }

}
