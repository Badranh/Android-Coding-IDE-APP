package net.cryptobrewery.mediumarticleproject.ChooseProject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.cryptobrewery.mediumarticleproject.R;
import net.cryptobrewery.mediumarticleproject.WorkingProject.WorkingProjectActivity;

public class MainActivity extends AppCompatActivity implements MainContract.ViewContract{

    private MainContract.UserActions presenter;
    private ProjectAdapter adapter;
    private ProgressDialog dialog;
    private CardView cardCreateProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter(this);
        RecyclerView projectsRecyclerView = findViewById(R.id.recycler_projects);
        cardCreateProject=findViewById(R.id.cardCreateProject);
        cardCreateProject.setOnClickListener(v->{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Please Put Project Name");
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.filename,null,false);
            EditText fileNameEdt = view.findViewById(R.id.editText);
            fileNameEdt.setHint("Project Name");
            dialog.setView(view);
            dialog.setPositiveButton("Create", (dialog12, which) -> presenter.createProject(fileNameEdt.getText().toString()))
                    .setNegativeButton("Cancel", (dialog1, which) -> showToast("Canceled"));
            dialog.show();
        });
        setupRecyclerView(projectsRecyclerView);
    }

    private void setupRecyclerView(RecyclerView recyclerView){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new ProjectAdapter(presenter) ;
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoading(String projectName) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Projects");
        dialog.setMessage("Loading "+projectName+" Please wait...");
        dialog.show();
    }

    @Override
    public void refreshRecycler() {
        runOnUiThread(() -> adapter.notifyDataSetChanged());
    }

    @Override
    public void moveToWorkingProject() {
        startActivity(new Intent(MainActivity.this, WorkingProjectActivity.class));
        finish();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void cancelLoading() {
        if(dialog!=null && dialog.isShowing())
            dialog.cancel();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        if(dialog!= null && dialog.isShowing())
            dialog.cancel();
        dialog=null;
    }
}
