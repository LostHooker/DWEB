package es.mariana.dweb.tienda.view.controller;

import es.mariana.dweb.tienda.model.entities.Customer;
import es.mariana.dweb.tienda.model.entities.Product;
import es.mariana.dweb.tienda.model.services.CustomerService;
import es.mariana.dweb.tienda.model.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("customer/list")
    public String customerList(Model model) {

        final List<Customer> allCustomers = customerService.findAll();
        model.addAttribute("customers", allCustomers);
        return "customer/list";
    }



}
