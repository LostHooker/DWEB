/*
 * =============================================================================
 *
 *   Copyright (c) 2011-2026 Thymeleaf (http://www.thymeleaf.org)
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 * =============================================================================
 */
package es.mariana.dweb.tienda.view.controller;

import es.mariana.dweb.tienda.model.entities.Product;
import es.mariana.dweb.tienda.model.entities.ProductNameComparator;
import es.mariana.dweb.tienda.model.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("product/list")
    public String productList(Model model) {

        final List<Product> allProducts = productService.findAll();
        model.addAttribute("prods", allProducts);
        model.addAttribute("maxPrice", null);
        model.addAttribute("inStock", null);
        model.addAttribute("order", null);
        return "product/list";
    }

    @GetMapping("product/search")
    public String searchProducts(@RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice,
                                 @RequestParam(value = "inStock", required = false) Boolean inStock,
                                 @RequestParam(value = "order", required = false) String order,Model model) 
        {
        final List<Product> matchingProducts = new ArrayList<>();
        for (Product product : productService.findAll()) {
            if (maxPrice != null && product.getPrice().compareTo(maxPrice) > 0) {
                continue;
            }
            if (inStock != null && product.isInStock() != inStock) {
                continue;
            }
            matchingProducts.add(product);
        }
        if ("asc".equalsIgnoreCase(order)) {
            matchingProducts.sort(new ProductNameComparator());
        } else if ("desc".equalsIgnoreCase(order)) {
            matchingProducts.sort(new ProductNameComparator().reversed());
        }

        model.addAttribute("prods", matchingProducts);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("inStock", inStock);
        model.addAttribute("order", order);
        return "product/list";
    }

    @GetMapping("product/{prodId}/comments")
    public String orderList(@PathVariable("prodId") Integer prodId, Model model) {

        final Product product = productService.findById(prodId);
        model.addAttribute("prod", product);
        return "product/comments";
    }

    @GetMapping("product/{prodId}")
    public String productDetail(@PathVariable("prodId") Integer prodId, Model model) {

        final Product product = productService.findById(prodId);
        model.addAttribute("prod", product);
        return "product/detail";
    }

}
