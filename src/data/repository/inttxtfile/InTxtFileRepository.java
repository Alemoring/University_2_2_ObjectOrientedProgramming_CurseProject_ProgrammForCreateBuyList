package data.repository.inttxtfile;

import domain.entity.Product;
import domain.entity.Shop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class InTxtFileRepository {
    private static File file;
    private static String name;
    public static boolean setFilename(File name, String dir){
        if (name != null){
            file = new File(dir, name.getName());
            InTxtFileRepository.name = String.valueOf(name);
        }else{
            file = new File(dir, "buyList.txt");
            InTxtFileRepository.name = String.valueOf("buyList.txt");
        }
        try {
            if (file.exists()){
                System.out.println("File exist");
            } else{
                file.createNewFile();
                System.out.println("file create");
            }
            return true;
        }catch (IOException e){
            System.out.println("Fail with create the file " + e);
            return false;
        }
    }

    public static void writeArrayToFile(ArrayList<Product> products, ArrayList<Shop> shops) {
        try{
            FileWriter writer = new FileWriter(name);
            int num = 1;
            for (Product product : products) {
                for (Shop shop : shops){
                    if (product.getIdShop() == shop.getId()){
                        writer.write(num + ") Name: " + product.getName() + "; Price: " + (product.getPrice() / 100) + "; Shop name: " + shop.getName() + "; Shop Address: " + shop.getAddress() + ";\n");
                        num++;
                        break;
                    }
                }
            }
            writer.close();
            System.out.println("Данные успешно добавлены");
        }catch (FileNotFoundException e){
            System.out.println("Такого файла не существует");
        }
        catch (IOException e){
            System.out.println("Что-то пошло не так");
        }
    }
}
