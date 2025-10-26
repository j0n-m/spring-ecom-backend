package com.ecommerce.spring_ecommerce.util;

import com.ecommerce.spring_ecommerce.config.AppConstants;
import com.ecommerce.spring_ecommerce.exceptions.APIException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableFactory {
    public Pageable of(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        if (pageNumber == null || pageNumber < 0) {
            throw new APIException("Page number must be greater than or equal to 0");
        }

        if (pageSize == null || pageSize <= 0 || pageSize > 50) {
            throw new APIException("Page size must be between 0 and 50");
        }

        if (sortBy == null || sortBy.isEmpty()) {
            throw new APIException("SortBy must be specified");
        }
        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = AppConstants.SORT_DIRECTION;
        }

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        return PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    }
}
