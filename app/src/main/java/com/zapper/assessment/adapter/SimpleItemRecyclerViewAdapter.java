package com.zapper.assessment.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zapper.assessment.MainActivity;
import com.zapper.assessment.R;
import com.zapper.assessment.databinding.PersonBinding;
import com.zapper.assessment.fragments.DetailFragment;
import com.zapper.assessment.model.PersonsModel;
import com.zapper.assessment.presenter.PersonPresenter;
import com.zapper.assessment.utils.AppConstants;
import com.zapper.assessment.utils.AppHelper;
import com.zapper.assessment.viewmodel.PersonViewModel;

import java.util.ArrayList;
import java.util.List;

public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> implements PersonPresenter {

    private FragmentActivity mContext;
    private final List<PersonViewModel> mValues;
    private final boolean mTwoPane;
    private LayoutInflater mLayoutInflater;

    public SimpleItemRecyclerViewAdapter(FragmentActivity parent,
                                  ArrayList<PersonViewModel> items,
                                  boolean twoPane) {
        mValues = items;
        mContext = parent;
        mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater==null){
            mLayoutInflater=LayoutInflater.from(parent.getContext());
        }
        PersonBinding myListBinding= DataBindingUtil.inflate(mLayoutInflater,R.layout.item_list_content,parent,false);
        return new ViewHolder(myListBinding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PersonViewModel personViewModel = mValues.get(position);
        holder.bind(personViewModel);
        holder.personBinding.setClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    @Override
    public void clickCard(PersonViewModel personsModel) {
        Toast.makeText(mContext,""+personsModel.personName,Toast.LENGTH_SHORT).show();
        if(mTwoPane){
            AppHelper.getInstance().addFragment(mContext, DetailFragment.getInstance(personsModel.personName, personsModel.personId), true,R.id.fragment_container_detail);
        }else {
            AppHelper.getInstance().addFragment(mContext, DetailFragment.getInstance(personsModel.personName, personsModel.personId), true);
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private PersonBinding personBinding;

        ViewHolder(PersonBinding view) {
            super(view.getRoot());
            this.personBinding = view;
         }

        public void bind(PersonViewModel personViewModel) {
            this.personBinding.setPersonModel(personViewModel);
        }

        public PersonBinding getPersonBinding() {
            return personBinding;
        }
    }
}