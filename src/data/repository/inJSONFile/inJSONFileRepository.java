package data.repository.inJSONFile;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.entity.*;
import domain.port.ProductFromJSONRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.List;


import java.io.File;



public class inJSONFileRepository implements ProductFromJSONRepository {
    File currentFile;
    ObjectMapper objectMapper;

    public inJSONFileRepository(String path, String fileName) throws IOException {
        currentFile = new File(path, fileName);
    }
    public List<ProductFromJSON> readAllProducts() throws IOException {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<ProductFromJSON> productList = objectMapper.readValue(currentFile, new TypeReference<>(){});
        return productList;
    }

    @Override
    public Product create(Product product) {
        return null;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public void update(Product product) {

    }

    @Override
    public Product getProductByID(int id) {
        return null;
    }

    @Override
    public boolean deleteProductByID(int id) {
        return false;
    }
}
