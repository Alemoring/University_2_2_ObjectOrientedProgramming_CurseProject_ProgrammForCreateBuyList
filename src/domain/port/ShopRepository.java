package domain.port;

import domain.entity.Shop;

import java.sql.SQLException;
import java.util.List;

public interface ShopRepository {
    Shop create (Shop shop) throws SQLException, ClassNotFoundException;

    Shop updateLgAndLt(Shop shop) throws SQLException;

    List<Shop> getAllShops() throws SQLException;

    Shop getShopByID(int id);

    boolean deleteByID(int id) throws SQLException;
}
