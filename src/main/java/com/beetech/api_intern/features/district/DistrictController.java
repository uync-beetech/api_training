package com.beetech.api_intern.features.district;

import com.beetech.api_intern.common.responses.CommonResponseBody;
import com.beetech.api_intern.features.district.dto.DistrictResponse;
import com.beetech.api_intern.features.district.dto.FindAllDistrictRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type District controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class DistrictController {
    private final DistrictService districtService;
    private final ModelMapper modelMapper;

    /**
     * Find all by city id response entity.
     *
     * @param request the request
     * @return the response entity
     */
    @PostMapping("districts")
    public ResponseEntity<CommonResponseBody<Object>> findAllByCityId(@Valid @RequestBody FindAllDistrictRequest request) {
        // convert cityId String -> Integer from request body
        Integer cityId = Integer.parseInt(request.getCityId());

        // find all districts of cityId
        List<DistrictResponse> districts = districtService.findAll(cityId)
                .stream()
                // map to response data
                .map(district -> modelMapper.map(district, DistrictResponse.class))
                .toList();

        // create response data hash map
        Map<String, Object> data = new HashMap<>();
        // put districts to data
        data.put("districts", districts);
        // create response body
        CommonResponseBody<Object> body = new CommonResponseBody<>(data);
        // response data to client
        return ResponseEntity.ok(body);
    }
}
