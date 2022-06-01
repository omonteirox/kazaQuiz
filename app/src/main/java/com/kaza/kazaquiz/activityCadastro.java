package com.kaza.kazaquiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
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
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class activityCadastro extends AppCompatActivity {
    private EditText txtNome,txtEmail,txtSenha,txtDtNasc;
    private Button btnCadastrar;
    String[] msgSnackBar = {"Preencha todos os campos","Cadastro Realizado com Sucesso"};
    String usuarioID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
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
                    cadastrarUsuario(view);
                }
            }
        });
    }
    private void cadastrarUsuario(View view){
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
                }else{
                    String err;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        err = "Digite uma senha com no mínimo 6 caracteres.";
                    }
                    catch(FirebaseAuthUserCollisionException e){
                        err = "E-mail já cadastrado.";
                    }
                    catch(FirebaseAuthInvalidCredentialsException e){
                        err = "E-mail inválido";
                    }
                    catch (Exception e){
                        err = "Erro ao cadastrar usuário, tente novamente mais tarde.";
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
                Log.d("db","Sucesso ao salvar usuário no banco");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error","Erro ao salvar usuário no banco " + e.toString());
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