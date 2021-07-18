package com.aulas.vendas.ui.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.aulas.vendas.dao.ClienteDAO;
import com.aulas.vendas.database.VendasDatabase;
import com.aulas.vendas.database.dao.RoomClienteDAO;
import com.aulas.vendas.model.Cliente;
import com.aulas.vendas.ui.adapter.ListaClientesAdapter;

import static androidx.core.content.ContextCompat.startActivity;
import static com.aulas.vendas.ui.activities.ConstantesActivities.CHAVE_CLIENTE;

public class ListaDeClientestView extends AppCompatActivity {
//--------------------------------------------------------------------------------------------------
    private final ListaClientesAdapter adapter;
    private final RoomClienteDAO dao;
    private final Context context;
//--------------------------------------------------------------------------------------------------
    public ListaDeClientestView(Context context){
        this.context = context;
        this.adapter = new ListaClientesAdapter(this.context);
        //SO UTILISE O .allowMainThreadQueries() QUANDO FOR TESTAR
         dao = VendasDatabase.getInstance(context).getClienteDAO();
    }
//--------------------------------------------------------------------------------------------------
    public void confirmaRemocao(final MenuItem item) {
            new AlertDialog.Builder(context).setTitle("Removendo Cliente").setMessage("Deseja mesmo remover o cliente?").setPositiveButton("Sim", (dialogInterface, i) -> {
                Cliente clienteEscolhido = pegaCliente(item);
                remove(clienteEscolhido);
                    })
                    .setNegativeButton("Não",null)
                    .show();
    }


    private Cliente pegaCliente(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        return adapter.getItem(menuInfo.position);
    }
//--------------------------------------------------------------------------------------------------
    private void remove(Cliente cliente){
        adapter.remove(cliente);
        dao.remove(cliente);
    }
//--------------------------------------------------------------------------------------------------
    public void atualizaLista(){
        adapter.atualiza(dao.todos());
}
//--------------------------------------------------------------------------------------------------
    public void configuraAdapter(ListView listaDeClientes) {

        listaDeClientes.setAdapter(adapter);
}
//--------------------------------------------------------------------------------------------------
}
