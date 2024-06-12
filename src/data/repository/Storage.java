package data.repository;

import data.storage.dto.ProductDTO;
import data.storage.dto.ShopDTO;
import domain.entity.Product;
import domain.entity.Shop;

import java.util.List;

public interface Storage {
    List<ProductDTO> readAllProducts();

    void saveAllProducts(List<ProductDTO> products);

    ProductDTO addProduct(ProductDTO product);

    List<ShopDTO> readAllShops();

    void saveAllShops(List<ShopDTO> shops);

    ShopDTO addShop(ShopDTO shop);
}
