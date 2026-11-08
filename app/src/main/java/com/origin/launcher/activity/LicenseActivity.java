package com.origin.launcher.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // --- CREAMOS LA UI POR CÓDIGO (Estilo Minecraft) ---
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(60, 250, 60, 60);
        layout.setBackgroundColor(android.graphics.Color.parseColor("#3C3C3C")); // Color tierra

        android.widget.TextView title = new android.widget.TextView(this);
        title.setText("BreakingCraft");
        title.setTextColor(android.graphics.Color.parseColor("#55FF55")); // Verde Minecraft
        title.setTextSize(32f);
        title.setGravity(android.view.Gravity.CENTER);
        layout.addView(title);

        EditText inputCode = new EditText(this);
        inputCode.setHint("Ingresa tu código VIP");
        inputCode.setTextColor(android.graphics.Color.WHITE);
        inputCode.setHintTextColor(android.graphics.Color.GRAY);
        layout.addView(inputCode);

        Button btnValidate = new Button(this);
        btnValidate.setText("JUGAR");
        btnValidate.setBackgroundColor(android.graphics.Color.parseColor("#55FF55"));
        btnValidate.setTextColor(android.graphics.Color.BLACK);
        
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = inputCode.getText().toString().trim();
                if (validateCode(code)) {
                    // Guardar licencia y pasar a la app
                    SharedPreferences prefs = getSharedPreferences("BreakingCraft", MODE_PRIVATE);
                    prefs.edit().putString("license_code", code).putBoolean("is_activated", true).apply();
                    
                    // Abrir la app principal (MainActivity)
                    try {
                        Class<?> mainClass = Class.forName("com.origin.launcher.activity.MainActivity");
                        startActivity(new Intent(LicenseActivity.this, mainClass));
                        finish();
                    } catch (Exception e) {
                        Toast.makeText(LicenseActivity.this, "Error al abrir el juego", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LicenseActivity.this, "Código inválido o vencido", Toast.LENGTH_LONG).show();
                }
            }
        });
        layout.addView(btnValidate);

        setContentView(layout);
    }

    private boolean validateCode(String code) {
        try {
            // El código es BC-YYYYMMDD-NOMBRE
            if (!code.startsWith("BC-")) return false;
            String[] parts = code.split("-");
            if (parts.length < 3) return false;
            
            String dateStr = parts[1]; // YYYYMMDD
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
            Date expiryDate = sdf.parse(dateStr);
            Date today = new Date();
            
            return !today.after(expiryDate); // Retorna true si no ha vencido
        } catch (Exception e) {
            return false;
        }
    }
}
