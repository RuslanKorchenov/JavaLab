package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.dto.OrderDto;
import ru.itis.dto.ProductDto;
import ru.itis.models.Product;
import ru.itis.models.User;
import ru.itis.security.jwt.details.UserDetailsImpl;
import ru.itis.services.interfaces.ProductService;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/products/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView getAddPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addProduct");
        return modelAndView;
    }

    @PostMapping(value = "/products/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView addProduct(ProductDto productDto) {
        productService.addProduct(productDto);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("check", "Product added!");
        modelAndView.setViewName("addProduct");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/products")
    public String getProducts(Model model) {
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/products/buy/{id}")
    public ModelAndView buyProduct(@PathVariable(value = "id") Long id, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        productService.addOrder(OrderDto.builder()
                .userId(userDetails.getUser())
                .productId(id)
                .build());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("check", "You buy product with ID: " + id);
        modelAndView.setViewName("products");
        return modelAndView;
    }
}
