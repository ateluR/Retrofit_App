package com.onaar.retrofitcik;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textViewPytanie, textViewNumer;
    RadioGroup radioGroupPytania;
    RadioButton radioButtonA, radioButtonB, radioButtonC;
    Button buttonNastepne;
    List<Pytanie> pytanieZInternetu;

    int aktualnePytanie = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewPytanie = findViewById(R.id.textViewTrescPytania);
        textViewNumer = findViewById(R.id.textViewNumer);
        radioButtonA = findViewById(R.id.radioButton);
        radioButtonB = findViewById(R.id.radioButton2);
        radioButtonC = findViewById(R.id.radioButton3);
        radioGroupPytania = findViewById(R.id.RadioGroup1);
        buttonNastepne = findViewById(R.id.button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://my-json-server.typicode.com/ateluR/Pytanie_Retrofit/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JSONPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JSONPlaceholderAPI.class);
        Call<List<Pytanie>> call = jsonPlaceholderAPI.getPytania();

        call.enqueue(new Callback<List<Pytanie>>() {
            @Override
            public void onResponse(Call<List<Pytanie>> call, Response<List<Pytanie>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Błąd serwera: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                pytanieZInternetu = response.body();
                if (pytanieZInternetu != null && !pytanieZInternetu.isEmpty()) {
                    aktualnePytanie = 0;
                    wyswietlPytanie(aktualnePytanie);
                }
            }

            @Override
            public void onFailure(Call<List<Pytanie>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Błąd pobierania danych: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        buttonNastepne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pytanieZInternetu != null && aktualnePytanie < pytanieZInternetu.size() - 1) {
                    aktualnePytanie++;
                    wyswietlPytanie(aktualnePytanie);
                } else {
                    Toast.makeText(MainActivity.this, "To już ostatnie pytanie!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void wyswietlPytanie(int nr) {
        if (pytanieZInternetu != null && nr < pytanieZInternetu.size()) {
            radioGroupPytania.clearCheck();

            textViewNumer.setText("Pytanie nr " + (nr + 1));

            textViewPytanie.setText(pytanieZInternetu.get(nr).getTrescPytania());
            radioButtonA.setText(pytanieZInternetu.get(nr).getOdpA());
            radioButtonB.setText(pytanieZInternetu.get(nr).getOdpB());
            radioButtonC.setText(pytanieZInternetu.get(nr).getOdpC());
        }
    }
}
