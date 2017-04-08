package com.example.oddvicky.cardapio;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.oddvicky.cardapio.dao.ComidaDAO;
import com.example.oddvicky.cardapio.dao.RestauranteDAO;
import com.example.oddvicky.cardapio.model.Comida;
import com.example.oddvicky.cardapio.model.Restaurante;

import org.w3c.dom.Text;

import java.util.List;

public class NovaComidaActivity extends AppCompatActivity {

    public final static int CODE_NOVA_COMIDA = 1002;

    private TextInputLayout tilNomeComida;
    private TextInputLayout tilPrecoComida;
    private Spinner spRestaurante;

    private List<Restaurante> restaurantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_comida);

        tilNomeComida = (TextInputLayout) findViewById(R.id.tilNomeComida);
        tilPrecoComida = (TextInputLayout) findViewById(R.id.tilPrecoComida);
        spRestaurante = (Spinner) findViewById(R.id.spRestaurante);

        RestauranteDAO restauranteDAO = new RestauranteDAO(this);
        restaurantes = restauranteDAO.getAll();

        ArrayAdapter<Restaurante> adapter =
                new ArrayAdapter<Restaurante>(getApplicationContext(), R.layout.restaurante_spinner_item, restaurantes);

        adapter.setDropDownViewResource(R.layout.restaurante_spinner_item);

        spRestaurante.setAdapter(adapter);
    }

    public void cadastrar(View v) {
        ComidaDAO comidaDAO = new ComidaDAO(this);
        Comida comida = new Comida();
        comida.setNome(tilNomeComida.getEditText().getText().toString());
        comida.setPreco(Double.valueOf(tilPrecoComida.getEditText().getText().toString()));
        comida.setRestaurante((Restaurante)spRestaurante.getSelectedItem());

        comidaDAO.add(comida);

        retornaParaTelaAnterior();
    }

    //retorna para tela de lista de comidas
    public void retornaParaTelaAnterior() {
        Intent intentMessage=new Intent();
        setResult(CODE_NOVA_COMIDA, intentMessage);
        finish();
        Intent homepage = new Intent(NovaComidaActivity.this, MainActivity.class);
        startActivity(homepage);
    }
}

