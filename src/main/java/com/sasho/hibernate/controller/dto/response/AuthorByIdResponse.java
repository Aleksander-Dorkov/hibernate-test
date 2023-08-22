package com.sasho.hibernate.controller.dto.response;

import lombok.Builder;

import java.util.List;

@Builder
public record AuthorByIdResponse(long id,
                                 String name,
                                 int birthYear,
                                 AddressResp address,
                                 List<BookResp> books,
                                 List<CarResp> cars) {
    @Builder
    public record AddressResp(long id, String street, String city) {
    }

    @Builder
    public record BookResp(long id, String name, int releaseYear) {
    }

    @Builder
    public record CarResp(long id, String name, int yearProduced) {
    }
}
