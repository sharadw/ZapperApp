package com.zapper.assessment.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zapper.assessment.R;
import com.zapper.assessment.adapter.SimpleItemRecyclerViewAdapter;
import com.zapper.assessment.model.PersonsModel;
import com.zapper.assessment.repository.PersonRepository;
import com.zapper.assessment.utils.AppConstants;
import com.zapper.assessment.utils.ProgressDialogHelper;
import com.zapper.assessment.viewmodel.PersonDatabaseViewModel;
import com.zapper.assessment.viewmodel.PersonViewModel;

import java.util.ArrayList;
import java.util.List;

public class MasterFragment extends Fragment {


    private PersonViewModel personViewModel;
    private RecyclerView rvRecycler;
    private PersonsModel.Person person;
    private boolean orientation;
    private ProgressDialogHelper mProgressDialogHelper;
    private PersonDatabaseViewModel personDBViewModel;


    public static MasterFragment getInstance(boolean orientation) {
        MasterFragment masterFragment = new MasterFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(AppConstants.TAG_orientation, orientation);
        masterFragment.setArguments(bundle);
        return masterFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        person = new PersonsModel.Person();
        personViewModel = new PersonViewModel(getActivity().getApplication(), person);
        personDBViewModel = new PersonDatabaseViewModel(getActivity().getApplication());
        Bundle bundle = getArguments();
        if (bundle != null) {
            orientation = bundle.getBoolean(AppConstants.TAG_orientation);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_master, container, false);
        rvRecycler = rootView.findViewById(R.id.rvRecycler);
        rvRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mProgressDialogHelper = new ProgressDialogHelper(getContext());
        mProgressDialogHelper.show();
        getDataFromDataBase();
        return rootView;
    }

    /*This method is responsible to observe data change in database
    If Database is empty get the data from API */
    private void getDataFromDataBase() {
        personDBViewModel.getAllData().observe(getViewLifecycleOwner(), new Observer<List<PersonsModel.Person>>() {
            @Override
            public void onChanged(List<PersonsModel.Person> personViewModels) {
                if (personViewModels != null && personViewModels.size() > 0) {
                    ArrayList<PersonViewModel> arrayList = new ArrayList<>();
                    for (PersonsModel.Person mPerson : personViewModels) {
                        arrayList.add(new PersonViewModel(getActivity().getApplication(), mPerson));

                    }
                    SimpleItemRecyclerViewAdapter customAdapter = new SimpleItemRecyclerViewAdapter(getActivity(), arrayList, orientation);
                    rvRecycler.setAdapter(customAdapter);
                    mProgressDialogHelper.hide();
                } else {
                    getPersonData();
                }
            }
        });
    }

    /*This method  observe data change on network*/
    private void getPersonData() {

        personViewModel.makeRequest().observe(getViewLifecycleOwner(), new Observer<ArrayList<PersonViewModel>>() {
            @Override
            public void onChanged(ArrayList<PersonViewModel> personViewModels) {
                SimpleItemRecyclerViewAdapter customAdapter = new SimpleItemRecyclerViewAdapter(getActivity(), personViewModels, orientation);
                rvRecycler.setAdapter(customAdapter);
                mProgressDialogHelper.hide();
            }
        });
    }


}
