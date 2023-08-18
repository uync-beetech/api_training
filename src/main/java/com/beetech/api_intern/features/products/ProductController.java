package com.beetech.api_intern.features.products;

import com.beetech.api_intern.features.products.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        List<ProductDto> products = productPage.getContent().stream().map(product -> modelMapper.map(product, ProductDto.class)).toList();
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

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping(value = "create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProduct(
            @RequestParam("sku") String sku,
            @RequestParam("name") String name,
            @RequestParam("detailInfo") String detailInfo,
            @RequestParam("price") Double price,
            @RequestParam("categoryId") Long categoryId,
            @RequestPart("detailImage") List<MultipartFile> detailImage,
            @RequestPart("thumbnail") MultipartFile thumbnail
    ) {
        productService.createProduct(CreateProductDto.builder()
                .name(name)
                .sku(sku)
                .price(price)
                .categoryId(categoryId)
                .detailInfo(detailInfo)
                .detailImage(detailImage)
                .thumbnail(thumbnail)
                .build());
        return ResponseEntity.ok().build();
    }
}
