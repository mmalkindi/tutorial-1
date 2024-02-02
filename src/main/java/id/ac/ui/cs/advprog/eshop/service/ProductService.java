package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    public Product create(Product product);
    public Product get(String productId);
    public Product saveEdits(Product product, Product editedProduct);
    public List<Product> findAll();
}