package data.repository.indatabase;

import domain.entity.Product;
import domain.port.ProductRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InDataBaseProductRepository implements ProductRepository {
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
    public Product create(Product product) throws SQLException {
        statement = connection.createStatement();
        statement.execute("INSERT INTO Product (name, price, fiscal_sign, date_time, id_shop) VALUES ('" + product.getName() + "', " +
                product.getPrice() + ", '" + product.getFiscalSign() + "', '" + product.getDateTime() + "', " + product.getIdShop() + "); ");
        return product;
    }

    @Override
    public List<Product> getAllProducts() throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery("SELECT * FROM Product");
        ArrayList<Product> products = new ArrayList<>();
        while(resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int price = resultSet.getInt("price");
            String fiscalSign = resultSet.getString("fiscal_sign");
            String dateTime = resultSet.getString("date_time");
            int idShop = resultSet.getInt("id_shop");
            products.add(new Product(id, name, price, fiscalSign, Long.parseLong(dateTime), idShop));
        }
        return products;
    }

    @Override
    public void update(Product product) throws SQLException {
        statement = connection.createStatement();
        statement.execute("UPDATE Product SET name='" + product.getName() + "', price=" +
                product.getPrice() + ", date_time='" + product.getDateTime() + "' WHERE id=" + product.getId() + "; ");
    }

    @Override
    public Product getProductByID(int id) {
        return null;
    }

    @Override
    public boolean deleteProductByID(int id) throws SQLException {
        statement = connection.createStatement();
        statement.execute("DELETE FROM Product WHERE id='" +id + "';");
        return false;
    }
}
