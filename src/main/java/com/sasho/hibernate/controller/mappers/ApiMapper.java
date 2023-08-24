package com.sasho.hibernate.controller.mappers;

import com.sasho.hibernate.controller.dto.request.AddressDto;
import com.sasho.hibernate.controller.dto.request.UserDto;
import com.sasho.hibernate.controller.dto.request.BookDto;
import com.sasho.hibernate.controller.dto.request.CarDto;
import com.sasho.hibernate.controller.dto.response.AllAddressResp;
import com.sasho.hibernate.controller.dto.response.AuthorByIdResponse;
import com.sasho.hibernate.domain.Address;
import com.sasho.hibernate.domain.DomainUser;
import com.sasho.hibernate.domain.Book;
import com.sasho.hibernate.domain.Car;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ApiMapper {
    AuthorByIdResponse toAuthorByIdResponse(DomainUser domainUser);

    DomainUser toAuthor(UserDto request);

    Book toBook(BookDto request);

    Car toCar(CarDto request);

    Address toAddress(AddressDto request);

    List<AllAddressResp> toAllAddressResp(List<Address> addresses);
}
