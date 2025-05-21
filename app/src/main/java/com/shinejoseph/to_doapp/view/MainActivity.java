package com.shinejoseph.to_doapp.view;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.setContent;
import com.shinejoseph.to_doapp.ui.theme.MainAppScreen;
import com.shinejoseph.to_doapp.ui.theme.ToDoAppTheme;

public class MainActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent {
            ToDoAppTheme {
                MainAppScreen();
            }
        }
    }
}
