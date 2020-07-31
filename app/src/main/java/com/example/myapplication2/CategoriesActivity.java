package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import io.realm.Realm;
import io.realm.RealmResults;

public class CategoriesActivity extends AppCompatActivity {

    private ChipGroup chipGroup;
    private Button buttonSelectCategories;
    private boolean isSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        chipGroup = (ChipGroup)findViewById(R.id.chipGroup);
        buttonSelectCategories = (Button) findViewById(R.id.buttonSelectCategories);


        buttonSelectCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getDefaultInstance();
                for(int i = 0; i < chipGroup.getChildCount(); i++){
                    Chip chip = (Chip) chipGroup.getChildAt(i);
                    if(chip.isChecked()){
                        realm.beginTransaction();
                        realm.copyToRealm(new CategoryEnt(String.valueOf(i)));
                        realm.commitTransaction();
                        isSelected = true;

                    }
                }
                if(isSelected){
                    finish();
                }else{
                    Toast.makeText(CategoriesActivity.this, "Выберите хотя бы одну категорию", Toast.LENGTH_SHORT).show();
                }
            }
        });







    }
}
