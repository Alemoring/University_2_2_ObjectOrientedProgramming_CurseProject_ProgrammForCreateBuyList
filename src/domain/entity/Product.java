package domain.entity;

public class Product {
    private int id;
    private String name;
    private int price;
    private String fiscalSign;
    private long dateTime;
    private int idShop;
    public Product(int id, String name, int price, String fiscalSign, long dateTime, int idShop){
        this.id = id;
        this.name = name;
        this.price = price;
        this.fiscalSign = fiscalSign;
        this.dateTime = dateTime;
        this.idShop = idShop;
    }

    public Product(String name, int price, String fiscalSign, long dateTime, int idShop) {
        this.name = name;
        this.price = price;
        this.fiscalSign = fiscalSign;
        this.dateTime = dateTime;
        this.idShop = idShop;
    }
    public Product(String name, int price, long dateTime, int idShop) {
        this.name = name;
        this.price = price;
        this.dateTime = dateTime;
        this.idShop = idShop;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getFiscalSign() {
        return fiscalSign;
    }

    public long getDateTime() {
        return dateTime;
    }

    public int getIdShop() {
        return idShop;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setFiscalSign(String fiscalSign) {
        this.fiscalSign = fiscalSign;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public void setIdShop(int idShop) {
        this.idShop = idShop;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", fiscalSign='" + fiscalSign + '\'' +
                ", dateTime=" + dateTime +
                ", idShop=" + idShop +
                '}';
    }
}
