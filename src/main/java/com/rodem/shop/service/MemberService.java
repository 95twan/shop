package com.rodem.shop.service;

import com.rodem.shop.common.ResponseEntity;
import com.rodem.shop.member.Member;
import com.rodem.shop.member.MemberRepository;
import com.rodem.shop.member.MemberRequest;
import com.rodem.shop.member.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public ResponseEntity<List<MemberResponse>> findAll() {
        List<MemberResponse> memberResponseList = memberRepository.findAll().stream().map(Member::memberToResponse).toList();
        return new ResponseEntity<>(HttpStatus.OK.value(), memberResponseList, memberResponseList.size());
    }

    public ResponseEntity<MemberResponse> create(@RequestBody MemberRequest request) {
        Member member = new Member(
                UUID.randomUUID(),
                request.email(),
                request.name(),
                request.password(),
                request.phone(),
                request.saltKey(),
                request.flag()
        );

        Member savedMember = memberRepository.save(member);
        MemberResponse memberResponse = savedMember.memberToResponse();
        return new ResponseEntity<>(HttpStatus.CREATED.value(), memberResponse, 1);
    }

    public ResponseEntity<MemberResponse> update(MemberRequest request, String id) {
        Member member = memberRepository.findById(UUID.fromString(id)).orElseThrow();
        member.updateMember(request);
        Member updatedMember = memberRepository.save(member);
        MemberResponse memberResponse = updatedMember.memberToResponse();
        return new ResponseEntity<>(HttpStatus.CREATED.value(), memberResponse, 1);
    }

    public ResponseEntity<Void> delete(String id) {
        memberRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK.value(), null, 0);
    }
}
