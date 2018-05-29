package com.segmentify.segmentifyandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.segmentify.segmentifyandroidsdk.SegmentifyManager;
import com.segmentify.segmentifyandroidsdk.model.UserChangeModel;
import com.segmentify.segmentifyandroidsdk.model.UserModel;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btLogin = (Button) findViewById(R.id.btLogin);
        Button btLoginAs = (Button) findViewById(R.id.btLoginAs);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UserModel model = new UserModel();
                model.setEmail("user@example.com");
                model.setUsername("John Doe");

                SegmentifyManager.INSTANCE.sendUserUpdate(model);

                UserChangeModel model_ = new UserChangeModel();
                model_.setUserId("12345");

                SegmentifyManager.INSTANCE.sendChangeUser(model_);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        btLoginAs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });



    }
}
