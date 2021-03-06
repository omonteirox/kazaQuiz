package ifgoiano.kazaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class telaFazerCadastro extends AppCompatActivity {

    private EditText txtNome,txtEmail,txtSenha,txtDtNasc;
    private Button btnCadastrar;
    String[] msgSnackBar = {"Preencha todos os campos","Cadastro Realizado com Sucesso"};
    String usuarioID;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_fazer_cadastro);
        iniciarComponentes();
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = txtNome.getText().toString();
                String email = txtEmail.getText().toString();
                String senha = txtSenha.getText().toString();
                String dataNasc = txtDtNasc.getText().toString();
                if(nome.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNasc.isEmpty()){
                    Snackbar snackbar = Snackbar.make(view,msgSnackBar[0],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }else{
                    try {
                        cadastrarUsuario(view);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private void intentDelay(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(telaFazerCadastro.this,telaInicialActivity.class);
                startActivity(intent);
            }
        },1000);
    }
    private void cadastrarUsuario(View view) throws InterruptedException {
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    SalvarDadosUsuario();
                    Snackbar snackbar = Snackbar.make(view,msgSnackBar[1],Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.GREEN);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                    intentDelay();
                }else{
                    String err;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        err = "Digite uma senha com no m??nimo 6 caracteres.";
                    }
                    catch(FirebaseAuthUserCollisionException e){
                        err = "E-mail j?? cadastrado.";
                    }
                    catch(FirebaseAuthInvalidCredentialsException e){
                        err = "E-mail inv??lido";
                    }
                    catch (Exception e){
                        err = "Erro ao cadastrar usu??rio, tente novamente mais tarde.";
                    }
                    Snackbar snackbar = Snackbar.make(view,err,Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.YELLOW);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();

                }
            }
        });


    }
    private void SalvarDadosUsuario(){
        String nome = txtNome.getText().toString();
        String email = txtSenha.getText().toString();
        String dtNasc = txtDtNasc.getText().toString();
        // Iniciando DB
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome",nome);
        usuarios.put("email",email);
        usuarios.put("dataNasc", dtNasc);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db","Sucesso ao salvar usu??rio no banco");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error","Erro ao salvar usu??rio no banco " + e.toString());
            }
        });
    }
    private void iniciarComponentes(){
        txtNome = findViewById(R.id.txtLabelNome);
        txtEmail = findViewById(R.id.txtLabelEmail);
        txtSenha = findViewById(R.id.txtLabelSenha);
        txtDtNasc = findViewById(R.id.txtLabelNasc);
        btnCadastrar = findViewById(R.id.btnCadastro);
    }
}