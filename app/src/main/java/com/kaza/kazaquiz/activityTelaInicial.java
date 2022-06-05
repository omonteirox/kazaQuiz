package com.kaza.kazaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class activityTelaInicial extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
    }
    public void telaLogin(View view){
        Intent intent = new Intent(this, activityLogin.class);
        startActivity(intent);
    }
    public void telaCadastro(View view){
        Intent intent = new Intent(this, activityCadastro.class);
        startActivity(intent);
    }
    public void recuperarSenha(View v){
        Intent intent = new Intent(this, activityRecuperarSenha.class);
        startActivity(intent);
    }
}