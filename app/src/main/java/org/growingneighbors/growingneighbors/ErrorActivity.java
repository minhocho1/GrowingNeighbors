package org.growingneighbors.growingneighbors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ErrorActivity extends AppCompatActivity {

    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras().getString("Error") != null) {
            error = (TextView)findViewById(R.id.error);
            error.setText(getIntent().getExtras().getString("Error"));
        }
    }

    public void onRetry(View v) {
        this.redirect();
    }

    public void onExit(View v) {
        finish(); // exit the app
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.redirect();
    }

    private void redirect() {
        if (Common.isConnectionAvailable(ErrorActivity.this)) {
            Intent home = new Intent(ErrorActivity.this, MainActivity.class);
            startActivity(home);
            finish();
        } else {
            Toast.makeText(this, "Please Check Internet Connection..", Toast.LENGTH_SHORT).show();
        }
    }
}
