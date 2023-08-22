package com.sasho.hibernate.controller.dto.response;

import lombok.Builder;

@Builder
public record AllAddressResp(
        Long id,
        String street,
        String city,
        AuthorResp author) {
    @Builder
    public record AuthorResp(Long id, String name, int birthYear) {

    }
}
