package senac.ebookstore.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EbookFirebaseHelper {    DatabaseReference db;
    Boolean saved = null;
    List<Ebook> ebookList = new ArrayList<>();

    public EbookFirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public Boolean save(Ebook ebook) {
        if (ebook == null) {
            saved = false;
        } else {

            try {
                db.child("ebooks").push().setValue(ebookList);
                saved = true;
            } catch (DatabaseException e) {
                e.printStackTrace();
                saved = false;
            }

        }

        return saved;
    }

    //READ
    public List<Ebook> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return ebookList;
    }

    private void fetchData(DataSnapshot dataSnapshot) {
        ebookList.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Ebook ebook = ds.getValue(Ebook.class);
            ebookList.add(ebook);
        }
    }
   }
