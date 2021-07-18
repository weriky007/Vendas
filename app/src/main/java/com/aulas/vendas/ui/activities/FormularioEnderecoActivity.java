package com.aulas.vendas.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aulas.vendas.R;
import com.aulas.vendas.dao.ClienteDAO;
import com.aulas.vendas.model.Cliente;
import com.aulas.vendas.model.MaskText;

import static com.aulas.vendas.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.aulas.vendas.ui.activities.ConstantesActivities.MASK_CEP;
import static com.aulas.vendas.ui.activities.ConstantesActivities.TITULO_APPBAR_ENDERECO;

public class FormularioEnderecoActivity extends AppCompatActivity {
//--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFB0000")));
        super.onCreate(savedInstanceState);
        setTitle(TITULO_APPBAR_ENDERECO);
        setContentView(R.layout.formulario_endereco_activity);
        bindDosCampos();
        formataTexto();
        recebeClienteIntent();
        configuraBotao();
    }
//--------------------------------------------------------------------------------------------------
    private EditText campoRua;
    private EditText campoNumero;
    private EditText campoQuadra;
    private EditText campoLote;
    private EditText campoBairro;
    private EditText campoCEP;
    private EditText campoComplemento;
    private Button botao;

    private Cliente cliente;
//--------------------------------------------------------------------------------------------------
    private void bindDosCampos() {
        campoRua = findViewById(R.id.edit_campo_rua);
        campoNumero = findViewById(R.id.edit_campo_numero);
        campoQuadra = findViewById(R.id.edit_campo_quadra);
        campoLote = findViewById(R.id.edit_campo_lote);
        campoBairro = findViewById(R.id.edit_campo_bairro);
        campoCEP = findViewById(R.id.edit_campo_cep);
        campoComplemento = findViewById(R.id.edit_campo_complemento);
        botao = findViewById(R.id.botao_salvar);
    }
//--------------------------------------------------------------------------------------------------
    private void formataTexto() {
        campoCEP.addTextChangedListener(MaskText.insert(MASK_CEP, campoCEP));
    }
//--------------------------------------------------------------------------------------------------
    private void recebeDadosDigitadosNosCampos() {
        String rua = campoRua.getText().toString();
        String numero = campoNumero.getText().toString();
        String quadra = campoQuadra.getText().toString();
        String lote = campoLote.getText().toString();
        String bairro = campoBairro.getText().toString();
        String cep = campoCEP.getText().toString();
        String complemento = campoComplemento.getText().toString();

        cliente.setRua(rua);
        cliente.setNumero(numero);
        cliente.setQuadra(quadra);
        cliente.setLote(lote);
        cliente.setBairro(bairro);
        cliente.setCep(cep);
        cliente.setComplemento(complemento);
    }
//--------------------------------------------------------------------------------------------------
    private void recebeClienteIntent(){
    Intent dados = getIntent();
    if(dados.hasExtra(CHAVE_CLIENTE)){ ;
        cliente = (Cliente) dados.getSerializableExtra(CHAVE_CLIENTE);
        preencheCamposParaEdicao();
    }else{
        cliente = new Cliente();
    }
}
//--------------------------------------------------------------------------------------------------
    private void preencheCamposParaEdicao(){
        campoRua.setText(cliente.getRua());
        campoNumero.setText(cliente.getNumero());
        campoQuadra.setText(cliente.getQuadra());
        campoLote.setText(cliente.getLote());
        campoBairro.setText(cliente.getBairro());
        campoCEP.setText(cliente.getCep());
        campoComplemento.setText(cliente.getComplemento());
    }
//--------------------------------------------------------------------------------------------------
    private void concluiDadosEndereco(){
        recebeDadosDigitadosNosCampos();
        enviaDadosEndereco(cliente);
    }
//--------------------------------------------------------------------------------------------------
    private void enviaDadosEndereco(Cliente cliente){
        Intent vaiParaCadastroCliente = new Intent(FormularioEnderecoActivity.this, DadosClineteActivity.class);
        vaiParaCadastroCliente.putExtra(CHAVE_CLIENTE, cliente);
        startActivity(vaiParaCadastroCliente);
    }
//--------------------------------------------------------------------------------------------------
    private void configuraBotao() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             concluiDadosEndereco();
            }
        });
    }
//--------------------------------------------------------------------------------------------------
}
