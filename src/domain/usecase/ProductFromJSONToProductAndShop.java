package domain.usecase;

import data.repository.inJSONFile.inJSONFileRepository;
import domain.entity.Product;
import domain.entity.ProductFromJSON;
import domain.entity.Shop;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProductFromJSONToProductAndShop {
    List<Shop> shops = new ArrayList<Shop>();
    List<Product> products = new ArrayList<Product>();

    public ProductFromJSONToProductAndShop(String path, String fileName) throws IOException {
        inJSONFileRepository parse = new inJSONFileRepository(path, fileName);
        List<ProductFromJSON> productFromJSONS = parse.readAllProducts();
        int i = 1;
        int j = 1;
        for (ProductFromJSON product : productFromJSONS){
            Shop shop = new Shop(j, product.getRetailPlace(), product.getRetailPlaceAddress());
            if(!shops.contains(shop)){
                shops.add(shop);
                j++;
            }
            //System.out.println(shop.equals(shops.getLast()));
            products.add(new Product(product.getName(), product.getPrice(), product.getFiscalSign(), product.getDateTime(), shops.indexOf(shop)+1));
            i++;

        }
    }

    public List<Product> convertToProduct(){
        return products;
    }
    public List<Shop> convertToShop(){
        return shops;
    }
}
