package com.sasho.hibernate.controller;

import com.sasho.hibernate.controller.dto.request.AddressDto;
import com.sasho.hibernate.controller.dto.request.BookDto;
import com.sasho.hibernate.controller.dto.request.CarDto;
import com.sasho.hibernate.controller.dto.request.UserDto;
import com.sasho.hibernate.controller.dto.response.AllAddressResp;
import com.sasho.hibernate.controller.dto.response.AuthorByIdResponse;
import com.sasho.hibernate.controller.mappers.ApiMapper;
import com.sasho.hibernate.domain.Address;
import com.sasho.hibernate.domain.Book;
import com.sasho.hibernate.domain.Car;
import com.sasho.hibernate.domain.DomainUser;
import com.sasho.hibernate.repos.AddressRepo;
import com.sasho.hibernate.repos.BookRepo;
import com.sasho.hibernate.repos.CarRepo;
import com.sasho.hibernate.repos.UserRepo;
import com.sasho.hibernate.security.dto.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class API {
    private final BookRepo bookRepo;
    private final UserRepo authorRepo;
    private final AddressRepo addressRepo;
    private final CarRepo carRepo;
    private final ApiMapper mapper;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return this.bookRepo.findAll();
    }

    @GetMapping("/users")
    public List<DomainUser> getAllAuthors(@AuthenticationPrincipal CurrentUser a) {
        System.out.println(a);
        return this.authorRepo.findAll();
    }

    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return this.carRepo.findAll();
    }

    @GetMapping("/addresses")
    public List<AllAddressResp> getAllAddresses() {
        var addresses = this.addressRepo.findAllBy();
        return this.mapper.toAllAddressResp(addresses);
    }

    @GetMapping("/user/{id}")
    public AuthorByIdResponse getAuthor(@PathVariable Long id) {
        var author = this.authorRepo.findByIdWithAllRelations(id);
        return this.mapper.toAuthorByIdResponse(author);
    }

    @GetMapping("/book/{id}")
    public Book getBook(@PathVariable Long id) {
        return this.bookRepo.findOneWithAuthor(id);
    }

    @PostMapping("/user")
    public DomainUser postBook(@RequestBody UserDto request) {
        DomainUser domainUser = this.mapper.toAuthor(request);
        return authorRepo.save(domainUser);
    }

    @PostMapping("/book")
    public String postBook(@RequestBody BookDto request) {
        Book book = this.mapper.toBook(request);
        var author = authorRepo.findById(request.authorId()).get();
        author.addBooks(Set.of(book));
        authorRepo.save(author);
        return "success";
    }

    @PostMapping("/car")
    public String postBook(@RequestBody CarDto request) {
        Car car = this.mapper.toCar(request);
        var author = this.authorRepo.findById(request.authorId()).get();
        author.addCars(Set.of(car));
        this.authorRepo.save(author);
        return "success";
    }

    @PostMapping("/address")
    public String addAddressToAuthor(@RequestBody AddressDto request) {
        Address address = this.mapper.toAddress(request);
        var author = this.authorRepo.findById(request.authorId()).get();
        author.addAddress(address);
        this.authorRepo.save(author);
        return "success";
    }
}
