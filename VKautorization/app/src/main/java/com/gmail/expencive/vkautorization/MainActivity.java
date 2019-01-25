package com.gmail.expencive.vkautorization;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiFriends;
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[] {VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};

    private ListView listView;

    private VKList userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (VKSdk.isLoggedIn()) {
            showList();
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Предупреждение")
                    .setMessage("Необходимо произвести авторизацию.")
                    .setPositiveButton("Авторизация", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            VKSdk.login(MainActivity.this, scope);
                        }
                    }).create();
            alertDialog.show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                showList();
                // Пользователь успешно авторизовался
                Toast.makeText(getApplicationContext(), "Авторизация прошла удачно", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onError(VKError error) {
                 // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(getApplicationContext(), "Ошибка авторизации", Toast.LENGTH_SHORT).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void showList() {

        VKRequest requestUserName = VKApi.users().get();
        requestUserName.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);
                VKList listUserName = (VKList) response.parsedModel;
                userName = listUserName;
            }
        });

        listView = findViewById(R.id.list_view);
        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name,last_name"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList list = (VKList) response.parsedModel;

                ArrayList<String> finalList = new ArrayList<>();
                finalList.add("User Name: " + userName.get(0));
                finalList.add("Friend: " + list.get(0));
                finalList.add("Friend: " + list.get(1));
                finalList.add("Friend: " + list.get(2));
                finalList.add("Friend: " + list.get(3));
                finalList.add("Friend: " + list.get(4));

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, finalList);
                listView.setAdapter(arrayAdapter);




            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem mi = menu.add(0,1, 0, "Выйти из аккаунта");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        VKSdk.logout();
        finish();
        return super.onOptionsItemSelected(item);
    }

}
