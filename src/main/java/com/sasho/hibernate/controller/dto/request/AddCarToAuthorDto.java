package com.sasho.hibernate.controller.dto.request;

import java.util.Set;

public record AddCarToAuthorDto(Set<Long> carIds, Long authorId) {
}
