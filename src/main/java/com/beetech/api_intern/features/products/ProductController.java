package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.products.dto.FindAllDto;
import com.beetech.api_intern.features.products.dto.FindAllResponse;
import com.beetech.api_intern.features.products.dto.ProductDetailDto;
import com.beetech.api_intern.features.products.dto.ProductDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping("products")
    public ResponseEntity<FindAllResponse> findAll(
            @Valid @RequestBody FindAllDto dto,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        Page<Product> productPage = productService.findAll(dto, size, page);
        List<ProductDto> products = productPage.getContent().stream().map(ProductDto::new).toList();
        var data = FindAllResponse.builder()
                .products(products)
                .pageNumber(productPage.getNumber())
                .totalPages(productPage.getTotalPages())
                .build();
        return ResponseEntity.ok(data);
    }

    @GetMapping("products/{sku}")
    public ResponseEntity<ProductDetailDto> findOne(@PathVariable("sku") String sku) {
        ProductDetailDto data = modelMapper.map(productService.findOne(sku), ProductDetailDto.class);
        return ResponseEntity.ok(data);
    }
}
