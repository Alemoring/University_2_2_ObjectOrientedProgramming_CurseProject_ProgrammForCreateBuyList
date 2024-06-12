package domain.usecase;

import domain.entity.Product;
import domain.entity.Shop;

import java.util.*;
import java.util.stream.Collectors;

public class FindMinimalPrice {
    public static List<Product> findMinimalPrice(List<Product> products, String name){
        List<Product> result = new ArrayList<Product>();
        for (Product product : products) {
            if (product.getName().toLowerCase().contains(name.toLowerCase())){
                result.add(product);
            }
        }
        Comparator<Product> compareByName = Comparator
                .comparing(Product::getPrice)
                .thenComparing(Product::getPrice);

        ArrayList<Product> sorted = result.stream()
                .sorted(compareByName)
                .collect(Collectors.toCollection(ArrayList::new));
        return sorted;
    }
    public static List<Product> findMinimalPrice(ArrayList<Product> products){
        Comparator<Product> compareByName = Comparator
                .comparing(Product::getPrice)
                .thenComparing(Product::getPrice);

        ArrayList<Product> sorted = products.stream()
                .sorted(compareByName)
                .collect(Collectors.toCollection(ArrayList::new));
        return sorted;
    }
}
