package com.example.baking.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.baking.R;
import com.example.baking.model.Recipe;
import com.example.baking.service.Service;
import com.example.baking.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by diegocotta on 19/10/2018.
 */

public class MainViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recipe>> receipes;

    private MainViewModelListener listener;

    public MainViewModel(@NonNull Application application) {
        super(application);
        receipes = new MutableLiveData<>();
    }

    public void getRecipesFromService() {
        if (Utils.hasInternet(getApplication())) {
            Service.retrofit.create(Service.class).getRecipes().enqueue(new Callback<List<Recipe>>() {
                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    receipes.setValue(response.body());
                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    listener.onError(null);
                }
            });
        } else {
            listener.onError(getApplication().getApplicationContext().getString(R.string.no_connection));
        }
    }

    public MutableLiveData<List<Recipe>> getReceipes() {
        return receipes;
    }

    public void setReceipes(MutableLiveData<List<Recipe>> receipes) {
        this.receipes = receipes;
    }

    public MainViewModelListener getListener() {
        return listener;
    }

    public void setListener(MainViewModelListener listener) {
        this.listener = listener;
    }

    public interface MainViewModelListener {
        void onError(String error);
    }

}
