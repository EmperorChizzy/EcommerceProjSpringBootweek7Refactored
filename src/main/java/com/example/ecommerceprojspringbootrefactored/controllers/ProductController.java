package com.example.ecommerceprojspringbootrefactored.controllers;

import com.example.ecommerceprojspringbootrefactored.models.Product;
import com.example.ecommerceprojspringbootrefactored.serviceImpl.AdminServiceImpl;
import com.example.ecommerceprojspringbootrefactored.serviceImpl.OrderServiceImpl;
import com.example.ecommerceprojspringbootrefactored.serviceImpl.ProductServiceImpl;
import com.example.ecommerceprojspringbootrefactored.serviceImpl.UsersServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductServiceImpl productService;
    private final UsersServiceImpl usersService;
    private final OrderServiceImpl orderService;

    @Autowired
    public ProductController(ProductServiceImpl productService, UsersServiceImpl usersService, OrderServiceImpl orderService) {
        this.productService = productService;
        this.usersService = usersService;
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public ModelAndView findAllProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product> productList = productService.findAllProducts.get();
        return new ModelAndView("dashboard")
                .addObject("products", productList)
                .addObject("cartItems", "Cart Items: "+session.getAttribute("cartItems"));
    }

    @GetMapping("/admin-products-all")
    public ModelAndView adminProducts(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Product> productList = productService.findAllProducts.get();
        return new ModelAndView("admin_dashboard")
                .addObject("products", productList);
    }






    @GetMapping("/add-cart")
    public String addToCart(@RequestParam(name = "cart") Long id, HttpServletRequest request, Model model){
        productService.addProductToCart(id, request);
        return "redirect:/products/all";
    }



    @GetMapping("/payment")
    public String checkOut(HttpSession session, Model model){
        productService.checkOutCart(session, model);
        model.addAttribute("paid", "");
        return "checkout";
    }

    @GetMapping("/pay")
    public String orderPayment(HttpSession session, Model model){
        return orderService.makePayment(session, model);
    }


    // Add product
    @GetMapping("/add-product-page")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "Admin-Add-Product";
    }

    @GetMapping("/edit-product-page")
    public String editProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "Admin-Edit-Product";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute Product product) {
        productService.addProduct(product);
        return "redirect:/products/admin-products-all";
    }

    // Edit product
    @GetMapping("/edit-product/{id}")
    public String editProductPage(@PathVariable(value = "id") Long id, Model model) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "redirect:/products/edit-product-page";
    }



    @PostMapping("/edit-product")
    public String editProduct(@ModelAttribute Product product) {
        productService.editProduct(product);
        return "redirect:/admin/admin_dashboard";
    }

    // Delete product
    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable(value = "id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/admin-products-all";
    }
}




