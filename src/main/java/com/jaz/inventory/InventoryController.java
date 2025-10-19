package com.jaz.inventory;

import com.jaz.inventory.Model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/inventory")
public class InventoryController {

    private final ItemDao itemDao;

    public InventoryController(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    @GetMapping
    public String DisplayInventory(Model model) {

        model.addAttribute("inventoryList", itemDao.getAllItems());

        return "inventory"; // Thymeleaf will look for templates/inventory.html
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable int id) {
        Item item = itemDao.getItemById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Void> AddItem(@RequestBody Item item) {// request body to convert from json to object
        itemDao.AddItem(item);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> UpdateItem(@RequestBody Item item , @PathVariable("id") int id) {
        item.setId(id);
        itemDao.updateItem(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> DeleteItem(@PathVariable("id") int id) {
        if (itemDao.ItemExist(id)){//check if id exist
           itemDao.RemoveItem(id);
           return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }


}
