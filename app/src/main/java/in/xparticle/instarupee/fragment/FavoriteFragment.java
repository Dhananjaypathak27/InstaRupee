package in.xparticle.instarupee.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import in.xparticle.instarupee.R;

public class FavoriteFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Toast.makeText(getActivity(), "Currently editing add feature is not available", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }
}