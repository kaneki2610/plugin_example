
package kaneki.com.fluttervnpt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.startactivitylibrary.NewDesign;


public class SecondActivity extends AppCompatActivity implements NewDesign.OnHeadlineSelectedListener {
	ImageView btnBackFlutterView;
	String deviceInfo = "";
	String model = "";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);

		Intent intent = getIntent();
		model = intent.getStringExtra("type");

		btnBackFlutterView = findViewById(R.id.btnOnBackFlutterView);
		handleClick();
		FragmentManager fragmentManager = getSupportFragmentManager();
		NewDesign newDesign = (NewDesign) fragmentManager.findFragmentById(R.id.fragment);
		newDesign.setModel(model);
	}

	@Override
	public void onAttachFragment(@NonNull Fragment fragment) {
		super.onAttachFragment(fragment);
		if (fragment instanceof NewDesign) {
			NewDesign headlinesFragment = (NewDesign) fragment;
			headlinesFragment.setOnHeadlineSelectedListener(this);
		}
	}

	public void handleClick() {
		btnBackFlutterView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Intent data = new Intent();
				data.putExtra("deviceInfo", deviceInfo);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
	}

	@Override
	public void onGetValue(String value) {
		deviceInfo = value;
	}
}
























/*package kaneki.com.fluttervnpt;

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
}*/
