package com.jaz.inventory;

import com.jaz.inventory.Data.Item;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class InventoryController {

    @RequestMapping("/inventory")
    public String index(Model model) {

        List<Item> items = Arrays.asList(
                new Item(0, "Laptop", 10, 999.99F, "Laptop Disc"),
                new Item(1, "Mouse", 25, 19.99F, "Mouse Disc"),
                new Item(2, "Keyboard", 15, 49.99F, "Keyboard Disc")
        );

        model.addAttribute("inventoryList", items);

        return "inventory"; // Thymeleaf will look for templates/inventory.html
    }
}
