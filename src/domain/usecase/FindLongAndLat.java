package domain.usecase;

import data.repository.indatabase.InDataBaseShopRepository;
import domain.entity.Shop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FindLongAndLat {
    static URL url;

    public FindLongAndLat() throws MalformedURLException, UnsupportedEncodingException {
    }

    public static void findLongAndLatAll(List<Shop> shops) throws IOException {
        for (Shop shop : shops) {
            String address = shop.getAddress();
            if (!address.isEmpty()) {
                if (shop.getLongitude().isEmpty()) {
                    address = address.replace(",", "");
                    address = address.replace("г.", "");
                    address = address.replace("ул.", "");
                    address = address.replace("д.", "");
                    String start = "https://geocode-maps.yandex.ru/1.x/?apikey=539e412b-acd3-4c1e-bc25-93f3b98ec947&geocode=";
                    for (int i = 0; i < address.length(); i++) {
                        String word = "";
                        while (i < address.length() && address.charAt(i) != ' ') {
                            word += URLEncoder.encode(String.valueOf(address.charAt(i)), "UTF-8");
                            i++;
                        }
                        if (i < address.length()) {
                            word += "+";
                        }
                        start += word;
                    }
                    start += "&lang=ru_RU&format=json";
                    url = new URL(start);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    int responseCode = connection.getResponseCode();
                    System.out.println(responseCode);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();
                    int index = response.indexOf("lowerCorner");
                    if (index > 0) {
                        String latAndlong = response.substring(index + 14, index + 33);
                        String[] ltAndlg = latAndlong.split(" ");
                        shop.setLongitude(ltAndlg[0]);
                        shop.setLatitude(ltAndlg[1]);
                    }else{
                        shop.setLongitude("null");
                    }
                }
            }
        }
    }
    public static void findLongitudeAndLatitudeById(List<Shop> shops, int id) throws IOException, SQLException, ClassNotFoundException {
        String address = "";
        for (Shop shop : shops){
            if (shop.getId() == id){
                address = shop.getAddress();
            }
        }
        address = address.replace(",", "");
        address = address.replace("г.", "");
        address = address.replace("ул.", "");
        address = address.replace("д.", "");
        String start = "https://geocode-maps.yandex.ru/1.x/?apikey=539e412b-acd3-4c1e-bc25-93f3b98ec947&geocode=";
        for (int i = 0; i < address.length(); i++) {
            String word = "";
            while (i < address.length() && address.charAt(i) != ' ') {
                word += URLEncoder.encode(String.valueOf(address.charAt(i)), "UTF-8");
                i++;
            }
            if (i < address.length()) {
                word += "+";
            }
            start += word;
        }
        start += "&lang=ru_RU&format=json";
        url = new URL(start);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        int index = response.indexOf("lowerCorner");
        String[] ltAndlg = new String[2];
        if (index > 0) {
            String latAndlong = response.substring(index + 14, index + 33);
            ltAndlg = latAndlong.split(" ");
            for (Shop shop : shops){
                if (shop.getId() == id){
                    shop.setLongitude(ltAndlg[0]);
                    shop.setLatitude(ltAndlg[1]);
                }
            }
        }else{
            for (Shop shop : shops){
                if (shop.getId() == id){
                    shop.setLongitude("null");
                    shop.setLatitude("null");
                }
            }
        }
        InDataBaseShopRepository.Conn();
        InDataBaseShopRepository dBs = new InDataBaseShopRepository();
        for (Shop shop : shops){
            if (shop.getId() == id){
                dBs.updateLgAndLt(shop);
            }
        }
        InDataBaseShopRepository.CloseDB();
    }
    public static String[] findLongitudeAndLatitudeByAddress(String address) throws IOException {
        address = address.replace(",", "");
        address = address.replace("г.", "");
        address = address.replace("ул.", "");
        address = address.replace("д.", "");
        String start = "https://geocode-maps.yandex.ru/1.x/?apikey=539e412b-acd3-4c1e-bc25-93f3b98ec947&geocode=";
        for (int i = 0; i < address.length(); i++) {
            String word = "";
            while (i < address.length() && address.charAt(i) != ' ') {
                word += URLEncoder.encode(String.valueOf(address.charAt(i)), "UTF-8");
                i++;
            }
            if (i < address.length()) {
                word += "+";
            }
            start += word;
        }
        start += "&lang=ru_RU&format=json";
        url = new URL(start);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        System.out.println(responseCode);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        int index = response.indexOf("lowerCorner");
        String[] ltAndlg = new String[2];
        if (index > 0) {
            String latAndlong = response.substring(index + 14, index + 33);
            ltAndlg = latAndlong.split(" ");
        }else{
            ltAndlg[0] = "null";
            ltAndlg[1] = "null";
        }
        return ltAndlg;
    }
}
