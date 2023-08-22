package com.sasho.hibernate.controller.dto.request;

import java.util.List;

public record AddBooksDto(List<Long> bookIds, Long authorId) {

}
