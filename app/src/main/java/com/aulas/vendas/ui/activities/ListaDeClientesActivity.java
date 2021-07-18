package com.aulas.vendas.ui.activities;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aulas.vendas.R;
import com.aulas.vendas.dao.ClienteDAO;
import com.aulas.vendas.database.VendasDatabase;
import com.aulas.vendas.database.dao.RoomClienteDAO;
import com.aulas.vendas.model.Cliente;
import com.aulas.vendas.ui.adapter.ListaClientesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.aulas.vendas.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.aulas.vendas.ui.activities.ConstantesActivities.TITULO_APPBAR_LISTA_DE_CLIENTES;

public class ListaDeClientesActivity extends AppCompatActivity {
//--------------------------------------------------------------------------------------------------
    //VARIAVEIS DE CLASSE
    private ListaDeClientestView listaDeClientestView;
//--------------------------------------------------------------------------------------------------
    //METODO MAIN DO JAVA
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFB0000")));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_clientes_activity);
        setTitle(TITULO_APPBAR_LISTA_DE_CLIENTES);
        listaDeClientestView = new ListaDeClientestView(this);
        configuraFabNewCliente();
        configuraLista();
    }
//--------------------------------------------------------------------------------------------------
    //MENU ITENS LISTA
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_lista_clientes_activity,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_remover_lista_clientes_activity) {
            listaDeClientestView.confirmaRemocao(item);
        }
        return super.onContextItemSelected(item);
    }
//--------------------------------------------------------------------------------------------------
    //CICLO DE VIDA DO APP
    @Override
    protected void onResume() {
        super.onResume();
        listaDeClientestView.atualizaLista();
    }
//--------------------------------------------------------------------------------------------------
    private void configuraLista(){
    ListView listaDeClientes = findViewById(R.id.list_view_clientes);
    listaDeClientestView.configuraAdapter(listaDeClientes);
    configuraClickPorItem(listaDeClientes);
    registerForContextMenu(listaDeClientes);
}
//--------------------------------------------------------------------------------------------------
    private void configuraFabNewCliente(){
        FloatingActionButton fabNewCliente = findViewById(R.id.fab_novo_cliente);
        fabNewCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioModoInsereNew();
            }
        });
    }
//--------------------------------------------------------------------------------------------------
    private void configuraClickPorItem(ListView listaClientes){
        listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
               Cliente clienteEscolhido = (Cliente)adapterView.getItemAtPosition(position);
                abreFormularioModoEdita(clienteEscolhido);
            }
        });
    }

    private void abreFormularioModoEdita(Cliente cliente){
        Intent vaiParaDadosCliente = new Intent(ListaDeClientesActivity.this, DadosClineteActivity.class);
        vaiParaDadosCliente.putExtra(CHAVE_CLIENTE, cliente);
        startActivity(vaiParaDadosCliente);
    }

    private void abreFormularioModoInsereNew() {
        startActivity(new Intent(ListaDeClientesActivity.this, FormularioDadosPessoaisActivity.class));
    }
//--------------------------------------------------------------------------------------------------

}
