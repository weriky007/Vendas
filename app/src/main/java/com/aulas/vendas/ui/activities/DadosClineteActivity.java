package com.aulas.vendas.ui.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.aulas.vendas.R;
import com.aulas.vendas.dao.ClienteDAO;
import com.aulas.vendas.database.VendasDatabase;
import com.aulas.vendas.database.dao.RoomClienteDAO;
import com.aulas.vendas.model.Cliente;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import static com.aulas.vendas.ui.activities.ConstantesActivities.CHAVE_CLIENTE;
import static com.aulas.vendas.ui.activities.ConstantesActivities.TITULO_APPBAR_DADOS_CLIENTE;

public class DadosClineteActivity extends AppCompatActivity {
//--------------------------------------------------------------------------------------------------
    //DADOS PESSOAIS
    private TextView nomeCompleto;
    private TextView dataNascimento;
    private TextView cpf;
    private TextView rg;
    private TextView nomePai;
    private TextView nomeMae;

    //CONTATO
    private TextView celular1;
    private TextView celular2;
    private TextView telefone;
    private TextView email;

    //ENDERECO
    private TextView rua;
    private TextView numero;
    private TextView quadra;
    private TextView lote;
    private TextView bairro;
    private TextView cep;
    private TextView complemento;

    private Button botaoSalvar;

    //CONFIGURA DATA
    Calendar dataCal = GregorianCalendar.getInstance();
    private int ano = (int)dataCal.get(Calendar.YEAR);

    private Cliente cliente;
    private RoomClienteDAO dao;

    //CONFIGURACAO SCRIPT E PLANILHA BASE DADOS
    String linkMacro= "https://script.google.com/macros/s/AKfycbwUpE6WxtMbrjSRMI0ctqqKrR9k6KdVIiKBiBr1fEONKeuKuggJ-ue0hvK5RwDIMZeT/exec";
    String idPlanilha = "1GlZHDYvGpCa7pbuVf7MnkorPUq9JLbFGN7sOSzftEuQ";
//--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFB0000")));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dados_cliente_activity);
        VendasDatabase dataBase = VendasDatabase.getInstance(this);
        dao = dataBase.getClienteDAO();
        setTitle(TITULO_APPBAR_DADOS_CLIENTE);
        bindDosCampos();
        recebeDados();
        configuraBotaoSalvar();
    }
//--------------------------------------------------------------------------------------------------
    //BOTAO NA APPBAR COM MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_formulario_editar, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int idItensBotao = item.getItemId();
        if(idItensBotao == R.id.item_botao_formulario_editar){
            enviaDadosPessoais(cliente);
        }

        return super.onOptionsItemSelected(item);
    }
//--------------------------------------------------------------------------------------------------
    private void bindDosCampos(){
        nomeCompleto = findViewById(R.id.text_dados_nome_completo);
        dataNascimento = findViewById(R.id.text_dados_data_nascimento);
        cpf = findViewById(R.id.text_dados_cpf);
        rg = findViewById(R.id.text_dados_rg);
        nomePai = findViewById(R.id.text_dados_nome_pai);
        nomeMae = findViewById(R.id.text_dados_nome_mae);

        celular1 = findViewById(R.id.text_contato_celular1);
        celular2 = findViewById(R.id.text_contato_celular2);
        telefone = findViewById(R.id.text_contato_telefone);
        email = findViewById(R.id.text_contato_email);

        rua = findViewById(R.id.text_dados_rua);
        numero = findViewById(R.id.text_dados_numero);
        quadra = findViewById(R.id.text_dados_quadra);
        lote = findViewById(R.id.text_dados_lote);
        bairro = findViewById(R.id.text_dados_bairro);
        cep = findViewById(R.id.text_dados_cep);
        complemento = findViewById(R.id.text_dados_complemento);

        botaoSalvar = findViewById(R.id.botao_salvar);
    }
//--------------------------------------------------------------------------------------------------
    private void recebeDados() {
        Intent dados = getIntent();
        if(dados.hasExtra(CHAVE_CLIENTE)){
            cliente = (Cliente) dados.getSerializableExtra(CHAVE_CLIENTE);
            preencheDados();
        }
    }
//--------------------------------------------------------------------------------------------------
    private void preencheDados(){
        nomeCompleto.setText("Nome: "+cliente.getNomeCompleto());
        dataNascimento.setText("Data de Nascimento: "+cliente.getDataNascimento());
        cpf.setText("CPF: "+cliente.getCpf());
        rg.setText("RG: "+cliente.getRg());
        nomePai.setText("Pai: "+cliente.getNomePai());
        nomeMae.setText("Mae: "+cliente.getNomeMae());

        celular1.setText("Celular1: "+cliente.getCelular1());
        celular2.setText("Celular2: "+cliente.getCelular2());
        telefone.setText("Telefone: "+cliente.getTelefone());
        email.setText("E-mail: "+cliente.getEmail());

        rua.setText("Rua: "+cliente.getRua());
        numero.setText("Numero: "+cliente.getNumero());
        quadra.setText("Quadra: "+cliente.getQuadra());
        lote.setText("Lote: "+cliente.getLote());
        bairro.setText("Bairro: "+cliente.getBairro());
        cep.setText("CEP: "+cliente.getCep());
        complemento.setText("Complemento: "+cliente.getComplemento());
    }
//--------------------------------------------------------------------------------------------------
    private void  vaiParaLista(){
        startActivity(new Intent(DadosClineteActivity.this,ListaDeClientesActivity.class));
    }

    private void enviaDadosPessoais(Cliente cliente){
        Intent vaiParaEndereco = new Intent(DadosClineteActivity.this, FormularioDadosPessoaisActivity.class);
        vaiParaEndereco.putExtra(CHAVE_CLIENTE, cliente);
        startActivity(vaiParaEndereco);
    }

    private void concluiCadastro(){
        preencheDados();
        if(cliente.temIdValido()){
            dao.edita(cliente);
            Toast.makeText(DadosClineteActivity.this,"Editado com Sucesso!",Toast.LENGTH_SHORT).show();
        }else {
            dao.salva(cliente);
            vaiParaLista();
            Toast.makeText(DadosClineteActivity.this,"Salvo com Sucesso!",Toast.LENGTH_SHORT).show();
        }
        finish();
    }
//--------------------------------------------------------------------------------------------------
    //INICIANDO COMUNICACAO WEB
public class SendRequest extends AsyncTask<String, Void, String> {

    protected void onPreExecute(){}

    protected String doInBackground(String... arg0) {
        try{
            URL url = new URL(linkMacro);
            String idPlan= idPlanilha;

            JSONObject enviaDados = enviaDadosParaPlanilha(idPlan);
            HttpURLConnection connection = executaConeccaoExternalServer(url);
            escreveDadosNaPlanilha(enviaDados, connection);
            return verificaLinhaVazia(connection);
        }//end try

        catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }//end catch

    }//end doInBackGround

    @Override
    protected void onPostExecute(String resultado) {
        Toast.makeText(getApplicationContext(),"Salvo!!!",Toast.LENGTH_LONG).show();
//            Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
    }//end onPostExecute
}//end sendRequest
//--------------------------------------------------------------------------------------------------
    private String verificaLinhaVazia(HttpURLConnection connection) throws IOException {
        int codigoWeb = connection.getResponseCode();
        if (codigoWeb == HttpsURLConnection.HTTP_OK) {
            BufferedReader leValor = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer sb = new StringBuffer("");
            String linha="";

            while((linha = leValor.readLine()) != null) {
                sb.append(linha);
                break;
            }//end while

            leValor.close();
            return sb.toString();

        }//end if
        else {
            return new String("false : "+codigoWeb);
        }//end else
    }
//--------------------------------------------------------------------------------------------------
    private void escreveDadosNaPlanilha(JSONObject enviaDados, HttpURLConnection connection) throws Exception {
        OutputStream saida = connection.getOutputStream();
        BufferedWriter escrita = new BufferedWriter(new OutputStreamWriter(saida, "UTF-8"));

        escrita.write(configuraDadosParaWeb(enviaDados));
        escrita.flush();
        escrita.close();
        saida.close();
    }
    //--------------------------------------------------------------------------------------------------
    private HttpURLConnection executaConeccaoExternalServer(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setReadTimeout(15000 /* milliseconds */);
        connection.setConnectTimeout(15000 /* milliseconds */);
        connection.setRequestMethod("POST");
        connection.setDoInput(true);
        connection.setDoOutput(true);
        return connection;
    }
//--------------------------------------------------------------------------------------------------
    //ENVIA DADOS PARA A PLANILHA GOOGLE
    private JSONObject enviaDadosParaPlanilha(String idPlan) throws JSONException {
        JSONObject enviaDados = new JSONObject();

        enviaDados.put("idPlan",idPlan);
        enviaDados.put("idCliente", ano+""+cliente.getId());
        enviaDados.put("nome", cliente.getNomeCompleto());
        enviaDados.put("dataNascimento", cliente.getDataNascimento());
        enviaDados.put("cpf",cliente.getCpf());
        enviaDados.put("rg",cliente.getRg());
        enviaDados.put("nomePai",cliente.getNomePai());
        enviaDados.put("nomeMae",cliente.getNomeMae());

        Log.e("params",enviaDados.toString());
        return enviaDados;
    }
//--------------------------------------------------------------------------------------------------
    public String configuraDadosParaWeb(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){
            String key= itr.next();
            Object value = params.get(key);

            if (first) {
                first = false;
            }else {
                result.append("&");
            }
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }//end configuraData
//--------------------------------------------------------------------------------------------------
    private void configuraBotaoSalvar(){
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                concluiCadastro();
                new SendRequest().execute();
                vaiParaLista();
            }
        });
    }
//--------------------------------------------------------------------------------------------------
}
