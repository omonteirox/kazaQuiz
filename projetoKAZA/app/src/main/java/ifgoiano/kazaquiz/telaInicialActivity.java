package ifgoiano.kazaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class telaInicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

    }
    public void telaLogin(View view){
        Intent intent = new Intent(this, telaFazerLoginActivity.class);
        startActivity(intent);
    }
    public void telaCadastro(View view){
        Intent intent = new Intent(this, telaFazerCadastro.class);
        startActivity(intent);
    }
}