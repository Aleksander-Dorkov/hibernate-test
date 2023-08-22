package com.sasho.hibernate.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Access(AccessType.FIELD)
@Getter
@SuperBuilder
@NoArgsConstructor
public class DomainUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_id_generator")
    @SequenceGenerator(name = "author_id_generator", sequenceName = "author_id_seq", allocationSize = 1)
    private Long id;
    private String name;
    private int birthYear;
    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST},
            mappedBy = "user"
    )
    private Address address;
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private Set<Book> books = new HashSet<>();
    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(name = "authors_cars",
            joinColumns = @JoinColumn(name = "author_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id")
    )
    private Set<Car> cars = new HashSet<>();

    public void addBooks(Set<Book> books) {
        for (Book book : books) {
            book.setDomainUser(this); // Set the author relationship in each book
        }
        this.books.addAll(books);
    }

    public void addCars(Set<Car> cars) {
        this.cars.addAll(cars);
    }

    public void addAddress(Address address) {
        this.address = address;
        address.setUser(this);
    }
}
