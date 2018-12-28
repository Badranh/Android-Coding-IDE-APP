package net.cryptobrewery.mediumarticleproject.WorkingProject;

import android.app.AlertDialog;
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
import com.jakewharton.rxbinding2.widget.RxTextView;

import net.cryptobrewery.mediumarticleproject.ChooseProject.MainActivity;
import net.cryptobrewery.mediumarticleproject.MainModel.WorkingProject;
import net.cryptobrewery.mediumarticleproject.R;
import net.cryptobrewery.syntaxview.SyntaxView;

import io.reactivex.disposables.Disposable;


public class WorkingProjectActivity extends AppCompatActivity implements Contract.ViewContract {
    private Contract.UserActions presenter;
    private FilesAdapter adapter;
    private SyntaxView syntaxView;
    private EditText fileNameEdt;
    private Disposable subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_project);
        RecyclerView recyclerView = findViewById(R.id.rec_files);
        CardView saveCard = findViewById(R.id.card_save_cur_file);
        CardView addCard = findViewById(R.id.card_add_new_file);
        presenter = new Presenter(this);
        setupRecyclerView(recyclerView);
        syntaxView = findViewById(R.id.syntaxView);
        subscribe = RxTextView.afterTextChangeEvents(syntaxView.getCode()).subscribe(textViewAfterTextChangeEvent -> {
            presenter.codeChanged();
        });
        addCard.setOnClickListener(v->{
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Please Put File Name With Extension");
            View view = LayoutInflater.from(WorkingProjectActivity.this).inflate(R.layout.filename,null,false);
            fileNameEdt = view.findViewById(R.id.editText);
            dialog.setView(view);
            dialog.setPositiveButton("Create", (dialog12, which) -> presenter.addFile(fileNameEdt.getText().toString()))
                    .setNegativeButton("Cancel", (dialog1, which) -> showToast("Canceled"));
            dialog.show();
        });
        saveCard.setOnClickListener(v -> {presenter.saveCurFile();});

    }

    private void setupRecyclerView(RecyclerView recyclerView){
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        adapter = new FilesAdapter(presenter) ;
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setCode(String code) {
        syntaxView.getCode().setText(code);
    }

    @Override
    public String getCode() {
        return syntaxView.getCode().getText().toString();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshFiles() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteFileWarning() {
        showToast("File Deleted");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        if(!subscribe.isDisposed())
            subscribe.dispose();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(WorkingProjectActivity.this,MainActivity.class));
        finish();
    }
}
