package com.example.spring_community.Comment.controller;

import com.example.spring_community.Auth.dto.CustomUserDetails;
import com.example.spring_community.Comment.dto.CommentDto;
import com.example.spring_community.Comment.dto.NewCommentDto;
import com.example.spring_community.Exception.dto.DataResponseDto;
import com.example.spring_community.Exception.dto.ResponseDto;
import com.example.spring_community.Comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="Comment API", description = "Comment 리소스에 관한 API 입니다.")
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    @Operation(summary = "댓글 목록 조회", description = "postId에 해당하는 게시글에 대한 댓글 목록을 조회합니다.")
    public ResponseEntity<DataResponseDto<List<CommentDto>>> loadCommentList(@PathVariable Long postId) {
        commentService.isValidPost(postId);
        List<CommentDto> commentList = commentService.readCommentList(postId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "READ_COMMNETLIST_SUCCESS", "댓글 목록 조회에 성공했습니다.", commentList));
    }

    @PostMapping
    @Operation(summary = "댓글 생성", description = "postId에 해당하는 게시글에 대한 댓글을 생성합니다.")
    public ResponseEntity<DataResponseDto<CommentDto>> createComment(@PathVariable Long postId, @RequestBody NewCommentDto newCommentDto, @AuthenticationPrincipal CustomUserDetails principal) {
        commentService.isValidPost(postId);
        CommentDto newComment = commentService.createComment(principal.getUserId(), postId, newCommentDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponseDto.of(HttpStatus.CREATED, "CREATE_COMMENT_SUCCESS", "성공적으로 댓글을 업로드했습니다.", newComment));
    }

    @PatchMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "postId에 해당하는 게시글에 대한 commentId의 댓글을 수정합니다.")
    public ResponseEntity<DataResponseDto<CommentDto>> updatePost(@PathVariable long postId,@PathVariable long commentId, @RequestBody NewCommentDto updateCommentDto, @AuthenticationPrincipal CustomUserDetails principal) {
        commentService.isValidPost(postId);
        CommentDto updatedComment = commentService.updateComment(principal.getUserId(), postId, commentId, updateCommentDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "UPDATE_COMMENT_SUCCESS", "성공적으로 댓글을 수정했습니다.", updatedComment));
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "postId에 해당하는 게시글에 대한 commentId의 댓글을 삭제합니다.")
    public ResponseEntity<ResponseDto> deletePost(@PathVariable long postId, @PathVariable long commentId, @AuthenticationPrincipal CustomUserDetails principal) {
        commentService.isValidPost(postId);
        commentService.deleteComment(principal.getUserId(), postId, commentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.of(HttpStatus.OK, "DELETE_COMMENT_SUCCESS", "성공적으로 댓글을 삭제했습니다."));
    }
}
