package com.andrew.codingtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import com.andrew.codingtest.data.Category;
import com.andrew.codingtest.data.Pass;
import com.andrew.codingtest.data.PassType;
import com.andrew.codingtest.data.Product;
import com.andrew.codingtest.data.Response;
import com.andrew.codingtest.view.ProductAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public String publicURL = "https://code-test.migoinc-dev.com/status";
    public String privateURL = "http://192.168.2.2/status";
    public Gson gson = new Gson();
    ArrayList<Product> productDayList = new ArrayList<>();
    ArrayList<Product> productHourList = new ArrayList<>();
    public LinearLayoutManager dayLayoutManager;
    public LinearLayoutManager hourLayoutManager;
    public RecyclerView dayPassRecyclerView;
    public RecyclerView hourPassRecyclerView;
    ProductAdapter dayAdapter, hourAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_AppCompat_Light);
        setContentView(R.layout.activity_main);

        // Step 1: detect network status
        detectNetWorkStatus();

        // Step 2: Create UI for Media platform
        createProductList(ReadDataFileFromJson());

        // Step 3: Build UI
        dayAdapter = new ProductAdapter(ProductAdapter.Category.DAY_PASS, productDayList);
        dayLayoutManager = new LinearLayoutManager(this);
        dayPassRecyclerView = findViewById(R.id.dayRecyclerView);
        dayPassRecyclerView.setLayoutManager(dayLayoutManager);
        dayPassRecyclerView.setAdapter(dayAdapter);
        dayAdapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
            @Override
            public void onItemClickListener(View view) {
                int position = dayPassRecyclerView.getChildAdapterPosition(view);

                // Reset hour adapter, due to Day/Hour pass one can choose one pass only
                hourAdapter.resetItem();
                hourAdapter.notifyDataSetChanged();

                // update choice day pass
                dayAdapter.updateItem(position);
                dayAdapter.notifyDataSetChanged();
            }
        });


        hourAdapter = new ProductAdapter(ProductAdapter.Category.HOUR_PASS, productHourList);
        hourLayoutManager = new LinearLayoutManager(this);
        hourPassRecyclerView = findViewById(R.id.hourRecyclerView);
        hourPassRecyclerView.setLayoutManager(hourLayoutManager);
        hourPassRecyclerView.setAdapter(hourAdapter);
        hourAdapter.setItemClickListener(new OnRecyclerViewItemOnClickListener() {
            @Override
            public void onItemClickListener(View view) {
                int position = hourPassRecyclerView.getChildAdapterPosition(view);

                // Reset hour adapter, due to Day/Hour pass one can choose one pass only
                dayAdapter.resetItem();
                dayAdapter.notifyDataSetChanged();

                // update choice hour pass
                hourAdapter.updateItem(position);
                hourAdapter.notifyDataSetChanged();
            }
        });

    }

    public void detectNetWorkStatus() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        TextView connectStatus = (TextView) findViewById(R.id.connectStatus);

        // Case 1: publicURL
        if (isPublicNetworkConnected()) {

            //Create URL connection
            try {
                HttpsURLConnection connection = (HttpsURLConnection) new URL(publicURL).openConnection();
                connection.setConnectTimeout(30 * 1000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setDoInput(true);
                connection.setUseCaches(false);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String text;
                StringBuilder result = new StringBuilder();
                while ((text = in.readLine()) != null)
                    result.append(text);

                in.close();
                Response response = gson.fromJson(result.toString(), Response.class);
                connectStatus.setText(response.getStatus() /* result.toString() */);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Case 2: privateURL
        else {
            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(privateURL).openConnection();
                connection.setConnectTimeout(30 * 1000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json");
                connection.setDoInput(true);
                connection.setUseCaches(false);

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String text;
                StringBuilder result = new StringBuilder();
                while ((text = in.readLine()) != null)
                    result.append(text);

                in.close();
                Response response = gson.fromJson(result.toString(), Response.class);
                connectStatus.setText(response.getStatus() /* result.toString() */);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean isPublicNetworkConnected() {
        // Detect network status
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            network = cm.getActiveNetwork();
        } else
            return true;

        NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);

        if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                return true;
            }
        }

        return true;
    }

    public void createProductList(Category category) {
        // Case 1: Feed data from Json file
        int size = category.getCategory().size();

        PassType passType;
        for (int i = 0; i < size;  i ++) {
            passType = category.getCategory().get(i);

            // Day List
            if (passType.getPassType() == 0) {
                int passCount = passType.getList().size();
                Pass pass;
                for (int j = 0; j < passCount; j ++) {
                    pass = passType.getList().get(j);
                    productDayList.add(new Product(Product.ProductType.DAY_PASS,
                            new Product.PassType(pass.getName(), pass.getRange(), pass.getPrice())));
                }
            } else {
                // Hour List
                int passCount = passType.getList().size();
                Pass pass;
                for (int j = 0; j < passCount; j ++) {
                    pass = passType.getList().get(j);
                    productHourList.add(new Product(Product.ProductType.HOUR_PASS,
                            new Product.PassType(pass.getName(), pass.getRange(), pass.getPrice())));
                }
            }
        }

        // Case 2: Hard code data
//        // Create Day pass
//        productDayList.add(new Product(Product.ProductType.DAY_PASS, new Product.PassType("1 DAY PASS", 1, 2000)));
//        productDayList.add(new Product(Product.ProductType.DAY_PASS, new Product.PassType("3 DAY PASS", 3, 5000)));
//        productDayList.add(new Product(Product.ProductType.DAY_PASS, new Product.PassType("7 DAY PASS", 7, 10000)));
//
//        // Create Hour pass
//        productHourList.add(new Product(Product.ProductType.HOUR_PASS, new Product.PassType("1 HOUR PASS", 1, 500)));
//        productHourList.add(new Product(Product.ProductType.HOUR_PASS, new Product.PassType("3 HOUR PASS", 3, 700)));
//        productHourList.add(new Product(Product.ProductType.HOUR_PASS, new Product.PassType("5 HOUR PASS", 5, 850)));
//        productHourList.add(new Product(Product.ProductType.HOUR_PASS, new Product.PassType("8 HOUR PASS", 8, 1000)));
    }

    public String readFromAssets(String fileName) {
        String jsonOutput = null;

        try {
            InputStream inputStream = getAssets().open(fileName);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonOutput = new String(buffer, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonOutput;
    }

    public Category ReadDataFileFromJson() {
        String result = readFromAssets("data.json");
        TypeToken<Category> typeToken = new TypeToken<Category>() {};
        return gson.fromJson(result, typeToken.getType());
    }
}
