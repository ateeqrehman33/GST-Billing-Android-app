package technologies.d.digital.znumerique;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {




    EditText editPassword, editName;
    Button btnSignIn ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);







        editName=(EditText)findViewById(R.id.editName);
        editPassword=(EditText)findViewById(R.id.editPassword);

        btnSignIn=(Button)findViewById(R.id.btnSignIn);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userLogin();
            }
        });








    }





    private void userLogin() {



        if(TextUtils.isEmpty(editName.getText())) {
            editName.setError("Username Cannot be Empty");
            return;
        }

        if(TextUtils.isEmpty(editPassword.getText())) {
            editPassword.setError("Password Cannot be Empty");
            return;
        }

else {

            String username = editName.getText().toString();
            String password = editPassword.getText().toString();
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, username, password);


        }
    }





}
