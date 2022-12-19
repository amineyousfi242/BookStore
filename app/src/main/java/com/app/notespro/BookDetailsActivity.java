package com.app.notespro;

import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import org.jetbrains.annotations.NotNull;

public class BookDetailsActivity extends AppCompatActivity {

    EditText titleEditText,priceEditeText;
    ImageButton saveBookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        titleEditText = findViewById(R.id.books_title_text);
        priceEditeText = findViewById(R.id.books_price_text);
        saveBookBtn = findViewById(R.id.save_book_btn);
        saveBookBtn.setOnClickListener((v)->saveBook());



    }
    //--
    void saveBook(){
        String bookTitle = titleEditText.getText().toString();
        float bookPrice = Float.valueOf(priceEditeText.getText().toString());
        //String bookPrice = priceEditeText.getText().toString();
        if(bookTitle == null || bookTitle.isEmpty()){
            titleEditText.setError("Title is required");
            return;
        }
        /*if ( bookPrice < 1 ){
            priceEditeText.setError("Price is required");
            return;
        }*/

        Book book = new Book();
        book.setTitle(bookTitle);
        book.setPrice(bookPrice);
        book.setTimestamp(Timestamp.now());

        saveBookToFirebase(book);
    }
    //--
    void saveBookToFirebase(Book book){
        DocumentReference documentReference;
        documentReference = Utility.getCollectionReferenceForBooks().document();

        documentReference.set(book).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                if(task.isSuccessful()){
                    // note added
                    Utility.showToast(BookDetailsActivity.this,"Book added successfully");
                    finish();
                }else {
                    Utility.showToast(BookDetailsActivity.this,"Failed while adding book");
                }
            }
        });
    }
}