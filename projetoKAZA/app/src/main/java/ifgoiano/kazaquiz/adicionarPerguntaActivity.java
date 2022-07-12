package ifgoiano.kazaquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class adicionarPerguntaActivity extends AppCompatActivity {
    Handler handler = new Handler();
    private EditText txtPergunta,txtAlternativaA,txtAlternativaB,txtAlternativaC,txtAlternativaD,txtResposta;
    private Button btnCriarQuestao;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_pergunta);
        inicializarComponentes();

    }

    public void criarQuestao(View view) {
        Snackbar snackbar = Snackbar.make(view,"Pergunta Adicionada com Sucesso",Snackbar.LENGTH_SHORT);
        snackbar.setBackgroundTint(Color.GREEN);
        snackbar.setTextColor(Color.WHITE);
        snackbar.show();
        criarPergunta();
        delay();
    }
    public void delay(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(adicionarPerguntaActivity.this,escolherOpcaoActivity.class);
                startActivity(intent);
            }
        },1000);
    }
    public void inicializarComponentes(){
        txtPergunta = findViewById(R.id.txtPergunta);
        txtAlternativaA = findViewById(R.id.txtAlternativaA);
        txtAlternativaB = findViewById(R.id.txtAlternativaB);
        txtAlternativaC = findViewById(R.id.txtAlterenativaC);
        txtAlternativaD = findViewById(R.id.txtAlternativaD);
        txtResposta = findViewById(R.id.txtCorreta);

    }
    public Object criarPergunta(){
        Map<String, Object> questao = new HashMap<>();
        String perguntaText = txtPergunta.getText().toString();
        String respostaTxt = txtResposta.getText().toString();
        String aText = txtAlternativaA.getText().toString();
        String bText = txtAlternativaB.getText().toString();
        String cText = txtAlternativaC.getText().toString();
        String dText = txtAlternativaD.getText().toString();

        questao.put("Pergunta",perguntaText);
        questao.put("Resposta",respostaTxt);
        questao.put("A",aText);
        questao.put("B",bText);
        questao.put("C",cText);
        questao.put("D",dText);
        DocumentReference perguntaRef = db.collection("Pergunta").document();
        perguntaRef.set(questao);
        return questao;
    }
}