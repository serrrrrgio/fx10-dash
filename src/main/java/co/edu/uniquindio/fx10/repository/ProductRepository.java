package co.edu.uniquindio.fx10.repository;

import co.edu.uniquindio.fx10.model.Product;
import java.util.ArrayList;

public class ProductRepository {
    private static ProductRepository instance;
    private ArrayList<Product> products;

    private ProductRepository() {
        products = new ArrayList<>();
        loadSampleData();
    }

    public static ProductRepository getInstance() {
        if (instance == null) {
            instance = new ProductRepository();
        }
        return instance;
    }

    private void loadSampleData() {
        products.add(new Product("P001", "Laptop Dell", "Laptop Dell Inspiron 15", 1200.00, 10));
        products.add(new Product("P002", "Mouse Logitech", "Mouse inalámbrico Logitech MX Master", 89.99, 25));
        products.add(new Product("P003", "Teclado Mecánico", "Teclado mecánico RGB", 150.00, 15));
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(Product product) {
        return products.remove(product);
    }

    public Product findByCode(String code) {
        return products.stream()
                .filter(p -> p.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }

    public int getProductCount() {
        return products.size();
    }
}
