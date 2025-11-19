package com.rodem.shop.member;


public record MemberResponse(
        String email,
        String name,
        String phone,
        String flag
) {
    public static MemberResponse memberToResponse(Member member) {
        return new MemberResponse(member.getEmail(), member.getName(), member.getPhone(), member.getFlag());
    }
}
