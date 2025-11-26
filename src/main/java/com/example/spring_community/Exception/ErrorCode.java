package com.example.spring_community.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_REQUEST", "파라미터 값이 비어있거나 올바르지 않습니다."), //400
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "INVALID_PASSWORD", "비밀번호가 다릅니다."), //400
    INVALID_FILE_FORMAT(HttpStatus.BAD_REQUEST, "INVALID_FILE_FORMAT", "지원하지 않는 파일 형식입니다."), //400
    INVALID_PICK_NUMBER(HttpStatus.BAD_REQUEST, "INVALID_PICK_NUMBER", "투표 선택지 번호가 올바르지 않습니다."), //400

    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED_USER", "비밀번호가 틀렸습니다."), //401
    UNAUTHORIZED_TOKEN(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED_TOKEN", "유효하지 않은 토큰입니다."), //401

    POST_UPDATE_FORBIDDEN_USER(HttpStatus.FORBIDDEN, "POST_UPDATE_FORBIDDEN_USER", "해당 게시글에 대한 수정권한이 없는 사용자입니다."), //403
    POST_DELETE_FORBIDDEN_USER(HttpStatus.FORBIDDEN, "POST_DELETE_FORBIDDEN_USER", "해당 게시글에 대한 삭제권한이 없는 사용자입니다."), //403
    COMMENT_UPDATE_FORBIDDEN_USER(HttpStatus.FORBIDDEN, "COMMNET_UPDATE_FORBIDDEN_USER", "해당 댓글에 대한 수정권한이 없는 사용자입니다."), //403
    COMMENT_DELETE_FORBIDDEN_USER(HttpStatus.FORBIDDEN, "COMMENT_DELETE_FORBIDDEN_USER", "해당 댓글에 대한 삭제권한이 없는 사용자입니다."), //403

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "회원정보를 찾을 수 없습니다."), //404
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "요청하신 게시글을 찾을 수 없습니다."), //404
    POST_META_NOT_FOUND(HttpStatus.NOT_FOUND, "POST_META_NOT_FOUND", "요청하신 게시글 메타 정보를 찾을 수 없습니다."), //404
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND, "LIKE_NOT_FOUND", "해당 게시글에 대한 좋아요를 찾을 수 없습니다."), //404
    PICK_NOT_FOUND(HttpStatus.NOT_FOUND, "PICK_NOT_FOUND", "해당 게시글에 대한 투표를 찾을 수 없습니다."), //404
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT_NOT_FOUND", "요청하신 댓글을 찾을 수 없습니다."), //404

    DUPLICATED_USER(HttpStatus.CONFLICT, "DUPLICATED_USER", "이미 가입된 회원입니다."), //409
    DUPLICATED_LIKE(HttpStatus.CONFLICT, "DUPLICATED_LIKE", "이미 좋아요를 누른 게시글입니다."), //409
    DUPLICATED_PICK(HttpStatus.CONFLICT, "DUPLICATED_PICK", "이미 투표한 게시글입니다."), //409
    DUPLICATED_WITHDRAW(HttpStatus.CONFLICT, "DUPLICATED_WITHDRAW", "이미 탈퇴 처리된 회원입니다."), //409
    DUPLICATED_PASSWORD(HttpStatus.CONFLICT, "DUPLICATED_PASSWORD", "현재 비밀번호와 동일한 비밀번호로는 수정이 불가능합니다."), //409
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, "DUPLICATED_NICKNAME", "중복된 닉네임입니다."),

    FAIL_TO_ACCESS_DB(HttpStatus.INTERNAL_SERVER_ERROR, "FAIL_TO_ACCESS_DB", "데이터베이스에 접근하지 못하였습니다."); //500


    private final HttpStatus status;
    private final String code;
    private final String message;

}
