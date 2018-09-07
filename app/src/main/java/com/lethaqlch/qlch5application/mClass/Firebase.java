package com.lethaqlch.qlch5application.mClass;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Firebase<T> {

    public void getInstance(String child, final List<T> list, @NonNull final Class<T> objectClass) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();
        myRef.child(child).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String s) {
                T o = dataSnapshot.getValue(objectClass);
                list.add(o);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public static   <T>  void Update (final Context context, String mkey, T o, String child){

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child(child).child(mkey).setValue(o, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null) {
                    Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Update thanh cong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   /* public static <T> boolean checkLike(List<T> arr*//*,Class<T> objectClass*//*,String strCcheck,String strCchecked) {
        boolean result = true;
        Iterator<T> iterator = arr.iterator();
        while (iterator.hasNext()) {
          T  object = iterator.next();
            if (strCcheck.equals(arr.)) {
                result = false;
            }
        }
        return result;
    }*/

}

