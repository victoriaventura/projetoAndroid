package com.example.oddvicky.cardapio.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.oddvicky.cardapio.model.Restaurante;

import java.util.LinkedList;
import java.util.List;

public class RestauranteDAO {

    private DBOpenHelper banco;

    public RestauranteDAO(Context context) {
        banco = new DBOpenHelper(context);
    }

    public static final String TABELA_RESTAURANTES = "restaurante";

    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";

    public List<Restaurante> getAll() {
        List<Restaurante> restaurantes = new LinkedList<>();

        String query = "SELECT  * FROM " + TABELA_RESTAURANTES;

        SQLiteDatabase db = banco.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Restaurante restaurante = null;

        if (cursor.moveToFirst()) {
            do {
                restaurante = new Restaurante();
                restaurante.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
                restaurante.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
                restaurantes.add(restaurante);
            } while (cursor.moveToNext());
        }
        return restaurantes;
    }

    public Restaurante getBy(int id) {

        SQLiteDatabase db = banco.getReadableDatabase();
        String colunas[] = { COLUNA_ID, COLUNA_NOME};
        String where = "id = " + id;
        Cursor cursor = db.query(true, TABELA_RESTAURANTES, colunas, where, null, null, null, null, null);

        Restaurante restaurante = null;

        if(cursor != null)
        {
            cursor.moveToFirst();
            restaurante = new Restaurante();
            restaurante.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
            restaurante.setId(cursor.getInt(cursor.getColumnIndex(COLUNA_ID)));
        }
        return restaurante;
    }
}