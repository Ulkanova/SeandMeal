package com.ulkanova.dao;

import android.os.AsyncTask;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ulkanova.model.Plato;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface PlatoDao {
    @Insert
    void insertar(Plato plato);

    @Delete
    void borrar(Plato plato);

    @Update
    void actualizar(Plato plato);

    @Query("SELECT * FROM plato WHERE platoId = :id LIMIT 1")
    Plato buscar(String id);

    @Query("SELECT * FROM plato")
    List<Plato> buscarTodos();
}

class BuscarPlatos extends AsyncTask<String, Void, List<Plato>> {

    private PlatoDao dao;
    private OnPlatoResultCallback callback;

    public BuscarPlatos(PlatoDao dao, OnPlatoResultCallback context) {
        this.dao = dao;
        this.callback = context;
    }

    @Override
    protected List<Plato> doInBackground(String... strings) {
        List<Plato> platos = dao.buscarTodos();
        return platos;
    }

    @Override
    protected void onPostExecute(List<Plato> platos) {
        super.onPostExecute(platos);
        callback.onResult(platos);
    }
}
interface OnPlatoResultCallback {
    void onResult(List<Plato> plato);
}

class BuscarPlatoById extends AsyncTask<String, Void, Plato> {

    private PlatoDao dao;
    private OnPlatoResultCallback callback;

    public BuscarPlatoById(PlatoDao dao, OnPlatoResultCallback context) {
        this.dao = dao;
        this.callback = context;
    }

    @Override
    protected Plato doInBackground(String... strings) {
        Plato plato = dao.buscar(strings[0]);
        return plato;
    }

    @Override
    protected void onPostExecute(Plato plato) {
        super.onPostExecute(plato);
        List<Plato> lista = new ArrayList<>();
        lista.add(plato);
        callback.onResult(lista);
    }
}