package com.aulas.vendas.ui.activities;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aulas.vendas.R;
import com.aulas.vendas.model.Cliente;
import com.aulas.vendas.model.MaskText;

import static com.aulas.vendas.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.aulas.vendas.ui.activities.ConstantesActivities.MASK_CPF;
import static com.aulas.vendas.ui.activities.ConstantesActivities.MASK_DATA_NASCIMENTO;
import static com.aulas.vendas.ui.activities.ConstantesActivities.TITULO_APPBAR_DADOS_PESSOAIS;
;

public class FormularioDadosPessoaisActivity extends AppCompatActivity {
//--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle saveIntanceState) {
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFB0000")));
        super.onCreate(saveIntanceState);
        setTitle(TITULO_APPBAR_DADOS_PESSOAIS);
        setContentView(R.layout.formulario_dados_pessoais_activity);
        bindDosCampos();
        formataTexto();
        recebeClienteIntent();
        configuraBotao();
    }
//--------------------------------------------------------------------------------------------------
    private EditText campoNomeCompleto;
    private EditText campoDataNascimento;
    private EditText campoCpf;
    private EditText campoRg;
    private EditText campoNomePai;
    private EditText campoNomeMae;
    private Button botao;

    private Cliente cliente;
//--------------------------------------------------------------------------------------------------
    private void bindDosCampos(){
        campoNomeCompleto = findViewById(R.id.edit_nome_completo);
        campoDataNascimento = findViewById(R.id.edit_data_nascimento);
        campoCpf = findViewById(R.id.edit_cpf);
        campoRg = findViewById(R.id.edit_rg);
        campoNomePai = findViewById(R.id.edit_nome_pai);
        campoNomeMae = findViewById(R.id.edit_nome_mae);
        botao = findViewById(R.id.botao_salvar);
    }
//--------------------------------------------------------------------------------------------------
    //MASCARA FORMATA TEXTO
    private void formataTexto(){
        campoCpf.addTextChangedListener(MaskText.insert(MASK_CPF, campoCpf));
        campoDataNascimento.addTextChangedListener(MaskText.insert(MASK_DATA_NASCIMENTO, campoDataNascimento));
    }
//--------------------------------------------------------------------------------------------------
    private void recebeDadosDigitadosNosCampos(){
        String nomeCompleto = campoNomeCompleto.getText().toString();
        String dataNascimento = campoDataNascimento.getText().toString();
        String cpf = campoCpf.getText().toString();
        String rg = campoRg.getText().toString();
        String nomePai = campoNomePai.getText().toString();
        String nomeMae = campoNomeMae.getText().toString();

        cliente.setNomeCompleto(nomeCompleto);
        cliente.setDataNascimento(dataNascimento);
        cliente.setCpf(cpf);
        cliente.setRg(rg);
        cliente.setNomePai(nomePai);
        cliente.setNomeMae(nomeMae);
    }
//--------------------------------------------------------------------------------------------------
   //CARREGA DADOS ENVIADOS PELA INTENT
    private void recebeClienteIntent(){
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_CLIENTE)){
            cliente = (Cliente) dados.getSerializableExtra(CHAVE_CLIENTE);
            preencheCamposParaEdicao();
        }else{
            cliente = new Cliente();
        }
    }
//--------------------------------------------------------------------------------------------------
    private void preencheCamposParaEdicao(){
        campoNomeCompleto.setText(cliente.getNomeCompleto());
        campoDataNascimento.setText(cliente.getDataNascimento());
        campoCpf.setText(cliente.getCpf());
        campoRg.setText(cliente.getRg());
        campoNomePai.setText(cliente.getNomePai());
        campoNomeMae.setText(cliente.getNomeMae());
    }
//--------------------------------------------------------------------------------------------------
    private void concluiDadosCliente(){
        recebeDadosDigitadosNosCampos();
        enviaDadosPessoais(cliente);
    }
//--------------------------------------------------------------------------------------------------
    private void enviaDadosPessoais(Cliente cliente){
        Intent vaiParaContato = new Intent(FormularioDadosPessoaisActivity.this, FormularioContatoActivity.class);
        vaiParaContato.putExtra(CHAVE_CLIENTE, cliente);
        startActivity(vaiParaContato);
    }
//--------------------------------------------------------------------------------------------------
    private void configuraBotao(){
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluiDadosCliente();
            }
        });
    }
//--------------------------------------------------------------------------------------------------
}
