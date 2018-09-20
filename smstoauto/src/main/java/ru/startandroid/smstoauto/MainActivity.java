package ru.startandroid.smstoauto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);


        // получаем элемент ListView
        ListView tvMessages = (ListView) findViewById(R.id.tvMessages);

        // получаем ресурс
        String[] messages = getResources().getStringArray(R.array.messages);

        // создаем адаптер
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);

        // устанавливаем для списка адаптер
        tvMessages.setAdapter(adapter);

        // добвляем для списка слушатель
        tvMessages.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String telefon = sp.getString("telefon", "");
                String servicekey = sp.getString("serviceKey", "");
                String toSms="smsto:"+telefon;
                String messageText= "Введите номер телефона и код управления в Настройках";
                Intent sms=new Intent(Intent.ACTION_SENDTO, Uri.parse(toSms));
                switch (position) {
                    case 0:
                        messageText= "01# " + servicekey;
                        break;
                    case 1:
                        messageText= "02# " + servicekey;
                        break;
                    case 2:
                        messageText= "03# " + servicekey;
                        break;
                    case 3:
                        messageText= "04# " + servicekey;
                        break;
                    case 4:
                        messageText= "05# " + servicekey;
                        break;
                    case 5:
                        messageText= "06# " + servicekey;
                        break;
                    case 6:
                        messageText= "07# " + servicekey;
                        break;
                    case 7:
                        messageText= "08# " + servicekey;
                        break;
                    case 8:
                        messageText= "09# " + servicekey;
                        break;
                    case 9:
                        messageText= "10# " + servicekey;
                        break;
                    case 10:
                        messageText= "17# " + servicekey;
                        break;
                    case 11:
                        messageText= "18# " + servicekey;
                        break;
                    case 12:
                        messageText= "19# " + servicekey;
                        break;
                    case 13:
                        messageText= "20# " + servicekey;
                        break;

                }
                sms.putExtra("sms_body", messageText);
                startActivity(sms);
                Toast.makeText(getApplicationContext(), "Нажмите кнопку отправки СМС", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0,1, 0, "Настройки");
        mi.setIntent(new Intent(this, PrefActivity.class));
        return super.onCreateOptionsMenu(menu);
    }

}
