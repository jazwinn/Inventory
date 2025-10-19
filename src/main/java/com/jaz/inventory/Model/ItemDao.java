package com.jaz.inventory.Model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDao {
    private final JdbcTemplate jdbcTemplate;

    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

//        Item item = new Item(0, "Mouse", 100, 99.99F, "Mouse disc");
//        AddItem(item);
    }

    public List<Item> getAllItems() {
        String sql = "SELECT * FROM inventory";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Item(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getInt("quantity"),
                rs.getFloat("price"),
                rs.getString("description")
        ));
    }

    public Item getItemById(int id) {
        String sql = "SELECT * FROM inventory WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setName(rs.getString("name"));
            item.setQuantity(rs.getInt("quantity"));
            item.setPrice(rs.getFloat("price"));
            item.setDescription(rs.getString("description"));
            return item;
        });
    }

    public void AddItem(Item item){
        String sql = "INSERT INTO inventory (name, quantity, price, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, item.getName(), item.getQuantity(), item.getPrice(), item.getDescription());
    }

    public void updateItem(Item item) {
        String sql = "UPDATE inventory SET name = ?, quantity = ?, price = ?, description = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                item.getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getDescription(),
                item.getId()
        );
    }

    public boolean ItemExist(int id){
        String sql = "SELECT COUNT(*) FROM inventory WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if(count > 0){return true;}
        return false;
    }

    public void RemoveItem(int id){
        String sql = "DELETE FROM inventory WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }


}
