package com.aulas.vendas.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.aulas.vendas.database.converter.ConversorCalendar;
import com.aulas.vendas.database.dao.RoomClienteDAO;
import com.aulas.vendas.model.Cliente;

@Database(entities = {Cliente.class},version = 1, exportSchema = false)

//ADICIONANDO OS TIPOS DE CONVERSOR QUE VAMOS TER NO PROGETO
@TypeConverters({ConversorCalendar.class})
public abstract class VendasDatabase extends RoomDatabase {
    /*CASO ADICIONE UM CAMPO A MAIS OU REMOVA DO FORMULARIO,
    UTILIZE O ".fallbackToDestructiveMigration()" QUE IRA DESTRUIR O BD E RECRIAR UM NOVO COM OS NOVOS
    DADOS. MAS SO FACA ISSO QUANDO NINGUEM ESTIVER USANDO O APLICATIVO, SOMENTE EM PRODUCAO SE NAO
    IRA APAGAR TODOS OS DADOS.
    
     */

    private static final String NOME_BD = "vendas.db";

    public abstract RoomClienteDAO getClienteDAO();

    public static VendasDatabase getInstance(Context context){
        return Room.databaseBuilder(context, VendasDatabase.class, NOME_BD)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
