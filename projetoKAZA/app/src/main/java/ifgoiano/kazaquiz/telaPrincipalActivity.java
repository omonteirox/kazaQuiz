package ifgoiano.kazaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class telaPrincipalActivity extends AppCompatActivity {

    TextView alternativa_A, alternativa_B, alternativa_C, alternativa_D, pergunta, acertos, erros, restante;
    Button btnProximaQuestao;
    ConstraintLayout cardPergunta,cardA,cardB,cardC,cardD;
    int contadorCerto = 0;
    int contadorErrado = 0;
    int index = 0;
    Handler handler = new Handler();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    modeloQuestao perguntaModelo = new modeloQuestao();
    List<modeloQuestao> listaPerguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        listaPerguntas = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_principal);
        inicializarComponentes();
        db.collection("Pergunta").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<modeloQuestao> teste = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        perguntaModelo = document.toObject(modeloQuestao.class);
                        listaPerguntas.add(perguntaModelo);
                        teste.add(perguntaModelo);
                    }
                    shuffle(listaPerguntas);
                    inicializarDados();
                    restante.setText("" + listaPerguntas.size());
                } else {
                    Log.d("Teste", String.valueOf(task.getException()));
                }
            }
        });


    }
    public static<T> void shuffle(List<T> list)
    {
        Random random = new Random();
        for (int i = list.size() - 1; i >= 1; i--)
        {
            int j = random.nextInt(i + 1);
            T obj = list.get(i);
            list.set(i, list.get(j));
            list.set(j, obj);
        }
    }
    public void efetuarLogout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(telaPrincipalActivity.this, telaInicialActivity.class);
        startActivity(intent);
        finish();
    }
    public void inicializarDados(){
        pergunta.setText(listaPerguntas.get(index).Pergunta);
        alternativa_A.setText(listaPerguntas.get(index).A);
        alternativa_B.setText(listaPerguntas.get(index).B);
        alternativa_C.setText(listaPerguntas.get(index).C);
        alternativa_D.setText(listaPerguntas.get(index).D);
    }
    public void inicializarComponentes(){
        alternativa_A = findViewById(R.id.alternativaQuestaoA);
        alternativa_B = findViewById(R.id.alternativaQuestaoB);
        alternativa_C = findViewById(R.id.alternativaQuestaoC);
        alternativa_D = findViewById(R.id.alternativaQuestaoD);
        pergunta = findViewById(R.id.nomeQuestao);
        cardA = findViewById(R.id.cardAlternativaQuestaoA);
        cardB = findViewById(R.id.cardAlternativaQuestaoB);
        cardC = findViewById(R.id.cardAlternativaQuestaoC);
        cardD = findViewById(R.id.cardAlternativaQuestaoD);
        cardPergunta = findViewById(R.id.cardNomeQuestao);
        acertos = findViewById(R.id.txtAcertos);
        erros = findViewById(R.id.txtErros);
        restante = findViewById(R.id.txtRestantes);
    }
    public void questaoCorreta(ConstraintLayout cardA){
        cardA.setBackgroundColor(getResources().getColor(R.color.alternativa_correta));
                contadorCerto++;
                index++;
                resetarEIniciar();
    }
    public void Restante(){
        int questoesRestantes = (listaPerguntas.size() - contadorCerto) - contadorErrado;
        restante.setText("" + questoesRestantes);
    }

    public void resetarEIniciar(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                inicializarDados();
                resetarCor();
                acertos.setText("" + contadorCerto);
                erros.setText("" + contadorErrado);
                Restante();
            }
        },300);
    }
    public void questaoErrada(ConstraintLayout cardA){
        cardA.setBackgroundColor(getResources().getColor(R.color.alternativa_errada));
                contadorErrado++;
                if (index < listaPerguntas.size()-1){
                    index++;
                    listaPerguntas.get(index);
                    resetarEIniciar();
                }else{
                    GanhouJogo();
                }
    }

    private void GanhouJogo() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(telaPrincipalActivity.this,ganhouActivity.class);
                intent.putExtra("corretas",contadorCerto);
                intent.putExtra("erradas",contadorErrado);
                intent.putExtra("totalQuestoes", listaPerguntas.size());
                startActivity(intent);
            }
        },500);

    }
    public void resetarCor(){
        cardA.setBackgroundColor(getResources().getColor(R.color.cor_cards_Padrao));
        cardB.setBackgroundColor(getResources().getColor(R.color.cor_cards_Padrao));
        cardC.setBackgroundColor(getResources().getColor(R.color.cor_cards_Padrao));
        cardD.setBackgroundColor(getResources().getColor(R.color.cor_cards_Padrao));
    }

    public void opcaoAClicada(View view) {
        if(listaPerguntas.get(index).getA().equals(listaPerguntas.get(index).getResposta())){
            if(index < listaPerguntas.size() -1 ){
                questaoCorreta(cardA);
            }else{
                cardA.setBackgroundColor(getResources().getColor(R.color.alternativa_correta));
                GanhouJogo();
            }
        }else{
            questaoErrada(cardA);
        }
    }
    public void opcaoBClicada(View view) {
        if(listaPerguntas.get(index).getB().equals(listaPerguntas.get(index).getResposta())){

            if(index < listaPerguntas.size() -1 ){
                questaoCorreta(cardB);
            }else{
                cardB.setBackgroundColor(getResources().getColor(R.color.alternativa_correta));
                GanhouJogo();
            }
        }else{
            questaoErrada(cardB);

        }
    }
    public void opcaoCClicada(View view) {
        if(listaPerguntas.get(index).getC().equals(listaPerguntas.get(index).getResposta())){
            if(index < listaPerguntas.size() -1 ){
                questaoCorreta(cardC);
            }else{
                cardC.setBackgroundColor(getResources().getColor(R.color.alternativa_correta));
                GanhouJogo();
            }
        }else{
            questaoErrada(cardC);

        }
    }
    public void opcaoDClicada(View view) {
        if(listaPerguntas.get(index).getD().equals(listaPerguntas.get(index).getResposta())){

            if(index < listaPerguntas.size() -1 ){
                questaoCorreta(cardD);
            }else{
                cardD.setBackgroundColor(getResources().getColor(R.color.alternativa_correta));
                GanhouJogo();
            }
        }else{
            questaoErrada(cardD);

        }
    }
}