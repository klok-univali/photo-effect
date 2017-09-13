package br.com.klokenterprise.photoeffect;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditPhoto extends AppCompatActivity implements View.OnClickListener{
    private Button btnSalvar;
    private ImageView imgCentral;
    private ImageButton filter01;
    private ImageButton filter02;
    private ImageButton filter03;
    private ImageButton filter04;
    private ImageButton filter05;
    private ImageButton filter06;
    private ImageButton filter07;

    private Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_photo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        imgCentral = (ImageView) findViewById(R.id.imgCenter);

        filter01 = (ImageButton) findViewById(R.id.filter01);
        filter02 = (ImageButton) findViewById(R.id.filter02);
        filter03 = (ImageButton) findViewById(R.id.filter03);
        filter04 = (ImageButton) findViewById(R.id.filter04);
        filter05 = (ImageButton) findViewById(R.id.filter05);
        filter06 = (ImageButton) findViewById(R.id.filter06);
        filter07 = (ImageButton) findViewById(R.id.filter07);

        btnSalvar.setOnClickListener(this);

        filter01.setOnClickListener(this);
        filter02.setOnClickListener(this);
        filter03.setOnClickListener(this);
        filter04.setOnClickListener(this);
        filter05.setOnClickListener(this);
        filter06.setOnClickListener(this);
        filter07.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey("IMAGE")){
            img = (Bitmap) bundle.get("IMAGE");
            imgCentral.setImageBitmap(img);

            atualizaMiniaturas();
        }

        if (bundle != null && bundle.containsKey("URL")) {
            String imageUri = (String) bundle.get("URL");

            img = (BitmapFactory.decodeFile(imageUri));
            imgCentral.setImageBitmap(img);

            atualizaMiniaturas();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.filter01:
                imgCentral.setImageBitmap(img);
                break;

            case R.id.filter02:
                imgCentral.setImageBitmap(Filtros.doGreyscale(img));
                break;

            case R.id.filter03:
                imgCentral.setImageBitmap(Filtros.doInvert(img));
                break;

            case R.id.filter04:
                imgCentral.setImageBitmap(Filtros.doColorFilter(img,1.0,0,0));
                break;

            case R.id.filter05:
                imgCentral.setImageBitmap(Filtros.doColorFilter(img,0,1.0,0));
                break;

            case R.id.filter06:
                imgCentral.setImageBitmap(Filtros.doColorFilter(img,0,0,1.0));
                break;

            case R.id.filter07:
                imgCentral.setImageBitmap(Filtros.emboss(img));
                break;

            case R.id.btnSalvar:
                save();
                finish();
                break;
        }
    }
    //doColorFilter(Bitmap src, double red, double green, double blue)
    private void atualizaMiniaturas(){
        filter01.setImageBitmap(img);
        filter02.setImageBitmap(Filtros.doGreyscale(img));
        filter03.setImageBitmap(Filtros.doInvert(img));
        filter04.setImageBitmap(Filtros.doColorFilter(img,1.0,0,0));
        filter05.setImageBitmap(Filtros.doColorFilter(img,0,1.0,0));
        filter06.setImageBitmap(Filtros.doColorFilter(img,0,0,1.0));
        filter07.setImageBitmap(Filtros.emboss(img));
    }

    private void save(){
        String timeStamp= new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File pasta = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");

        File f = new File(pasta.getPath() + "/photo-effect-" + timeStamp + ".png");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);

            BitmapDrawable bmp = (BitmapDrawable) imgCentral.getDrawable();
            Bitmap bitmap = bmp.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG,90,fos);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
