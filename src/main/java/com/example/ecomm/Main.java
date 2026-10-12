package com.example.ecomm;


import com.example.ecomm.models.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/productcatalogservice";
        try (Connection con = DriverManager.getConnection(url, "root", "rahul999");
             PreparedStatement ps = con.prepareStatement(
                     "SELECT id, name, price FROM demo_product WHERE id = ?")) {
            ps.setLong(1, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getBigDecimal("price"));
                System.out.println(product.getName() + " costs " + product.getPrice());
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        }

    }
}
