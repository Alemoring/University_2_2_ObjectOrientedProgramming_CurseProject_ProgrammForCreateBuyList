package domain.entity;

public class ProductFromJSON {
    // Название магазина
    private String retailPlace;
    // Адрес магазина
    private String retailPlaceAddress;
    // Наименование товара
    private String name;
    // Цена товара в копейках
    private int price;
    // Уникальный номер чека
    private String fiscalSign;
    // Date d = new Date(timestamp ); From timestamp to Date
    // Дата и время покупки в Time Stamp, GMT +0
    private long dateTime;

    public String ToString() {
        return "product{ retailPlace=" + getRetailPlace() + "; retailPlaceAddress=" + getRetailPlaceAddress() +
                "; name=" + getName() + "; price=" + getPrice() + "; Fiscal Document Number= " + getFiscalSign() + "; Date Time=" + getDateTime();
    }

    public String getRetailPlace() {
        return retailPlace;
    }

    public String getRetailPlaceAddress() {
        return retailPlaceAddress;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
    public String getFiscalSign(){
        return fiscalSign;
    }
    public long getDateTime(){
        return dateTime;
    }
}
