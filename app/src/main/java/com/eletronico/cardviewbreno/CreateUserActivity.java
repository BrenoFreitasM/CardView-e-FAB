package com.eletronico.cardviewbreno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUserActivity extends AppCompatActivity {

    private EditText usuarioedit;
    private EditText senhaedit;
    private EditText confirmacaoSenhaedit;
    private Button botaosalvar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        carregarWidgets();
        salvarAoClicar();

    }

    private void carregarWidgets() {
        this.usuarioedit = findViewById(R.id.create_user_edt_user);
        this.senhaedit = findViewById(R.id.create_user_edt_password);
        this.confirmacaoSenhaedit = findViewById(R.id.create_user_edt_confirm_password);
        this.botaosalvar = findViewById(R.id.create_user_btn_save);
    }

    private void salvarAoClicar() {
        this.botaosalvar.setOnClickListener(view -> {
            try {
               criarNovoUsuario();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    private void criarNovoUsuario() {
        esconderTeclado();

        if(camposVazios()){
            Toast.makeText(this, R.string.txt_field_required, Toast.LENGTH_SHORT).show();
            this.usuarioedit.requestFocus();
            return;
        }
        if (!senhasSaoAsMesmas()) {
            Toast.makeText(this, R.string.txt_create_user_password_not_the_same, Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(() -> {
            SQLiteHelper db = SQLiteHelper.getInstance(this);
            long id = db.criarUsuario(usuarioedit.getText().toString(), senhaedit.getText().toString());
            if (id > 0 ) {
                runOnUiThread(() -> Toast.makeText(this, R.string.txt_create_user_created_successful, Toast.LENGTH_SHORT).show());
            } else {
                runOnUiThread(() ->Toast.makeText(this, R.string.txt_create_user_not_created, Toast.LENGTH_SHORT).show());
            }
        }).start();
        finish();

    }

    private void esconderTeclado() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usuarioedit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(senhaedit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(confirmacaoSenhaedit.getWindowToken(), 0);
    }

    private boolean camposVazios() {
        return this.usuarioedit.getText().toString().isEmpty()
                || this.senhaedit.getText().toString().isEmpty()
                || this.confirmacaoSenhaedit.getText().toString().isEmpty();
    }

    private boolean senhasSaoAsMesmas() {
        return this.senhaedit.getText().toString().equals(this.confirmacaoSenhaedit.getText().toString());
    }
}