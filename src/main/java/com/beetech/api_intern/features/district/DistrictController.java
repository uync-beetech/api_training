package com.beetech.api_intern.features.district;

import com.beetech.api_intern.features.district.dto.DistrictDto;
import com.beetech.api_intern.features.district.dto.FindAllDistrictDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type District controller.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class DistrictController {
    private final DistrictService districtService;
    private final ModelMapper modelMapper;

    /**
     * Find all by city id response entity.
     *
     * @param dto the dto
     * @return the response entity
     */
    @PostMapping("districts")
    public ResponseEntity<List<DistrictDto>> findAllByCityId(@RequestBody FindAllDistrictDto dto) {
        List<DistrictDto> districts = districtService.findAll(dto.getCityId())
                .stream()
                .map(district -> modelMapper.map(district, DistrictDto.class))
                .toList();
        return ResponseEntity.ok(districts);
    }
}
