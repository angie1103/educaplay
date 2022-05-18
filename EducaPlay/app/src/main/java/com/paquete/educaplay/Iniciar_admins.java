package com.paquete.educaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paquete.educaplay.ui.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Iniciar_admins extends AppCompatActivity {

    EditText usuario, contraseña;
    Button iniciar_sesion;
    Connection con;
    String nombreusuario, nom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_admins);
        usuario = (EditText) findViewById(R.id.admin_ingresar1);
        contraseña = (EditText) findViewById(R.id.admin_ingresar2);
        iniciar_sesion = (Button) findViewById(R.id.btniniciaradmin);
        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new checkLogin().execute("");
                finish();
            }
        });
    }

    public class checkLogin extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            con = conexionBD(ConnectionClass.rol.toString(),ConnectionClass.nom.toString(), ConnectionClass.ape.toString(), ConnectionClass.correo.toString(), ConnectionClass.correo.toString(), ConnectionClass.contra.toString());
            if (con == null) {
                Toast.makeText(Iniciar_admins.this, "Revisa tu conexion", Toast.LENGTH_LONG).show();
            } else {
                try {

                    String sql = "SELECT * FROM Usuarios WHERE Correo = '" + usuario.getText() + "' AND Pass = '" + contraseña.getText() + "' ";
                    nombreusuario = usuario.getText().toString();
                    nom = "" + nombreusuario;
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);
                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Iniciar_admins.this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show();
                            }
                        });
                        z = "Success";
                        Intent intent = new Intent(Iniciar_admins.this, MainActivity2.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Iniciar_admins.this, "Revisa tu usuario o contraseña", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Iniciar_admins.this, Iniciar_admins.class);
                                startActivity(intent);
                            }
                        });
                        usuario.setText("");
                        contraseña.setText("");
                    }

                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error :", e.getMessage());
                }
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection conexionBD(String rol,String nom, String ape, String corr, String usu, String cotra) {
        Connection conexion = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:jtds:sqlserver://gutgara.ddns.net;databaseName=EducaPlay;user=gutgara;password=VAuX2v_1xx0_T9w");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return conexion;
    }
    public void nuevoadmin (View view){
        Intent in = new Intent(this, registrar_admins.class);
        startActivity(in);
    }
}