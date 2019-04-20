package com.example.oneanothercookbook;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static String APPID = "C0A895B0-EF11-396B-FFD1-6E65A8D69400";
    private static String APIKEY = "3286B362-42BA-A61A-FF75-816FE646FA00";

    ArrayList<ReceptBluda> spisokReceptov;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Backendless.initApp( this, APPID, APIKEY );

        //TODO: переделать под загрузку из Интернета

        spisokReceptov = new ArrayList<ReceptBluda>();

        ReceptBluda recept = new ReceptBluda("Омлет","Описание","Описание");
        recept.setFoto("omlet.png");

        ArrayList<IngredientRecepta> ingredients = new ArrayList<IngredientRecepta>();

        IngredientRecepta ingredient = new IngredientRecepta("Яйцо куриное",2.0,"шт.");
        ingredients.add(ingredient);
        ingredient = new IngredientRecepta("Растительное масло",20.0,"мл");
        ingredients.add(ingredient);
        ingredient = new IngredientRecepta("Зелень");
        ingredients.add(ingredient);
        ingredient = new IngredientRecepta("Соль, перец");
        ingredients.add(ingredient);
        recept.setIngredienty(ingredients);

        ArrayList<EtapGotovki> etapy = new ArrayList<EtapGotovki>();

        EtapGotovki etap = new EtapGotovki("Налить растительное масло на сковороду");
        etapy.add(etap);
        etap = new EtapGotovki("Разбить яйца в сковороду, посолить и добавить зелень");
        etapy.add(etap);
        etap = new EtapGotovki("Жарить 2 минуты");
        etapy.add(etap);
        recept.setEtapy(etapy);

        spisokReceptov.add(recept);

        //TODO: Сохранить данные в облаке

        new SaveCookBook().execute(recept);




        //
//        ArrayList<String> nazvaniia = new ArrayList<String>();
//
//        for (ReceptBluda rb: spisokReceptov){
//            nazvaniia.add(rb.getNazvanie());
//        }

        //Gson gson = new Gson();
        //MyClass myClass = gson.fromJson(jsonString, MyClass.class);

        // находим список
        ListView spisokView = (ListView) findViewById(R.id.spisok);


        // создаем адаптер
        ArrayAdapter<ReceptBluda> adapter = new ArrayAdapter<ReceptBluda>(this,android.R.layout.simple_list_item_1, spisokReceptov);
        // присваиваем адаптер списку
        spisokView.setAdapter(adapter);

        spisokView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //создаем j,]trn lkz cthbfkkbpfwbb
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                //получаем объект-рецепт, на который нажал пользователь
                ReceptBluda recept = (ReceptBluda) ( (ListView) findViewById(R.id.spisok) ).getItemAtPosition(position);

                Log.d("recept", recept.toString());
                //сериализация объекта в строку json
                String receptJson = gson.toJson(recept);

                //cоздаем Intent для перехода в Activity ReceptInfo
                Intent i = new Intent(MainActivity.this,ReceptInfo.class);

                Log.d("json", receptJson);

                //добовляем в Intent данные(сериализованные json recept)
                i.putExtra("receptJson",receptJson);

                startActivity(i);
                //Log.d(LOG_TAG, "itemClick: position = " + position + ", id = "+ id);
            }
        });

    }

    private class SaveCookBook extends AsyncTask<ReceptBluda,Object,Object>{

        @Override
        protected Object doInBackground(ReceptBluda... receptBludas) {

            return null;
        }
    }
}
