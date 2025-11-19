package com.rodem.shop.member;

public record MemberRequest(
        String email,
        String name,
        String password,
        String phone,
        String saltKey,
        String flag
) {
}
