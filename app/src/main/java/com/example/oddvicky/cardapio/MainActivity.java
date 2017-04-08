package com.example.oddvicky.cardapio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oddvicky.cardapio.adapter.ListAndroidAdapter;
import com.example.oddvicky.cardapio.dao.ComidaDAO;
import com.example.oddvicky.cardapio.listener.ClickListener;
import com.example.oddvicky.cardapio.listener.RecyclerTouchListener;
import com.example.oddvicky.cardapio.model.Comida;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button btnExcluir;
    private ListAndroidAdapter adapter;
    RecyclerView rvLista;
    ComidaDAO comidaDAO = new ComidaDAO(this);
    List<Comida> comidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvLista = (RecyclerView) findViewById(R.id.rvLista);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Chama a tela de cadastro e aguarda um retorno que irá chamar o método onActivityResult
                startActivityForResult(new Intent(MainActivity.this,
                                NovaComidaActivity.class),
                        NovaComidaActivity.CODE_NOVA_COMIDA);
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView)
                findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        comidas = comidaDAO.getAll();

        //Adicionando o listener para saber qual item foi clicado e deletar o item
        rvLista.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rvLista, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Comida comida = comidas.get(position);
                ComidaDAO comidaDAO = new ComidaDAO(getApplicationContext());
                comidaDAO.remove(comida.getId());
                finish();
                startActivity(getIntent());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        setUpLista(comidas);
    };

    private void setUpLista(List<Comida> lista) {
        adapter = new ListAndroidAdapter(this,lista);
        rvLista.setLayoutManager(new LinearLayoutManager(this));
        rvLista.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {

            Toast.makeText(MainActivity.this, "Cancelado", Toast.LENGTH_LONG).show();
        } else if(requestCode == NovaComidaActivity.CODE_NOVA_COMIDA) {
            ComidaDAO comidaDAO = new ComidaDAO(this);
            List<Comida> comidas = comidaDAO.getAll();
            setUpLista(comidas);
        }
    }

    private void carregaComidas() {

        /*
        tvComidas.setText("");
        ComidaDAO comidaDAO = new ComidaDAO(this);
        StringBuilder sb= new StringBuilder();
        List<Comida> comidas = comidaDAO.getAll();

        for(Comida c : comidas) {
            sb= new StringBuilder(tvComidas.getText());
            sb.append("\n");
            sb.append(c.getNome());
            sb.append(" - ");
            sb.append(c.getRestaurante().getNome());
            sb.append(" - ");
            sb.append(c.getPreco());

            tvComidas.setText(sb.toString());
            //btnExcluir.setText("test");
        }
        */

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
