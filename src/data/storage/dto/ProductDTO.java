package data.storage.dto;

import domain.entity.Product;
import domain.entity.Shop;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class ProductDTO {
    private int id;
    private String name;
    private double price;

    private String fiscalSign;
    private Timestamp dateTime;
    private int idShop;
    private String shopName;
    public ProductDTO(Product product, List<Shop> shops){
        this.id = product.getId();
        this.name = product.getName();
        this.price = (double) product.getPrice() / 100;
        this.dateTime = new Timestamp(product.getDateTime() * 1000);
        //System.out.println(product.getDateTime() + " + " + dateTime);
        //Timestamp stamp = new Timestamp(1714155600000l); //5607059900000
        //Date date = new Date(stamp.getTime());
        //System.out.println("Now: " + date);
        for (Shop shop : shops){
            if (shop.getId() == product.getIdShop()){
                this.shopName = shop.getName();
            }
        }
        this.idShop = product.getIdShop();
    }

    public Product toProduct(){
        Product pr = new Product(this.id, this.name, (int) (this.price * 100), this.fiscalSign, this.dateTime.getTime(), this.idShop);
        return pr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getFiscalSign() {
        return fiscalSign;
    }

    public void setFiscalSign(String fiscalSign) {
        this.fiscalSign = fiscalSign;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }
}
