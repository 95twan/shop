package com.rodem.shop.member.presentation;

import com.rodem.shop.common.ResponseEntity;
import com.rodem.shop.member.presentation.dto.MemberRequest;
import com.rodem.shop.member.application.dto.MemberInfo;
import com.rodem.shop.member.application.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.v1}/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @Operation(
            summary = "회원 목록 조회",
            description = "public.member 테이블에 저장된 모든 회원을 조회한다."
    )
    @GetMapping
    public ResponseEntity<List<MemberInfo>> findAll(Pageable pageable) {
        return memberService.findAll(pageable);
    }

    @Operation(
            summary = "회원 등록",
            description = "요청으로 받은 회원 정보를 public.member 테이블에 저장한다."
    )
    @PostMapping
    public ResponseEntity<MemberInfo> create(@RequestBody MemberRequest request) {
        return memberService.create(request.toCommand());
    }

    @Operation(
            summary = "회원 수정",
            description = "요청으로 받은 회원 정보를 public.member 테이블에 수정한다."
    )
    @PutMapping("/{id}")
    public ResponseEntity<MemberInfo> update(@RequestBody MemberRequest request, @PathVariable String id) {
        return memberService.update(request.toCommand(), id);
    }

    @Operation(
            summary = "회원 정보 삭제",
            description = "요청으로 받은 회원 정보를 public.member 테이블에 삭제한다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        return memberService.delete(id);
    }

}
