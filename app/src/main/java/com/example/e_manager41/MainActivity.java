package com.example.e_manager41;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.example.e_manager41.Adapter.TransactionsAdapter;
import com.example.e_manager41.databinding.ActivityMainBinding;
import com.example.e_manager41.model.Transaction;
import com.example.e_manager41.utils.Constants;
import com.example.e_manager41.utils.Helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    Calendar calendar;
    Realm realm;
    /*
    0 = Daily
    1 = Monthly
    2 = Calendar
    3 = Summary
    4 = Notes
     */


    //public MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpDatabase();

        setSupportActionBar(binding.toolBar);
        getSupportActionBar().setTitle("Transactions");

        calendar = Calendar.getInstance();
         updateDate();

         binding.nextDateBtn.setOnClickListener(v -> {
             calendar.add(Calendar.DATE,1);
             updateDate();

         });

         binding.previousDateBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 calendar.add(Calendar.DATE,-1);
                 updateDate();
             }
         });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddTransactionFragment().show(getSupportFragmentManager(),null);
            }
        });
     //  ArrayList<Transaction>transactions=new ArrayList<>();

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(
                new Transaction(Constants.INCOME,"Business","Bank",
                        "Some note here",new Date(),new Date().getTime(),500));
       realm.copyToRealmOrUpdate(
                new Transaction(Constants.INCOME,"Business","Bank",
                        "Some note here",new Date(),new Date().getTime(),500));
        realm.copyToRealmOrUpdate(
                new Transaction(Constants.INCOME,"Business","Bank",
                        "Some note here",new Date(),new Date().getTime(),500));
        realm.copyToRealmOrUpdate(
                new Transaction(Constants.INCOME,"Business","Bank",
                        "Some note here",new Date(),new Date().getTime(),500));
        realm.copyToRealmOrUpdate(
                new Transaction(Constants.INCOME,"Business","Bank",
                        "Some note here",new Date(),new Date().getTime(),500));

        realm.commitTransaction();
        RealmResults<Transaction>transactions=realm.where(Transaction.class).findAll();


        TransactionsAdapter transactionsAdapter=new TransactionsAdapter(this,transactions );
        binding.transactionsList.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionsList.setAdapter(transactionsAdapter);



    }

    void setUpDatabase()
    {
        Realm.init(this);
        realm=Realm.getDefaultInstance();

    }
    void updateDate() {

      //  SimpleDateFormat dateFormat= new SimpleDateFormat("dd MMMM, yyyy");


        binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
       /* if(Constants.SELECTED_TAB == Constants.DAILY) {
            binding.currentDate.setText(Helper.formatDate(calendar.getTime()));
        } else if(Constants.SELECTED_TAB == Constants.MONTHLY) {
            binding.currentDate.setText(Helper.formatDateByMonth(calendar.getTime()));
        }
        viewModel.getTransactions(calendar);*/
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}