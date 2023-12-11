package com.example.ecommerceprojspringbootrefactored.controllers;
import com.example.ecommerceprojspringbootrefactored.dtos.AdminDto;
import com.example.ecommerceprojspringbootrefactored.dtos.PasswordDTO;
import com.example.ecommerceprojspringbootrefactored.dtos.UsersDTO;
import com.example.ecommerceprojspringbootrefactored.models.Admin;
import com.example.ecommerceprojspringbootrefactored.models.Product;
import com.example.ecommerceprojspringbootrefactored.models.Users;
import com.example.ecommerceprojspringbootrefactored.serviceImpl.AdminServiceImpl;
import com.example.ecommerceprojspringbootrefactored.serviceImpl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    @Autowired
    public AdminController(AdminServiceImpl adminService){this.adminService = adminService;}

    //the ModelAndView helps us to set (the "object" required in "th:object")
    // and (view that is the page that will be shown)




    @GetMapping("/admin-reg")
    public String signUpPage(Model model){
        model.addAttribute("admin", new AdminDto());
        return "admin-signup";
    }

    //@ModelAttribute is used to bind the form data (from the user's input) to the UserDto object.
    //the "/signup" in @PostMapping must be the same in form action="/signup" ,form method will be "post"
    @PostMapping("/admin-signup")
    public String signup(@ModelAttribute AdminDto adminDto) {
        Admin admin = adminService.saveAdmin.apply(new Admin(adminDto));
        log.info("Admin created --->{} ", admin);
        return "/admin-login";
    }


    @GetMapping("/admin-login")
    public ModelAndView loginPage(Model model){
        return new ModelAndView("admin-login").addObject("admin", new AdminDto());
    }

    @PostMapping("/admin-sign-in")
    public String login(@ModelAttribute AdminDto adminDto, HttpServletRequest request, Model model) {
        Admin admin = adminService.findAdminByEmail.apply(adminDto.getEmail());
        log.info("Admin details ---> {}", admin);

        if(adminService.verifyAdminPassword.apply(PasswordDTO.builder()
                .password(adminDto.getPassword())
                .hashPassword(admin.getPassword())
                .build())){
            HttpSession session = request.getSession();
            session.setAttribute("adminID", admin.getId());
            return "redirect:/products/admin-products-all";
        }
            return "redirect:/admin/admin-login";
        }


    @GetMapping("/admin-logout")
    public String logOut(HttpSession session){
        session.invalidate();
        return "/index";
    }

}