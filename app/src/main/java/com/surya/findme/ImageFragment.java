package com.surya.findme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Surya on 19-05-2017.
 */

public class ImageFragment extends Fragment {

    @BindView(R.id.current_item)
    TextView mCurrent_item;
    private Unbinder unbinder;

    public ImageFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.image_fragment,container,false);

        Bundle bundle = getArguments();

        unbinder = ButterKnife.bind(this,rootView);
        if (bundle!=null){

            mCurrent_item.setText(String.valueOf(bundle.getInt("position")+1));

        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
