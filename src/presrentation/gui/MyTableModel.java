package presrentation.gui;

import data.repository.indatabase.InDataBaseProductRepository;
import data.repository.indatabase.InDataBaseShopRepository;
import data.storage.dto.ProductDTO;
import domain.entity.Product;
import domain.entity.Shop;
import domain.usecase.FindLongAndLat;
import domain.usecase.FindMinimalPrice;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class MyTableModel extends AbstractTableModel {
    private int sizeRow;
    private ArrayList<ProductDTO> products;
    private ArrayList<Shop> shops;
    public MyTableModel(ArrayList<ProductDTO> pr, ArrayList<Shop> shops){
        this.products = pr;
        this.shops = shops;
        if (products == null){
            sizeRow = 1;
        }else {
            sizeRow = products.size();
        }
    }
    @Override
    public int getRowCount() {
        return sizeRow;
    }
    @Override
    public int getColumnCount() {
        return 6;
    }
    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Num";
            case 1:
                return "Name";
            case 2:
                return "Price";
            case 3:
                return "Date/Time";
            case 4:
                return "Name of Magazine";
            case 5:
                return "Address";
            default:
                return "default";
        }
    }
    @Override
    public Object getValueAt(int row, int column) {
        if (products.isEmpty() || row >= products.size()){
            if (column == 0){
                return row + 1;
            }
            return "-";
        }
        switch (column){
            case 0:
                return row + 1;
            case 1:
                return products.get(row).getName();
            case 2:
                return products.get(row).getPrice();
            case 3:
                DateFormat df = new SimpleDateFormat("dd MM yyyy");
                return df.format(products.get(row).getDateTime());
            case 4:
                return products.get(row).getShopName();
            case 5:
                for (Shop shop : shops){
                    if (products.get(row).getIdShop() == shop.getId()){
                        return shop.getAddress();
                    }
                }
                //return shops.get();
            default:
                return "-";
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return false;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            case 4:
                return true;
            case 5:
                return true;
            default:
                return true;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (rowIndex <= products.size()){
            switch (columnIndex){
                case 1:// Имя
                    try {
                        products.get(rowIndex).setName((String) aValue);
                        InDataBaseProductRepository.Conn();
                        InDataBaseProductRepository dBp = new InDataBaseProductRepository();
                        dBp.update(products.get(rowIndex).toProduct());
                        InDataBaseProductRepository.CloseDB();
                    }catch (IndexOutOfBoundsException ex) {
                        JOptionPane.showMessageDialog(null, "That wrong");
                    }catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                case 2: // Цена
                    try{
                        products.get(rowIndex).setPrice(Double.parseDouble((String) aValue));
                        InDataBaseProductRepository.Conn();
                        InDataBaseProductRepository dBp = new InDataBaseProductRepository();
                        dBp.update(products.get(rowIndex).toProduct());
                        InDataBaseProductRepository.CloseDB();
                    }catch (IndexOutOfBoundsException ex){
                        JOptionPane.showMessageDialog(null, "That wrong");
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Wrong format");
                    } catch (SQLException e) {
                        System.out.println(e);
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                    }catch (ClassCastException e){
                        System.out.println(e);
                        JOptionPane.showMessageDialog(null, "Wrong format");
                    }
                    break;
                case 3: // Дата
                    try{
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
                        Date parsedDate = dateFormat.parse((String) aValue);
                        Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                        products.get(rowIndex).setDateTime(timestamp);
                        InDataBaseProductRepository.Conn();
                        InDataBaseProductRepository dBp = new InDataBaseProductRepository();
                        dBp.update(products.get(rowIndex).toProduct());
                        InDataBaseProductRepository.CloseDB();
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Wrong format");
                    }catch (IndexOutOfBoundsException ex){
                        JOptionPane.showMessageDialog(null, "That wrong");
                    } catch (SQLException e) {
                        System.out.println(e);
                    } catch (ClassNotFoundException e) {
                        System.out.println(e);
                    } catch (ParseException e) {
                        System.out.println(e);
                    }
                    break;
                case 4: // Название магазина
                    try{
                        products.get(rowIndex).setShopName((String) aValue);
                        InDataBaseShopRepository.Conn();
                        InDataBaseShopRepository dBs = new InDataBaseShopRepository();
                        dBs.updateNameByid((String) aValue, products.get(rowIndex).getIdShop());
                        InDataBaseShopRepository.CloseDB();
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Wrong format");
                    }catch (IndexOutOfBoundsException ex){
                        JOptionPane.showMessageDialog(null, "That wrong");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5: // Адрес магазина
                    try{
                        for (Shop shop : shops){
                            if (products.get(rowIndex).getIdShop() == shop.getId()){
                                shop.setAddress((String) aValue);
                            }
                        }
                        InDataBaseShopRepository.Conn();
                        InDataBaseShopRepository dBs = new InDataBaseShopRepository();
                        dBs.updateAddressByid((String) aValue, products.get(rowIndex).getIdShop());
                        FindLongAndLat.findLongitudeAndLatitudeById(shops, products.get(rowIndex).getIdShop());
                        InDataBaseShopRepository.CloseDB();
                    }catch (NumberFormatException ex){
                        JOptionPane.showMessageDialog(null, "Wrong format");
                    }catch (IndexOutOfBoundsException ex){
                        JOptionPane.showMessageDialog(null, "That wrong");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    public void changeData(ArrayList<ProductDTO> products, ArrayList<Shop> shops) {
        this.products = products;
        this.shops = shops;
        if (products == null){
            sizeRow = 1;
        }else {
            sizeRow = products.size();
        }
        this.fireTableDataChanged();
    }

    public void deleteRow(int selectedRow) {
        this.products.remove(selectedRow);
        sizeRow -= 1;
        this.fireTableDataChanged();
    }
    public ArrayList<Product> getData(){
        ArrayList<Product> resultProducts = new ArrayList<>();
        for (ProductDTO productDTO : products){
            resultProducts.add(productDTO.toProduct());
        }
        return resultProducts;
    }
}
