package ifgoiano.kazaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class telaFazerLoginActivity extends AppCompatActivity {
    private EditText txtEmail,txtSenha;
    private Button btnLogin;
    Handler handler = new Handler();
    String[] msgSnackBar = {"Preencha todos os campos","Login Efetuado com sucesso"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fazer_login);
        iniciarCompnentes();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                if(email.isEmpty()|| senha.isEmpty()){
                    Snackbar snackbar = Snackbar.make(v,msgSnackBar[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();

                }else {
                    AutenticarUsuario(v);
                }
            }
        });
    }

    private void AutenticarUsuario(View v){
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                }else{
                    String err;
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        err = "Erro ao autenticar usuário";
                        Snackbar snackbar = Snackbar.make(v,err,Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(Color.RED);
                        snackbar.setTextColor(Color.WHITE);
                        snackbar.show();
                    }
                }
            }
        });
    }
    protected void onStart(){
        super.onStart();
        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();
        if(usuarioAtual != null){
            telaPrincipal();
        }
    }
    private void telaPrincipal(){
        Intent intent = new Intent(telaFazerLoginActivity.this,escolherOpcaoActivity.class);
        startActivity(intent);
        finish();
    }
    private void iniciarCompnentes(){
        txtEmail = findViewById(R.id.txtLabelEmail);
        txtSenha = findViewById(R.id.txtLabelSenha);
        btnLogin = findViewById(R.id.btnLogin);
    }
}