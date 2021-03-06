package com.example.levimmartins.heartratemonitor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by levimmartins on 13/04/17.
 */


public class BackgroundWorker extends  AsyncTask <String, Void, String>  {

    Context context;
    AlertDialog alertDialog;
    String global_user;
    String globalPontos;


    String dica = "";
    String idDica = "";

    public String getGlobal_user() {
        return global_user;
    }

    public void setGlobal_user(String global_user) {
        this.global_user = global_user;
    }

    public String getGlobalPontos() {
        return globalPontos;
    }

    public void setGlobalPontos(String globalPontos) {
        this.globalPontos = globalPontos;
    }

    public String getDica() {
        return dica;
    }

    public void setDica(String dica) {
        this.dica = dica;
    }


    public interface AsyncResponse {
        void processFinish(String output);
    }



    public AsyncResponse delegate = null;

      BackgroundWorker (Context ctx){
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String login_url = "http://192.168.0.3/HRM/logar.php";
        String cadastrar_url = "http://192.168.0.3/HRM/cadastrar.php";
        String pontos_url = "http://192.168.0.3/HRM/pontos.php" ;
        String pontuar_url = "http://192.168.0.3/HRM/pontuarHRM.php";
        String lerDica_url = "http://192.168.0.3/HRM/lerDica.php";
        String setDica_url = "http://192.168.0.3/HRM/setDica.php";

        if(type.equals("login")){
            try {
                String user_name = params[1];
                String password = params[2];
                global_user = user_name;
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+URLEncoder.encode("senha","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-15"));
                String result = "";
                String line;

                while((line = bufferedReader.readLine() )!= null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(type.equals("register")){
            try {
                String nome = params[1];
                String dataNascimento = params[2];
                String user_name = params[3];
                String emailCuidador =  params[4];
                String password = params[5];

                URL url = new URL(cadastrar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("nome", "UTF-8")+"="+URLEncoder.encode(nome,"UTF-8")+"&"+URLEncoder.encode("dataNascimento","UTF-8")+"="+URLEncoder.encode(dataNascimento,"UTF-8")+"&"+URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+URLEncoder.encode("senha","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+URLEncoder.encode("emailCuidador","UTF-8")+"="+URLEncoder.encode(emailCuidador,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-15"));
                String result = "";
                String line;

                while((line = bufferedReader.readLine() )!= null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("pontos")){
            try{
                String user_name = params[1];

                URL url = new URL(pontos_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-15"));
                String result = "";
                String line;

                while((line = bufferedReader.readLine() )!= null){
                    result+=line;
                }
                this.setGlobalPontos(result);


                Intent openConquistas = new Intent(this.context, Conquistas.class);
                openConquistas.putExtra("EXTRA_SESSION_PONTOS",this.getGlobalPontos());
                // openConquistas.putExtra("EXTRA_SESSION_ID", this.getGlobal_user());
                context.startActivity(openConquistas);



                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("pontuar")){
            try {

                String user_name = params[1];
                String bpm = params[2];

                URL url = new URL(pontuar_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data =URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+URLEncoder.encode("bpm", "UTF-8")+"="+URLEncoder.encode(bpm,"UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-15"));
                String result = "";
                String line;

                while((line = bufferedReader.readLine() )!= null){
                    result+=line;
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(type.equals("lerDica")){
                try{


                        String user_name = params[1];
                        String idDica = params[2];


                        URL url = new URL(lerDica_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);

                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data =URLEncoder.encode("email", "UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+URLEncoder.encode("idDicas","UTF-8")+"="+URLEncoder.encode(idDica,"UTF-8");
                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();


                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-15"));
                        String result = "";
                        String line;

                        while((line = bufferedReader.readLine() )!= null){
                            result+=line;
                        }

                       // this.setDica(result);

                        //JSONParser jParser = new JSONParser();
                        //List<NameValuePair> parameters = new ArrayList<NameValuePair>();


                        try {
                            JSONObject  jObj = new JSONObject(result);

                           // System.out.println(jObj.toString());
                           // System.out.println(jObj.get("dica"));

                            //String dicas = jObj.getJSONArray("dica").toString();this.setDica(dicas);
                            //this.setDica(jObj.getString("dica"));
                            this.dica = new String(jObj.getString("dica"));
                            this.dica = "Tip:"+this.dica;//params[3] = params[3].concat(dica);


                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                        result = "dicaRecebida";
                        //System.out.println(dica);
                        bufferedReader.close();
                        inputStream.close();
                        httpURLConnection.disconnect();

                        return result;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                }
        }else if(type.equals("SetIdDica")) {
            try {

                String user_name = params[1];


                URL url = new URL(setDica_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(user_name, "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "ISO-8859-15"));
                String result = "";
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }



                try {
                    JSONObject  jObj = new JSONObject(result);


                    this.idDica= new String(jObj.getString("total"));
                }catch(JSONException e){
                    e.printStackTrace();
                }



                result = "idRetornado";
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        return null;
    }


    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Status");
    }

    @Override
    protected void onPostExecute(String result) {
        //super.onPostExecute(aVoid);
        //alertDialog.setMessage(result);
        //alertDialog.show();

        if(result.equals("Cadastro_ok")){
            Intent abreLogin = new Intent(this.context, login.class);
            context.startActivity(abreLogin);

            Toast.makeText(this.context, "Cadastro Realizado com SUCESSO!", Toast.LENGTH_LONG).show();
        }


        if(result.equals("Cadastro_EmailCadastrado")){
            Toast.makeText(this.context, "Email já cadastrado!", Toast.LENGTH_LONG).show();
        }

        if(result.contains("Pontos:")) {
            Intent openConquistas = new Intent(this.context, Conquistas.class);
            openConquistas.putExtra("EXTRA_SESSION_PONTOS",result);
           // openConquistas.putExtra("EXTRA_SESSION_ID", this.getGlobal_user());
             context.startActivity(openConquistas);


        }


        if(result.equals("Cadastro_erro")){
            Toast.makeText(this.context, "Houve um problema no cadastro!", Toast.LENGTH_LONG).show();
        }


        if(result.equals("login_ok")){


            Intent abreInicio = new Intent(this.context, TelaInicial.class);
            abreInicio.putExtra("EXTRA_SESSION_ID", global_user);
            context.startActivity(abreInicio);

            //((Activity)context).finish();
        }else if(result.equals("login_erro")){
            Toast.makeText(this.context, "Usuário ou senha estão incorretos.", Toast.LENGTH_LONG).show();
        }else if(result.equals("pontuar")){
            Toast.makeText(this.context, "BACKGROUND PONTUAR TODO", Toast.LENGTH_LONG).show();
        }


        if(result.equals("idRetornado")){
            delegate.processFinish(this.idDica);
            //delegate = null;

        }

        if(result.equals("dicaRecebida")){
            //delegate = null;
            delegate.processFinish(this.dica);
        }






    }



    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}