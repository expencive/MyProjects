package com.gmail.expencive.vkautorization;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
import com.vk.sdk.api.model.VKList;
import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private String[] scope = new String[] {VKScope.MESSAGES, VKScope.FRIENDS, VKScope.WALL};

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (VKSdk.isLoggedIn()) {
            showList();
        }else {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Предупреждение")
                    .setMessage("Необходимо произвести авторизацию")
                    .setPositiveButton("Авторизация", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            VKSdk.login(MainActivity.this, scope);

                        }
                    }).create();
            alertDialog.show();
        }


        //String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        //Log.e("MyLog", Arrays.asList(fingerprints).toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                listView = findViewById(R.id.list_view);

                VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name,last_name"));
                request.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        VKList list = (VKList) response.parsedModel;

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                        listView.setAdapter(arrayAdapter);
                    }
                });
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

        listView = findViewById(R.id.list_view);

        VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "first_name,last_name"));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKList list = (VKList) response.parsedModel;

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, list);
                listView.setAdapter(arrayAdapter);
            }
        });

    }
}
