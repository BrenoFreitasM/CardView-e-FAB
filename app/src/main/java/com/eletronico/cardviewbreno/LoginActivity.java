package com.eletronico.cardviewbreno;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText usuarioedit;
    private EditText senhaedit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loadWidgets();
    }

    private void loadWidgets() {
        this.usuarioedit = findViewById(R.id.login_edt_user);
        this.usuarioedit.requestFocus();
        this.senhaedit = findViewById(R.id.login_edt_password);
        Button btnlogin = findViewById(R.id.login_btn_login);
        btnlogin.setOnClickListener(view -> login());
        TextView txtNewAccount = findViewById(R.id.login_txt_new_account);
        txtNewAccount.setOnClickListener(view -> createNewAccount());
    }

    private void login() {
        hideKeyboard();
        if (this.isFieldsEmpty()) {
            Toast.makeText(this, R.string.txt_field_required, Toast.LENGTH_SHORT).show();
            this.usuarioedit.requestFocus();
            return;
        }
        if (!this.authenticate()) {
            Toast.makeText(this, R.string.txt_credentials_wrong, Toast.LENGTH_SHORT).show();
            this.usuarioedit.requestFocus();
            return;
        }
        startActivity(new Intent(this, DashboardActivity.class));
    }

    private void createNewAccount() { startActivity(new Intent(this, CreateUserActivity.class));}

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(usuarioedit.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(senhaedit.getWindowToken(), 0);
    }

    private boolean isFieldsEmpty() {
        return this.usuarioedit.getText().toString().isEmpty()
                || this.senhaedit.getText().toString().isEmpty();
    }

    private boolean authenticate() {
        String user = this.usuarioedit.getText().toString();
        String password = this.senhaedit.getText().toString();
        SQLiteHelper db = SQLiteHelper.getInstance(this);
        return db.authenticateUser(user, password);
    }

    private void cleanFields() {
        this.usuarioedit.setText("");
        this.senhaedit.setText("");
        this.usuarioedit.requestFocus();
    }
}
