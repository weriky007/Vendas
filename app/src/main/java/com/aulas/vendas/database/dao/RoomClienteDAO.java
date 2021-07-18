package com.aulas.vendas.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.aulas.vendas.model.Cliente;

import java.util.List;

@Dao
public interface RoomClienteDAO {
    @Insert
    void salva(Cliente cliente);

    @Query("SELECT * FROM cliente")
    List<Cliente> todos();

    @Delete
    void remove(Cliente cliente);

    @Update
    void edita(Cliente cliente);
}
