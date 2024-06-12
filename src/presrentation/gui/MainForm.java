package presrentation.gui;

import data.repository.indatabase.InDataBaseProductRepository;
import data.repository.indatabase.InDataBaseShopRepository;
import data.repository.inttxtfile.InTxtFileRepository;
import data.storage.dto.ProductDTO;
import domain.entity.Product;
import domain.entity.Shop;
import domain.usecase.FindLongAndLat;
import domain.usecase.FindMinimalPrice;
import domain.usecase.FindingTheShortestPath;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MainForm extends JFrame {
    private JFrame frame;
    private JButton btnAddRow;
    private JButton btnDeleteRow;
    private JButton btnSearch;
    private JButton btnShowAllTheProducts;
    private JButton btnSort;
    private JButton btnAddToBuyList;
    private JButton btnShowBuyList;
    private JButton btnGenerateBuyList;
    private JMenuBar menuBar;
    private JMenu helpMenu;
    private JMenuItem helpItem;
    private JMenu aboutMenu;
    private JMenuItem aboutProgramItem;
    private JMenu fileMenu;
    private JMenuItem openItem;
    private JTable table;
    private MyTableModel tableModel;
    private JPanel mainPanel;
    private JPanel eastPanel;
    private ArrayList<Product> products;
    private ArrayList<Product> productsInBuyList = new ArrayList<>();
    private ArrayList<Shop> shops;
    public MainForm(){
        super();
        initialization();
        start();
    }
    public void initialization(){
        frame = new JFrame("App for Economy Buy");
        frame.setSize(1200, 800);
        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //frame.setIconImage();

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));

        menuBar = new JMenuBar();

        aboutMenu = new JMenu("About");
        aboutProgramItem = new JMenuItem("About program");
        aboutProgramItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainForm.this, "This program was created by 2nd year student " +
                        "Alexander Morgunov in the direction of ISTB-22-1.\n The program is designed to view, add, delete purchases in table form" +
                        "\n version 0.1");
            }
        });

        helpMenu = new JMenu("Help");
        helpItem = new JMenuItem("Help notes");
        helpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainForm.this, "\"File\" => \"Save File\": Saves data to the selected file\n" +
                        "\"File\" => \"Open File\": Loads data from a file\n" +
                        "\"About Program\": Tells about the program\n" +
                        "\"Add Row\": Adds a new row to the table\n" +
                        "\"Delete Row\": Deletes the selected row in the table");
            }
        });

        fileMenu = new JMenu("DataBase");

        openItem = new JMenuItem("Open");
        openItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    InDataBaseProductRepository.Conn();
                    InDataBaseProductRepository dBp = new InDataBaseProductRepository();
                    products = (ArrayList<Product>) dBp.getAllProducts();
                    InDataBaseProductRepository.CloseDB();
                    InDataBaseShopRepository.Conn();
                    InDataBaseShopRepository dBs = new InDataBaseShopRepository();
                    shops = (ArrayList<Shop>) dBs.getAllShops();
                    InDataBaseShopRepository.CloseDB();
                    loadTable();
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        if(products != null){
            for (Product product : products){
                productDTOS.add(new ProductDTO(product, shops));
            }
        }
        tableModel = new MyTableModel(productDTOS, shops);

        table = new JTable(tableModel);

        btnAddRow = new JButton("Add new product");
        btnAddRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // name, price, date_time, id_shop
                String name = readLine("Enter product name");
                double price = readDoubleValue("Enter the cost of the product");
                Date timestamp = new Date(readLine("Enter the date purchase"));
                Shop shop = (Shop) JOptionPane.showInputDialog(
                        MainForm.this,
                        "Select product quality: ",
                        "Adding a new product",
                        JOptionPane.QUESTION_MESSAGE,
                        null, shops.toArray(), shops.get(0));
                Product product = new Product(name, (int) price * 100, timestamp.getTime(), shop.getId());
                try {
                    InDataBaseProductRepository.Conn();
                    //InDataBaseProductRepository dBp = new InDataBaseProductRepository();
                    //dBp.create(product);
                    InDataBaseProductRepository.CloseDB();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                products.add(product);
                loadTable();
            }
        });


        btnDeleteRow = new JButton("Delete selected product");
        btnDeleteRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    InDataBaseProductRepository.Conn();
                    InDataBaseProductRepository dBP = new InDataBaseProductRepository();
                    //dBP.deleteProductByID(products.get(table.getSelectedRow()).getId());
                    InDataBaseProductRepository.CloseDB();
                    tableModel.deleteRow(table.getSelectedRow());
                }catch (IndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(MainForm.this, "Unselected line");
                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(MainForm.this, "Too many rows selected");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnSearch = new JButton("Search products by name");
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGenerateBuyList.setVisible(false);
                search();
            }
        });
        btnSort = new JButton("Sort products by price");
        btnSort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sort();
            }
        });
        btnShowAllTheProducts = new JButton("Show all the products");
        btnShowAllTheProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGenerateBuyList.setVisible(false);
                loadTable();
            }
        });
        btnAddToBuyList = new JButton("Add products to buy list");
        btnAddToBuyList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    productsInBuyList.add(products.get(table.getSelectedRow()));
                }catch (IndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(MainForm.this, "Unselected line");
                }catch (IllegalArgumentException ex){
                    JOptionPane.showMessageDialog(MainForm.this, "Too many rows selected");
                }
            }
        });
        btnShowBuyList = new JButton("Show buy list");
        btnShowBuyList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnGenerateBuyList.setVisible(true);
                loadTable(productsInBuyList);
            }
        });
        btnGenerateBuyList = new JButton("Generate buy list");
        btnGenerateBuyList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String home = readLine("Enter the address of home");
                String[] lgAndlt;
                try {
                    lgAndlt = FindLongAndLat.findLongitudeAndLatitudeByAddress(home);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                ArrayList<Shop> shopsInBuyList = new ArrayList<>();
                shopsInBuyList = (ArrayList<Shop>) FindingTheShortestPath.findingTheShortestPath(productsInBuyList, shops, lgAndlt);
                ArrayList<Product> resultProducts = new ArrayList<>();
                for (Shop shop : shopsInBuyList){
                    for (Product product : productsInBuyList){
                        if (shop.getId() == product.getIdShop()){
                            resultProducts.add(product);
                        }
                    }
                }
                productsInBuyList = resultProducts;
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose the directory to save buy list");
                int res = fileChooser.showOpenDialog(frame);
                if (res == JFileChooser.APPROVE_OPTION ) {
                    JOptionPane.showMessageDialog(MainForm.this,
                            "File '" + fileChooser.getSelectedFile() +
                                    " ) saved");
                    InTxtFileRepository.setFilename(fileChooser.getSelectedFile(), fileChooser.getCurrentDirectory().toString());
                    InTxtFileRepository.writeArrayToFile(productsInBuyList, shopsInBuyList);
                }
                loadTable(productsInBuyList);
            }
        });
    }
    public void start(){
        aboutMenu.add(aboutProgramItem);
        helpMenu.add(helpItem);
        fileMenu.add(openItem);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        menuBar.add(aboutMenu);

        eastPanel.add(btnAddRow);
        eastPanel.add(btnDeleteRow);
        eastPanel.add(btnSearch);
        eastPanel.add(btnSort);
        eastPanel.add(btnShowAllTheProducts);
        eastPanel.add(btnAddToBuyList);
        eastPanel.add(btnShowBuyList);
        eastPanel.add(btnGenerateBuyList);
        btnGenerateBuyList.setVisible(false);

        mainPanel.add(eastPanel, BorderLayout.WEST);
        mainPanel.add(menuBar, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table));

        frame.add(mainPanel);
        frame.setVisible(true);
    }
    public int readValue(int readedId, String message) {
        // readedID = 1 - readYear, 2 - readMonth, 3 - readDay
        int readed = -1;
        try {
            if (readedId == 1) {
                String line = JOptionPane.showInputDialog(
                        MainForm.this,
                        message);
                readed = Integer.parseInt(line);
                if (readed < 2000 || readed > 3000) {
                    readed = readValue(1, message);
                }
            } else if (readedId == 2) {
                String line = JOptionPane.showInputDialog(
                        MainForm.this,
                        message);
                readed = Integer.parseInt(line);
                if (readed < 1 || readed > 12) {
                    readed = readValue(2, message);
                }
            } else if (readedId == 3) {
                String line = JOptionPane.showInputDialog(
                        MainForm.this,
                        message);
                readed = Integer.parseInt(line);
                if (readed < 1 || readed > 31) {
                    readed = readValue(3, message);
                }
            }else if (readedId == 4) {
                String line = JOptionPane.showInputDialog(
                        MainForm.this,
                        message);
                readed = Integer.parseInt(line);
                if (readed < 1 || readed > 1000) {
                    readed = readValue(4, message);
                }
            }
        }catch (Exception e){
            if (readedId == 1) {
                JOptionPane.showMessageDialog(MainForm.this, "Invalid value entered :(\n" +
                        "Enter the year from 2000 to 3000\n" + "Try again, you will succeed");
                readed = readValue(1, message);
            }else if (readedId == 2) {
                JOptionPane.showMessageDialog(MainForm.this, "Invalid value entered :(\n" +
                        "Enter month from 1 to 12\n" + "Try again, you will succeed");
                readed = readValue(2, message);
            }else if (readedId == 3){
                JOptionPane.showMessageDialog(MainForm.this, "Invalid value entered :(\n" +
                        "Enter day from 1 to 31\n" + "Try again, you will succeed");
                readed = readValue(3, message);
            }else {
                JOptionPane.showMessageDialog(MainForm.this, "Invalid value entered :(\n" +
                        "Enter the number of months from 1 to 1000\n" + "Try again, you will succeed");
                readed = readValue(4, message);
            }
        }
        return readed;
    }
    public double readDoubleValue(String message){
        double readed = -1;
        try {
            String line = JOptionPane.showInputDialog(
                    MainForm.this,
                    message);
            readed = Double.parseDouble(line);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(MainForm.this, "Something went wrong :( \n" +
                    "Try again, you will succeed");
            readed = readDoubleValue(message);
        }
        return readed;
    }
    public String readLine(String message){
        String readed = "";
        try {
            readed = JOptionPane.showInputDialog(
                    MainForm.this,
                    message);
        }
        catch (NumberFormatException e){
            JOptionPane.showMessageDialog(MainForm.this, "Something went wrong :( \n" +
                    "Try again, you will succeed");
            readed = readLine(message);
        }catch (IllegalArgumentException ex){
            readed = readLine((message));
        }
        return readed;
    }
    public void loadTable(){
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        if (products != null){
            for (Product product : products){
                productDTOS.add(new ProductDTO(product, shops));
            }
        }
        tableModel.changeData(productDTOS, shops);
    }
    public void loadTable(ArrayList<Product> products){
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : products){
            productDTOS.add(new ProductDTO(product, shops));
        }
        tableModel.changeData(productDTOS, shops);
    }
    public void search(){
        String productName = readLine("Enter the product name");
        ArrayList<Product> resultProducts = new ArrayList<>();
        for (Product product : products){
            if (product.getName().toLowerCase().contains(productName.toLowerCase())){
                resultProducts.add(product);
            }
        }
        loadTable(resultProducts);
    }
    public void sort(){
        ArrayList<Product> resultProducts = (ArrayList<Product>) FindMinimalPrice.findMinimalPrice(tableModel.getData());
        loadTable(resultProducts);
    }
}
