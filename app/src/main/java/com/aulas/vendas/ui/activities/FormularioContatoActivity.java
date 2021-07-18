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
import com.aulas.vendas.model.Cliente;
import com.aulas.vendas.model.MaskText;

import static com.aulas.vendas.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.aulas.vendas.ui.activities.ConstantesActivities.MASK_CEL;
import static com.aulas.vendas.ui.activities.ConstantesActivities.MASK_TEL;
import static com.aulas.vendas.ui.activities.ConstantesActivities.TITULO_APPBAR_CONTATO;

public class FormularioContatoActivity extends AppCompatActivity {
//--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFB0000")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_contato_activity);
        setTitle(TITULO_APPBAR_CONTATO);
        bindDosCampos();
        formataTexto();
        recebeClienteIntent();
        configuraBotao();
    }
//--------------------------------------------------------------------------------------------------
    private EditText campoCelular1;
    private EditText campoCelular2;
    private EditText campoTelefone;
    private EditText campoEmail;
    private Button botao;

    private Cliente cliente;
    //--------------------------------------------------------------------------------------------------
    private void bindDosCampos() {
        campoCelular1= findViewById(R.id.edit_campo_cel1);
        campoCelular2 = findViewById(R.id.edit_campo_cel2);
        campoTelefone = findViewById(R.id.edit_campo_telefone);
        campoEmail = findViewById(R.id.edit_campo_email);
        botao = findViewById(R.id.botao_salvar);
    }
//--------------------------------------------------------------------------------------------------
    private void formataTexto() {
        campoCelular1.addTextChangedListener(MaskText.insert(MASK_CEL, campoCelular1));
        campoCelular2.addTextChangedListener(MaskText.insert(MASK_CEL, campoCelular2));
        campoTelefone.addTextChangedListener(MaskText.insert(MASK_TEL, campoTelefone));
    }
//--------------------------------------------------------------------------------------------------
    private void recebeDadosDigitadosNosCampos() {
        String celular1 = campoCelular1.getText().toString();
        String celular2 = campoCelular2.getText().toString();
        String telefone = campoTelefone.getText().toString();
        String email = campoEmail.getText().toString();

        cliente.setCelular1(celular1);
        cliente.setCelular2(celular2);
        cliente.setTelefone(telefone);
        cliente.setEmail(email);
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
        campoCelular1.setText(cliente.getCelular1());
        campoCelular2.setText(cliente.getCelular2());
        campoTelefone.setText(cliente.getTelefone());
        campoEmail.setText(cliente.getEmail());
    }
//--------------------------------------------------------------------------------------------------
    private void concluiDadosContato(){
        recebeDadosDigitadosNosCampos();
        enviaDadosEndereco(cliente);
    }
//--------------------------------------------------------------------------------------------------
    private void enviaDadosEndereco(Cliente cliente){
        Intent vaiParaEndereco = new Intent(FormularioContatoActivity.this, FormularioEnderecoActivity.class);
        vaiParaEndereco.putExtra(CHAVE_CLIENTE, cliente);
        startActivity(vaiParaEndereco);
    }
//--------------------------------------------------------------------------------------------------
    private void configuraBotao() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluiDadosContato();
            }
        });
    }
//--------------------------------------------------------------------------------------------------
}
