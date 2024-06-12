package data.repository.indatabase;

import domain.entity.Product;
import domain.entity.Shop;
import domain.port.ShopRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InDataBaseShopRepository implements ShopRepository {
    private static String DB = "DB.db";
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        connection = null;
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:" + DB);
        System.out.println("DataBase connected!");
    }
    public static void CloseDB() throws SQLException
    {
        connection.close();
        if (statement != null){
            statement.close();
        }
        if (resultSet != null){
            resultSet.close();
        }
        System.out.println("DataBase connection closed");
    }

    @Override
    public Shop create(Shop shop) throws SQLException {
        statement = connection.createStatement();
        statement.execute("INSERT INTO shop (name, address, longitude, latitude) VALUES ('" + shop.getName() + "', '" +
                shop.getAddress() + "', '" + shop.getLongitude() + "', '" + shop.getLatitude() + "'); ");
        return shop;
    }


    @Override
    public Shop updateLgAndLt(Shop shop) throws SQLException {
        statement = connection.createStatement();
        statement.execute("UPDATE shop SET longitude='" + shop.getLongitude() + "', latitude='" + shop.getLatitude() + "' WHERE id='" + shop.getId() + "';");
        return shop;
    }
    public void updateNameByid(String name, int id) throws SQLException {
        statement = connection.createStatement();
        statement.execute("UPDATE shop SET name='" + name + "' WHERE id='" + id + "';");
    }

    @Override
    public List<Shop> getAllShops() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM shop");
        ArrayList<Shop> shops = new ArrayList<>();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String address = resultSet.getString("address");
            String longitude = resultSet.getString("longitude");
            String latitude = resultSet.getString("latitude");
            shops.add(new Shop(id, name, address, longitude, latitude));
        }
        return shops;
    }

    @Override
    public Shop getShopByID(int id) {
        return null;
    }

    @Override
    public boolean deleteByID(int id){
        try{
            statement = connection.createStatement();
            statement.execute("DELETE FROM shop WHERE id='" +id + "';");
            return true;
        }catch (Exception ex){
            System.out.println(ex);
            return false;
        }

    }

    public void updateAddressByid(String address, int id) {
        try {
            statement = connection.createStatement();
            statement.execute("UPDATE shop SET address='" + address + "' WHERE id='" + id + "';");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
