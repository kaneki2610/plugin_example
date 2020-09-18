package kaneki.com.fluttervnpt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import io.flutter.app.FlutterActivity;

public class SecondActivity extends FlutterActivity {
    ImageView btnBackFlutterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        findById();
        onHandleClick();
    }

    private void findById() {
        btnBackFlutterView = findViewById(R.id.btnOnBackFirstActivity);
    }

    private void onHandleClick() {
        btnBackFlutterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent data = new Intent();
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }
}
