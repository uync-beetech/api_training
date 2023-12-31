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

import java.util.List;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @PostMapping("products")
    public ResponseEntity<FindAllResponse> findAll(@Valid @RequestBody FindAllRequest dto, @RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "10") Integer size) {
        Page<Product> productPage = productService.findAll(dto, size, page);
        List<ProductResponse> products = productPage.getContent().stream().map(product -> modelMapper.map(product, ProductResponse.class)).toList();
        var data = FindAllResponse.builder().products(products).pageNumber(productPage.getNumber()).totalPages(productPage.getTotalPages()).build();
        return ResponseEntity.ok(data);
    }

    @GetMapping("products/{sku}")
    public ResponseEntity<ProductDetailResponse> findOne(@PathVariable("sku") String sku) {
        ProductDetailResponse data = modelMapper.map(productService.findOne(sku), ProductDetailResponse.class);
        return ResponseEntity.ok(data);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping(value = "create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> createProduct(@Valid @ModelAttribute CreateProductRequest createProductRequest) {
        productService.createProduct(createProductRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("delete-product")
    public ResponseEntity<Object> deleteProduct(@Valid @RequestBody DeleteProductRequest dto) {
        productService.deleteById(dto.getId());
        return ResponseEntity.ok().build();
    }
}
