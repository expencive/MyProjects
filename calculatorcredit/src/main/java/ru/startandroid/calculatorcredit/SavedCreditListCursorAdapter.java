package ru.startandroid.calculatorcredit;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import ru.startandroid.calculatorcredit.Database.DatabaseHelper;

public class SavedCreditListCursorAdapter extends AppCompatActivity {
    ListView lvData;
    SimpleCursorAdapter scAdapter;
    Cursor res;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_credit_list_cursor_adapter);

        myDb = new DatabaseHelper(this);
        Cursor res = myDb.getAllData();
        startManagingCursor(res);

        String[] from = new String[] { myDb.COL_2, myDb.COL_3, myDb.COL_4, myDb.COL_5 };
        int[] to = new int[] { R.id.ivImg, R.id.tvText };


        // создааем адаптер и настраиваем список
        scAdapter = new SimpleCursorAdapter(this, R.layout.item_sc_adapter, res, from, to);
        lvData = (ListView) findViewById(R.id.lvData);
        lvData.setAdapter(scAdapter);

        // добавляем контекстное меню к списку
        registerForContextMenu(lvData);

    }
}
