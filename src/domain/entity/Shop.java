package domain.entity;

import java.util.Objects;

public class Shop {
    private int id;
    private String name;
    private String address;
    private String longitude; // идёт вторым 104...
    private String latitude; // идёт первым 52...

    public Shop(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Shop(int id, String name, String address, String longitude, String latitude) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        if(longitude != null){
            return longitude;
        }
        return "";
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        if(latitude != null){
            return latitude;
        }
        return "";
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return Objects.equals(name, shop.name) && Objects.equals(address, shop.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }
}
