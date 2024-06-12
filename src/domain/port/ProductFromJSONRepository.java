package domain.port;

import domain.entity.Product;

import java.util.List;

public interface ProductFromJSONRepository {
    Product create (Product product);

    List<Product> getAllProducts();

    void update(Product product);

    Product getProductByID(int id);
    boolean deleteProductByID(int id);
}
