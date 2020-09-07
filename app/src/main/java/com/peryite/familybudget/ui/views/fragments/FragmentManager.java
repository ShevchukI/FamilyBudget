package com.peryite.familybudget.ui.views.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.peryite.familybudget.entities.Credential;

import java.util.ArrayList;
import java.util.List;

public class FragmentManager {
    private static FragmentManager instance;

    private List<BaseFragment> fragmentList;

    public static FragmentManager getInstance() {
       if(instance==null){
           instance = new FragmentManager();
       }
       return instance;
    }

    private FragmentManager(){
        fragmentList = fillFragmentList();
    }

    private List<BaseFragment> fillFragmentList(){
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new BudgetCategoryFragment());
        return fragments;
    }

    public Fragment getFragment(FragmentSelect fragmentSelect){
        Fragment fragment = null;
        if(fragmentSelect.equals(FragmentSelect.BudgetCategory)){
            fragment = fragmentList.get(0);
        }
        return fragment;
    }

    public void setContextOnFragment(FragmentSelect fragmentSelect, Context context){
        if(fragmentSelect.equals(FragmentSelect.BudgetCategory)){
            fragmentList.get(0).setContext(context);
        }
    }

    public void setCredentialOnFragment(FragmentSelect fragmentSelect, Credential credential){
        if(fragmentSelect.equals(FragmentSelect.BudgetCategory)){
            fragmentList.get(0).setCredential(credential);
        }
    }

   public enum FragmentSelect{
        BudgetCategory
    }


}
