/* *
* @author: Liliane Castro
* @since: 22/07/2018
* @version 1.1
* */

package com.desafiojoke.joke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.desafiojoke.joke.Models.Joke;
import com.desafiojoke.joke.Remote.JokeService;
import com.squareup.picasso.Picasso;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    JokeService mService;
    TextView txtJoke, txtAuthor;
    ImageView ftImageView, imgAbout;
    Button btnNewJoke;
    SpotsDialog dialog;


    /*
    * @method loadMainScreen carrega a página inicial da aplicação.
    * @button btnGetJoke ao ser clicado vai para a tela de piadas
    * @button btnJokesCategory ao ser clicado vai para a tela de categorias de piadas
    * @button btnAbout ao ser clicado vai para a tela com informações sobre a aplicação
    * @button btnExit ao ser clicado sai da aplicação
    * */
    public void loadMainScreen(){
        setContentView(R.layout.activity_main);
        mService = Common.getValueService();
        dialog = new SpotsDialog(MainActivity.this);


        Button btnGetJoke = (Button) findViewById(R.id.btnGetJoke);
        btnGetJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJokeAddress();
                loadNewJokeScreen();

            }
        });

        Button btnAbout = (Button) findViewById(R.id.btnAbout);
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAboutScreen();
            }
        });

        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    /*
    * @param mService trata com a interface JokeService
    * @param txtJoke mostra o texto retornado da API pelo value
    * @param ftImageView mostra a imagem retornada da API pelo icon_url
    * @bottom btnNewJoke ao ser clicado vai fazer a chamada do método getJokeAddress, responsável pelas requisições na API
    * @bottom btnHome ao ser clicado retornará para a tela inicial da aplicação.
    * */
    public void loadNewJokeScreen() {
        setContentView(R.layout.activity_jokes);
        txtJoke = (TextView) findViewById(R.id.txtJoke);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        ftImageView = (ImageView) findViewById(R.id.ftImageView);
        dialog = new SpotsDialog(MainActivity.this);
        btnNewJoke = (Button) findViewById(R.id.btnNewJoke);

        btnNewJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getJokeAddress();
            }
        });

        Button btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMainScreen();
            }
        });

    }

    /*
    * getJokeAddress vai ser o responsável por verificar se a requisição foi bem sucedida,
    * caso afirmativo ele retornará o texto da API e a imagem correspondente a requisição
    * Se negativo, então a requisição não chegou a API, então é mostrada uma mensagem de erro.
    * */
    private void getJokeAddress(){
        mService.getJoke().enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if(!response.isSuccessful()){
                    Log.i("TAG","ERRO" + response.code());
                    txtJoke.setText("Erro: " + response.code() + ". Something went wrong, Chuck Norris don't find you! Please, contact the administrator!");
                    dialog.dismiss();
                }else{
                    dialog.dismiss();
                    txtJoke.setText(response.body().getValue());
                    txtAuthor.setText("Chuck Norris");
                    String url = response.body().getIcon_url();
                    Picasso.with(getApplicationContext()).load(url).into(ftImageView);
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
                dialog.dismiss();
                txtJoke.setText("Whoa, your're lost buddy! Please check your Internet signal or Chuck Norris will check and he will not like it!");
            }
        });

    }

    /*
    * Método responsável por mostrar uma tela com informações sobre o aplicativo.
    * @param imgAbout através do Picasso, faz uma requisição a uma URL e retorna uma imagem.
    * @button btnHome ao ser clicado retorna para a tela inicial da aplicação.
    * */
    public void loadAboutScreen() {
        setContentView(R.layout.activity_about);
        imgAbout = (ImageView) findViewById(R.id.imgAbout);
        String url = "https://i.pinimg.com/originals/72/17/62/721762512268154368f443b9b2fa0049.png";

        Picasso.with(getApplicationContext()).load(url).into(imgAbout);

        Button btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadMainScreen();
            }
        });


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadMainScreen();
    }

}
