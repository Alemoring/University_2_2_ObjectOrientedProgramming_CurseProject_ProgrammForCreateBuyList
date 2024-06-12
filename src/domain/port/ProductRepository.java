package domain.port;

import domain.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductRepository {
    Product create (Product product) throws SQLException, ClassNotFoundException;

    List<Product> getAllProducts() throws SQLException;

    void update(Product product) throws SQLException;

    Product getProductByID(int id);
    boolean deleteProductByID(int id) throws SQLException;
}
