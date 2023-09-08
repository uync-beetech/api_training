package com.beetech.api_intern.features.city;

import com.beetech.api_intern.common.responses.CommonResponseBody;
import com.beetech.api_intern.features.city.dto.CityResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type City controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
public class CityController {
    private final CityService cityService;
    private final ModelMapper modelMapper;

    /**
     * Find all response entity.
     *
     * @return the response entity
     */
    @GetMapping("cities")
    public ResponseEntity<CommonResponseBody<Object>> findAll() {
        List<CityResponse> cities = cityService.findAll().stream().map(city -> modelMapper.map(city, CityResponse.class)).toList();
        Map<String, Object> data = new HashMap<>();
        data.put("cities", cities);
        return ResponseEntity.ok(new CommonResponseBody<>(data));
    }
}
