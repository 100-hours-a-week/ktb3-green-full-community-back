package com.example.spring_community.Pick.controller;

import com.example.spring_community.Auth.dto.CustomUserDetails;
import com.example.spring_community.Exception.dto.DataResponseDto;
import com.example.spring_community.Pick.dto.PickDto;
import com.example.spring_community.Pick.service.PickService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Pick API", description = "Pick 리소스에 관한 API입니다.")
@RequestMapping("/posts/{postId}/picks")
public class PickController {

    private final PickService pickService;

    public PickController(PickService pickService) {
        this.pickService = pickService;
    }

    @GetMapping
    @Operation(summary = "투표 여부 조회", description = "postId에 해당하는 게시글에 대한 사용자의 투표 여부를 조회합니다.")
    public ResponseEntity<DataResponseDto<PickDto>> isLiked(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails principal) {
        PickDto pick = pickService.getPickNumber(postId, principal.getUserId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "GET_PICKS_SUCCESS", "투표 여부를 성공적으로 조회했습니다.", pick));
    }

    @PostMapping
    @Operation(summary = "투표 생성", description = "postId에 해당하는 게시글에 대한 사용자의 투표를 반영합니다.")
    public ResponseEntity<DataResponseDto<PickDto>> addLikes(@PathVariable Long postId, @RequestBody PickDto pickDto, @AuthenticationPrincipal CustomUserDetails principal) {
        PickDto pick = pickService.addPick(postId, principal.getUserId(), pickDto.getPickNumber());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(DataResponseDto.of(HttpStatus.CREATED, "POST_PICK_SUCCESS", "성공적으로 투표가 반영되었습니다.", pick));
    }

    @DeleteMapping
    @Operation(summary = "투표 삭제", description = "postId에 해당하는 게시글에 대한 사용자의 투표 여부를 확인한 후 존재한다면 해당 투표를 삭제합니다.")
    public ResponseEntity<DataResponseDto<PickDto>> deleteLikes(@PathVariable Long postId,@RequestBody PickDto pickDto, @AuthenticationPrincipal CustomUserDetails principal) {
        PickDto pick = pickService.deletePick(postId, principal.getUserId(), pickDto.getPickNumber());
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDto.of(HttpStatus.OK, "DELETE_PICK_SUCCESS", "성공적으로 투표를 취소했습니다.", pickDto));
    }

}
