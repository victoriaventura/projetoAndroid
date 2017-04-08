package com.example.oddvicky.cardapio.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oddvicky.cardapio.model.Comida;
import com.example.oddvicky.cardapio.model.Restaurante;

import java.util.LinkedList;
import java.util.List;


public class ComidaDAO {
    private SQLiteDatabase db;
    private DBOpenHelper banco;

    public ComidaDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    private static final String TABELA_COMIDA = "comida";

    private static final String COLUNA_ID = "id";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_RESTAURANTE_ID = "restaurante_id";
    private static final String COLUNA_PRECO = "preco";

    //private static final String[] COLUMNS = {COLUNA_ID, COLUNA_NOME, COLUNA_CLUBE_ID};

    public String add(Comida comida){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, comida.getNome());
        values.put(COLUNA_RESTAURANTE_ID, comida.getRestaurante().getId());
        values.put(COLUNA_PRECO, comida.getPreco());

        resultado = db.insert(TABELA_COMIDA,
                null,
                values);

        db.close();

        if(resultado == -1) {
            return "Erro ao inserir registro";
        } else {
            return "Registro inserido com sucesso";
        }
    }

    public String remove(int comidaId){
        long resultado;
        SQLiteDatabase db = banco.getWritableDatabase();
        ContentValues values = new ContentValues();

        resultado = db.delete(TABELA_COMIDA, COLUNA_ID + "=" + comidaId, null);

        db.close();

        if(resultado > 0) {
            return "Registro(s) deletado(s) com sucesso!";
        } else {
            return "Ocorreu um erro!";
        }
    }

    public List<Comida> getAll() {
        List<Comida> comidas = new LinkedList<>();

        String rawQuery = "SELECT t.*, c.nome FROM " + ComidaDAO.TABELA_COMIDA + " t INNER JOIN " + RestauranteDAO.TABELA_RESTAURANTES
                + " c ON t." + ComidaDAO.COLUNA_RESTAURANTE_ID + " = c." + RestauranteDAO.COLUNA_ID +
                " ORDER BY " + ComidaDAO.COLUNA_NOME  + " ASC";

        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery(rawQuery, null);

        Comida comida = null;
        if (cursor.moveToFirst()) {
            do {
                comida = new Comida();

                comida.setId(cursor.getInt(0));
                comida.setNome(cursor.getString(2));
                comida.setPreco(cursor.getDouble(3));
                comida.setRestaurante(new Restaurante(cursor.getInt(1), cursor.getString(4)));

                comidas.add(comida);
            } while (cursor.moveToNext());
        }
        return comidas;
    }
}

