package com.beetech.api_intern.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * The type Api error vo.
 */
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ApiErrorVO {
    /**
     * errorMessage String
     */
    @NonNull
    private String errorMessage;

    /**
     * validationList contains list error for validation
     */
    private List<String> validationList;
}
