package ifgoiano.kazaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class escolherOpcaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_opcao);
    }

    public void Jogar(View view) {
        Intent intent = new Intent(this, telaPrincipalActivity.class);
        startActivity(intent);
    }

    public void adicionarPergunta(View view) {
        Intent intent = new Intent(this, adicionarPerguntaActivity.class);
        startActivity(intent);
    }
}