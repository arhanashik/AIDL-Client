package com.w3engineers.core.aidlclient;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.w3engineers.core.IAdd;
import com.w3engineers.core.R;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etNum1, etNum2;
    private Button btnAdd, btnList, btnObject, btnCall;
    private TextView txtResult;

    private String TAG = "AIDL CLIENT";
    private String AIDL_SERVER_URI = "com.w3engineers.core.aidlserver";

    private IAdd addService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "Service Connected");
            addService = IAdd.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "Service Disconnected");
            addService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNum1 = findViewById(R.id.et_num1);
        etNum2 = findViewById(R.id.et_num2);
        btnAdd = findViewById(R.id.btn_add);
        btnList = findViewById(R.id.btn_list);
        btnObject = findViewById(R.id.btn_object);
        btnCall = findViewById(R.id.btn_call);
        txtResult = findViewById(R.id.txt_result);

        initConnection();

        btnAdd.setOnClickListener(this);
        btnList.setOnClickListener(this);
        btnObject.setOnClickListener(this);
        btnCall.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(serviceConnection != null) unbindService(serviceConnection);
    }

    private void initConnection() {
        if (addService == null) {
            Intent intent = new Intent();
            intent.setClassName(AIDL_SERVER_URI,  AIDL_SERVER_URI + ".AdditionService");
            // binding to remote service
            bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                if(addService != null){
                    int num1 = Integer.valueOf(etNum1.getText().toString());
                    int num2 = Integer.valueOf(etNum2.getText().toString());

                    try {
                        int res = addService.addNumbers(num1, num2);

                        txtResult.setText(num1 + " + " + num2 + " = " + res);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else txtResult.setText("Service not connected!");

                break;

            case R.id.btn_list:
                if(addService != null){
                    try {
                        String res = "String list";
                        List<String> strings = addService.getStringList();
                        if(strings == null) res = "List not initiated";
                        else {
                            if(strings.size() == 0) res = "Empty list";
                            else {
                                for(String s : strings)
                                    res += "\n" + s;
                            }
                        }

                        txtResult.setText(res);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else txtResult.setText("Service not connected!");

                break;

            case R.id.btn_call:
                if(addService != null){
                    try {
                        addService.placeCall("+8801764515461");

                        txtResult.setText("Calling....");
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                else txtResult.setText("Service not connected!");

                break;
        }
    }
}
