package com.aulas.vendas.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aulas.vendas.R;
import com.aulas.vendas.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ListaClientesAdapter extends BaseAdapter {
//--------------------------------------------------------------------------------------------------
    //CRIANDO UMA COPIA DA LISTA PARA O ADAPTER
    private final List<Cliente> clientes = new ArrayList<>();
    private final Context context;
//--------------------------------------------------------------------------------------------------
    public ListaClientesAdapter(Context context) {
        this.context = context;
    }

    //INDICA A QUANTIDADE DE ELEMENTOS QUE O ADAPTER TERA
    @Override
    public int getCount() {
        return clientes.size();
    }

    //INDICA O ELEMENTO QUE VC QUER COM BASE NA POSICAO
    @Override
    public Cliente getItem(int position) {
        return clientes.get(position);
    }

    //ELEMENTO PELO ID, CASO NAO HOUVER ID NA LISTA DEIXE ZERO
    @Override
    public long getItemId(int position) {
        return clientes.get(position).getId();
    }

    //REPRESENTA AS CONFIGURACOES VIEW QUE SERA APRESENTADA
    @Override
    public View getView(int position, View convertView, ViewGroup listViewClientes) {
        //NAO PRECISA UTILISAR O FALSE QUANDO O LAYOUT E LINEAR
        View viewCriada = criaView(listViewClientes);

        Cliente clienteDevolvido = clientes.get(position);
        vincula(viewCriada, clienteDevolvido);

        return viewCriada;
    }

    private void vincula(View viewCriada, Cliente clienteDevolvido) {
        //BIND
        TextView nome = viewCriada.findViewById(R.id.item_cliente_nome);
        TextView celular1 = viewCriada.findViewById(R.id.item_cliente_telefone);
        TextView endereco = viewCriada.findViewById(R.id.item_cliente_endereco);

        nome.setText(clienteDevolvido.getNomeCompleto());
        celular1.setText(clienteDevolvido.getCelular1());
        endereco.setText("R :"+clienteDevolvido.getRua()+" | "+"Bairro: "+clienteDevolvido.getBairro());
    }

    private View criaView(ViewGroup listViewClientes) {
        return LayoutInflater.from(context).inflate(R.layout.item_cliente, listViewClientes,false);
    }

//--------------------------------------------------------------------------------------------------
    public void atualiza(List<Cliente> clientes){
        this.clientes.clear();
        this.clientes.addAll(clientes);
        notifyDataSetChanged();
    }

    public void remove(Cliente cliente) {
        clientes.remove(cliente);
        notifyDataSetChanged();
    }
//--------------------------------------------------------------------------------------------------
}
