package domain.usecase;

import domain.entity.Product;
import domain.entity.Shop;

import java.util.*;

public class FindingTheShortestPath {
    public static List<Shop> findingTheShortestPath(ArrayList<Product> products, ArrayList<Shop> shops, String[] Home){
        List<Shop> shopOfProducts = new ArrayList<>();
        shopOfProducts.add(new Shop(1, "Home", "Home", Home[0], Home[1]));
        for (Product product : products){
            for (Shop shop : shops){
                if (product.getIdShop() == shop.getId() && !shopOfProducts.contains(shop)){
                    shopOfProducts.add(shop);
                }
            }
        }
        Map<Integer,Map<Integer, Double>> graph = new HashMap<>();
        for (Shop shop1 : shopOfProducts){
            Map<Integer, Double> put = new HashMap<>();
            for (Shop shop2 : shopOfProducts){
                if (shop1 != shop2){
                    double lnsq = Math.pow(Double.parseDouble(shop1.getLongitude()) -
                            Double.parseDouble(shop2.getLongitude()), 2);
                    double ltsq = Math.pow(Double.parseDouble(shop1.getLatitude()) -
                            Double.parseDouble(shop2.getLatitude()), 2);
                    double d = Math.sqrt(lnsq + ltsq);
                    put.put(shop2.getId(), d);
                }
            }
            graph.put(shop1.getId(), put);
        }
        List<Shop> resultShops = new ArrayList<>();
        List<Integer> processed = new ArrayList<>();
        Object[] keys = graph.keySet().toArray();
        int i = 0;
        int curr = (int) keys[i];
        while(!processed.contains(curr)){
            double minimalPath = Double.MAX_VALUE;
            int idNode = curr;
            for (Map.Entry entry : graph.get(curr).entrySet()){
                if ((double) entry.getValue() < minimalPath && !processed.contains((int) entry.getKey())){
                    minimalPath = (double) entry.getValue();
                    idNode = (int) entry.getKey();
                }
            }
            processed.add(curr);
            for (Shop shop : shopOfProducts){
                if (shop.getId() == curr){
                    resultShops.add(shop);
                }
            }
            curr = idNode;
        }
        System.out.println(processed);
        return resultShops;
    }
}
