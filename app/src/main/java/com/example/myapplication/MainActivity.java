package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ExecutorService executor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ProductAdapter adapter = new ProductAdapter();

        recyclerView.setAdapter(adapter);

        AppDatabase database = Room.databaseBuilder(
                getApplicationContext(), AppDatabase.class, "shop_database"
        ).build();

        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Product> products = database.productDao().getAll();
            if (products.isEmpty()) {
                database.productDao().insert(new Product("Клавиатура", 2500, 10));
                database.productDao().insert(new Product("Мышь", 1200, 15));
                database.productDao().insert(new Product("Наушники", 3400, 5));
                products = database.productDao().getAll();
            }
            List<Product> loadedProducts = products;
            runOnUiThread(() -> adapter.setProducts(loadedProducts));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executor != null) {
            executor.shutdown();
        }
    }
}
