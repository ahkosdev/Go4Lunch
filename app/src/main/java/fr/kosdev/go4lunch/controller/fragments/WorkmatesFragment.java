package fr.kosdev.go4lunch.controller.fragments;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.kosdev.go4lunch.Base.BaseActivity;
import fr.kosdev.go4lunch.R;
import fr.kosdev.go4lunch.api.WorkmateHelper;
import fr.kosdev.go4lunch.controller.activities.WorkmateRecyclerViewAdapter;
import fr.kosdev.go4lunch.model.Workmate;

public class WorkmatesFragment extends Fragment {


    @BindView(R.id.workmates_rcv)
    RecyclerView workmateRcv;

    private WorkmateRecyclerViewAdapter workmateAdapter;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workmates_fragment, container, false);
        ButterKnife.bind(this, view);
        this.configureRecyclerView();
        return view;
    }

    private void configureRecyclerView(){

        workmateAdapter = new WorkmateRecyclerViewAdapter(getOptionsForAdapter(WorkmateHelper.getWorkmates()));
        workmateRcv.setAdapter(workmateAdapter);

    }

    private FirestoreRecyclerOptions<Workmate> getOptionsForAdapter(Query query){

        return new FirestoreRecyclerOptions.Builder<Workmate>()
                .setQuery(query, Workmate.class)
                .setLifecycleOwner(this)
                .build();
    }



}
