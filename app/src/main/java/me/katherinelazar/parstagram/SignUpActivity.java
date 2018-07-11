package me.katherinelazar.parstagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        usernameInput = findViewById(R.id.new_username);
        passwordInput = findViewById(R.id.new_password);

        submit = findViewById(R.id.new_user_submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();

                signUp(username, password);
            }
        });
    }

    private void signUp(String username, String password) {

        // Create the ParseUser
        ParseUser user = new ParseUser();
        user.setUsername(username);
        user.setPassword(password);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    // Hooray! Let them use the app now.
                    Log.d("SignUpActivity", "Sign Up Successful");
                    Toast.makeText(SignUpActivity.this, "New user created!", Toast.LENGTH_LONG).show();

                    final Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    // Sign up didn't succeed. Look at the ParseException
                    // to figure out what went wrong
                    Log.e("SignUpActivity", "Sign Up failure");
                    e.printStackTrace();
                }
            }
        });
    }
}
