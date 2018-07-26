package com.felipe.felipe.chuckfacts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout mainLayout;
    private TextView joke_text;
    private TextView seta;
    private Button button;
    private Bitmap current_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        joke_text = (TextView) findViewById(R.id.joke_text);
        seta = (TextView) findViewById(R.id.seta);
        button = (Button) findViewById(R.id.btn_get_joke);

        joke_text.setMovementMethod(new ScrollingMovementMethod());

        if(has_joke_cache("c_f_last_joke")){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader( new FileInputStream(getCacheDir().getPath() + "/c_f_last_joke.txt")));
                String joke = "";
                String line = reader.readLine();
                while(line != null){
                    joke += line;
                    line = reader.readLine();
                }
                joke_text.setText(joke);
            } catch (Exception e) {
                Log.e("joke-cache", "A error ocurred when oppening the last joke.");
            }
        }
        if(has_img_cache("c_f_last_img")) {
            try {
                FileInputStream reader = new FileInputStream(getCacheDir() + "/c_f_last_img.png");
                Bitmap img = BitmapFactory.decodeStream(reader);
                if (img != null) {
                    set_img_joke(img);
                }
            } catch (Exception e) {
                Log.e("img-cache", "A error ocurred when oppening the last img.");
            }
        }
    }
    protected void get_joke(View v){
        joke_text.setText("Carregando...");
        button.setClickable(false);

        Thread requisicao = new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    URL url = new URL("https://api.chucknorris.io/jokes/random");
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                    conexao.setRequestMethod("GET");
                    conexao.setDoInput(true);
                    conexao.connect();

                    BufferedReader entrada = new BufferedReader(new InputStreamReader( conexao.getInputStream()));
                    StringBuffer aux = new StringBuffer();
                    String linha = entrada.readLine();

                    while(linha != null){
                        aux.append(linha);
                        linha = entrada.readLine();
                    }
                    entrada.close();
                    conexao.disconnect();

                    final String aux2 = aux.toString();
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            try{
                                JSONObject resposta = new JSONObject(aux2);
                                joke_text.setText(resposta.getString("value"));

                                load_image(resposta.getString("icon_url"), resposta.getString("id"));
                                button.setClickable(true);

                            }catch (Exception e){
                                   Log.e("Json Exception", ""+e.toString());
                            }
                        }
                    });

                }catch(Exception e){
                    Log.e("connection exception", ""+e.toString());
                }
            }
        });
        requisicao.start();
    }

    protected void load_image(final String img_url,final String id){
        Thread downloadImage = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL(img_url);
                    HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
                    conexao.setRequestMethod("GET");
                    conexao.setDoInput(true);
                    conexao.connect();

                    final Bitmap img_bitmap = BitmapFactory.decodeStream(conexao.getInputStream());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            set_img_joke(img_bitmap);
                        }
                    });
                }catch (Exception e){
                    Log.e("img-dowload exception", ""+e.getMessage());
                }
            }
        }); downloadImage.start();
    }
    protected void set_img_joke(Bitmap img_bitmap){

        current_img = img_bitmap;

        ImageView img = new ImageView(getBaseContext());
        img.setImageBitmap(img_bitmap);
        img.setId(View.generateViewId());
        ConstraintLayout.LayoutParams lp = new ConstraintLayout.LayoutParams(200, 200);
        img.setLayoutParams(lp);
        img.setAdjustViewBounds(true);
        mainLayout.addView(img);

        ConstraintSet c_set = new ConstraintSet();
        c_set.clone(mainLayout);
        c_set.connect(img.getId(), ConstraintSet.TOP, seta.getId(), ConstraintSet.BOTTOM, 8);
        c_set.connect(img.getId(), ConstraintSet.START, seta.getId(), ConstraintSet.START);
        c_set.connect(img.getId(), ConstraintSet.END, seta.getId(), ConstraintSet.END);
        c_set.applyTo(mainLayout);

    }

    protected void save_cache_img(final Bitmap btmp,final String name){
        Thread cacher = new Thread(new Runnable() {
            @Override
            public void run() {
                String fileName = "/"+ name+".png";
                ByteArrayOutputStream b_out = new ByteArrayOutputStream();
                btmp.compress(Bitmap.CompressFormat.PNG, 100, b_out);
                byte[] buffer = b_out.toByteArray();
                try{
                    FileOutputStream writer = new FileOutputStream(getCacheDir().getPath()+fileName);
                    writer.write(buffer);
                    writer.close();
                }catch(Exception e){
                    Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_SHORT);
                }

            }
        }); cacher.start();
    }

    protected void save_cache_joke(String name, String joke){
        try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(getCacheDir().getPath()+"/"+name+".txt")));
            writer.write(joke);
            writer.flush();
            writer.close();
        }catch(Exception e){
            Log.e("joke-text", "error saving cache for this joke");
        }
    }

    protected boolean has_img_cache(String name){
        return new File(getCacheDir()+"/"+name+".png").exists();
    }
    protected boolean has_joke_cache(String name){
        return new File(getCacheDir()+"/"+name+".txt").exists();
    }

    @Override
    protected void onStop() {
        super.onStop();

        save_cache_img(current_img, "c_f_last_img");
        save_cache_joke("c_f_last_joke",joke_text.getText().toString());
    }

    @Override
    protected void onDestroy() {

        save_cache_img(current_img, "c_f_last_img");
        save_cache_joke("c_f_last_joke",joke_text.getText().toString());

        super.onDestroy();
    }
}