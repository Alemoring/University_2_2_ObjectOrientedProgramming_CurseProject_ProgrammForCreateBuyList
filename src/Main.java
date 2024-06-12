import data.repository.indatabase.*;
import domain.entity.Product;
import domain.entity.Shop;
import domain.usecase.FindLongAndLat;
import domain.usecase.FindMinimalPrice;
import domain.usecase.FindingTheShortestPath;
import domain.usecase.ProductFromJSONToProductAndShop;
import presrentation.gui.MainForm;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        MainForm gui = new MainForm();
        /*ProductFromJSONToProductAndShop pfJSONtpas = new ProductFromJSONToProductAndShop("C:\\Users\\Алемор\\Desktop\\Саша\\Обучение\\ООП\\ProjectCurse\\CurseProject", "products.json");
        InDataBaseShopRepository.Conn();
        InDataBaseShopRepository repS = new InDataBaseShopRepository();
        List<Shop> shops = repS.getAllShops();
        for (Shop shop : shops){
            System.out.println(shop);
        }
        InDataBaseShopRepository.CloseDB();
        InDataBaseProductRepository.Conn();
        InDataBaseProductRepository repP = new InDataBaseProductRepository();
        List<Product> products = repP.getAllProducts();
        for (Product product : products){
            System.out.println(product);
        }
        InDataBaseProductRepository.CloseDB();
        System.out.println("--------------Sorted--------------");
        List<Product> sorted = FindMinimalPrice.findMinimalPrice(products, "Чипсы");
        for (Product product : sorted){
            System.out.println(product);
        }
        System.out.println("----------------------Shortest path------------------");
        // 52.235819, 104.279636
        String[] Home = new String[2];
        Home[0] = "104.279636";
        Home[1] = "52.235819";
        FindingTheShortestPath.findingTheShortestPath(sorted, shops, Home);
        FindLongAndLat.findLongAndLatAll(shops);
        InDataBaseShopRepository.Conn();
        for (Shop shop : shops){
            repS.updateLgAndLt(shop);
        }
        InDataBaseShopRepository.CloseDB();*/
    }
}