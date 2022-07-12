package ifgoiano.kazaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ganhouActivity extends AppCompatActivity {
    TextView pontuacao ;
    int corretas,erradas,totalQuestoes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganhou);
        inicializarElementos();
        corretas = getIntent().getIntExtra("corretas",0);
        erradas = getIntent().getIntExtra("erradas",0);
        totalQuestoes = getIntent().getIntExtra("totalQuestoes",0);
        pontuacao.setText(corretas + "/" + totalQuestoes);
    }

    public void efetuarLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ganhouActivity.this, telaInicialActivity.class);
        startActivity(intent);
        finish();
    }
    public void voltarInicio(View view){
        Intent intent = new Intent(ganhouActivity.this, telaPrincipalActivity.class);
        startActivity(intent);
        finish();
    }
    public void inicializarElementos(){
        pontuacao = findViewById(R.id.txtFinal);
    }
}