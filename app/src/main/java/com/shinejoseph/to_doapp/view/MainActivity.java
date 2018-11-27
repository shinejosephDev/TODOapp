package com.shinejoseph.to_doapp.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shinejoseph.to_doapp.R;
import com.shinejoseph.to_doapp.data.entity.TaskEntity;
import com.shinejoseph.to_doapp.model.Task;
import com.shinejoseph.to_doapp.utils.Preferences;
import com.shinejoseph.to_doapp.view.viewmodel.TaskViewModel;

import java.util.ArrayList;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity implements TasksAdapter.TaskListener, ActionMode.Callback {

    private Preferences mPref;
    private TasksAdapter mTasksAdapter;

    private ArrayList<TaskEntity> mTasksList = new ArrayList<>();
    private int mSelectedItem = -1;

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private RecyclerView mRvTasks;
    private TextView mtvEmpty;

    private ActionMode mActionMode;
    private TaskViewModel mTaskViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        init();
    }

    private void bindViews() {
        mToolbar = findViewById(R.id.toolbar);
        mFab = findViewById(R.id.fab);
        mRvTasks = findViewById(R.id.rv_tasks);
        mtvEmpty = findViewById(R.id.tv_empty);
    }

    private void init() {
        setSupportActionBar(mToolbar);
        mPref = new Preferences(this);

        mTasksAdapter = new TasksAdapter(mTasksList, this);
        mRvTasks.setLayoutManager(new LinearLayoutManager(this));
        mRvTasks.setAdapter(mTasksAdapter);

        if (!mPref.getName().equals(""))
            getSupportActionBar().setTitle("Welcome " + mPref.getName());
        mFab.setOnClickListener(view
                -> addTask()
        );

        mTaskViewModel = ViewModelProviders.of(this).get(TaskViewModel.class);

        mTaskViewModel.getTasks().observe(this, taskEntities -> {
            if (taskEntities.size() > 0) {
                mtvEmpty.setVisibility(View.GONE);
                System.out.println("Data changed");
                System.out.println("taskEntities = " + taskEntities.size());
                mTasksList.clear();
                mTasksList.addAll(taskEntities);
                mTasksAdapter.notifyDataSetChanged();
            } else {
                mTasksList.clear();
                mTasksAdapter.notifyDataSetChanged();
                mtvEmpty.setVisibility(View.VISIBLE);
            }
        });
    }

    private void addTask() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setTitle("Enter task");
        alert.setView(edittext);

        alert.setPositiveButton("ADD", (dialog, whichButton) -> {
            String taskName = edittext.getText().toString();
            Task task = new Task();
            task.setId((int) System.currentTimeMillis());
            task.setTaskName(taskName);
            task.setCompleted(false);
            mTaskViewModel.insertTask(task);
        });

        alert.setNegativeButton("CANCEL", (dialog, whichButton) -> {
            // what ever you want to do with No option.
        });

        alert.show();
    }

    @Override
    public void onLongTap(int position) {
        mSelectedItem = position;
        if (mActionMode == null)
            // there are some selected items, start the actionMode
            mActionMode = startSupportActionMode(this);
        else mActionMode.finish();
        if (mActionMode != null)
            //set action mode title on item selection
            mActionMode.setTitle(String.valueOf(mTasksList.get(position).getTask_name() + " selected"));
    }

    @Override
    public void onCheckBoxTap(int position, boolean isCompeted) {
        TaskEntity taskEntity = mTasksList.get(position);
        taskEntity.setIs_completed(isCompeted);
        mTaskViewModel.updateTask(taskEntity);
    }

    @Override
    public void onTap(int position) {

    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_task_long, menu);//Inflate the menu over action mode
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        mActionMode.finish();
        mActionMode = null;
        switch (item.getItemId()) {
            case R.id.action_edit:
                editTask();
                break;
            case R.id.action_delete:
                deleteTask();
                break;
        }
        return true;
    }

    private void editTask() {
        TaskEntity taskEntity = mTasksList.get(mSelectedItem);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(this);
        alert.setTitle("Edit task");
        alert.setView(edittext);
        edittext.setText(taskEntity.getTask_name());
        alert.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String taskName = edittext.getText().toString();
                TaskEntity task = new TaskEntity();
                task.setId(taskEntity.getId());
                task.setTask_name(taskName);
                task.setIs_completed(taskEntity.isIs_completed());
                mTaskViewModel.updateTask(task);
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
            }
        });

        alert.show();
    }

    private void deleteTask() {
        if (mSelectedItem >= 0)
            mTaskViewModel.delete(mTasksList.get(mSelectedItem));
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mActionMode.finish();
        mActionMode = null;
    }
}
