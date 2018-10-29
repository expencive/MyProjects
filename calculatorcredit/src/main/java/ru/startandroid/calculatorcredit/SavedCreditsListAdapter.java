package ru.startandroid.calculatorcredit;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import ru.startandroid.calculatorcredit.Database.DatabaseHelper;

import static ru.startandroid.calculatorcredit.R.id.parent;

public class SavedCreditsListAdapter extends AppCompatActivity {
    private static final int CM_DELETE_ID = 1;
    private static final int CM_VIEW_CREDIT_ID = 2;
    DatabaseHelper myDb;
    ListView lvData;
    SimpleCursorAdapter scAdapter;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_credits_list_adapter);

        // открываем подключение к БД
        myDb = new DatabaseHelper(this);



        // получаем курсор
        res = myDb.getAllData();
        startManagingCursor(res);

        // формируем столбцы сопоставления
        String[] from = new String[] { myDb.COL_2, myDb.COL_3, myDb.COL_4, myDb.COL_5, myDb.COL_1 };
        int[] to = new int[] { R.id.tvTitle, R.id.tvLoanAmount, R.id.tvInterestRate, R.id.tvLoanPeriod, R.id.tvId };

        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item_sc_adapter, res, from, to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(lvData);



        lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView tvLoanAmount = (TextView) view.findViewById(R.id.tvLoanAmount);

                TextView tvInterestRate = (TextView) view.findViewById(R.id.tvInterestRate);

                TextView tvLoanPeriod = (TextView) view.findViewById(R.id.tvLoanPeriod);

                Intent intent = new Intent(SavedCreditsListAdapter.this, GraphPayments.class);
                intent.putExtra("tvLoanAmount", String.valueOf(tvLoanAmount.getText()));
                intent.putExtra("tvInterestRate", String.valueOf(tvInterestRate.getText()));
                intent.putExtra("tvLoanPeriod", String.valueOf(tvLoanPeriod.getText()));
                startActivity(intent);


            }
        });


    }




    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Удалить запись");


    }

    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == CM_DELETE_ID) {
            // получаем из пункта контекстного меню данные по пункту списка
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            // извлекаем id записи и удаляем соответствующую запись в БД
            myDb.deleteData(String.valueOf(acmi.id));
            // обновляем курсор
            res.requery();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    protected void onDestroy() {
        super.onDestroy();
        // закрываем подключение при выходе
        myDb.close();
    }
}
