package com.kaza.kazaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class activityTelaPrincipal extends AppCompatActivity {
    private Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        iniciarComponentes();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(activityTelaPrincipal.this, activityTelaInicial.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void iniciarComponentes(){
        btnLogout = findViewById(R.id.btnLogout);
    }
}