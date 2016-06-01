package com.stu.app.jyu.view.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stu.app.jyu.R;

import java.util.ArrayList;
import java.util.List;

import static com.stu.app.jyu.view.Activity.InnerAppFunctionActivity.App_Function;
import static com.stu.app.jyu.view.Activity.InnerAppFunctionActivity.LostFind;

/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ItemFragment extends Fragment {

    // TODO: Customize parameter argument names



    // TODO: Customize parameters
    private int App_Type = 0;
    //    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    public static ItemFragment newInstance(int TYPE) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(App_Function, TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            App_Type = getArguments().getInt(App_Function);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            List list = new ArrayList();
            list.add("aaa");
            list.add("bb");
            list.add("aacca");
            list.add("dd");
            //get data source
            switch (App_Type){
                case LostFind:
                    break;
            }

//            recyclerView.setAdapter(new testApater(getContext(), list, R.layout.base_appui_item));
            //            recyclerView.setAdapter(new MyItemRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }

    //
    //    @Override
    //    public void onAttach(Context context) {
    //        super.onAttach(context);
    //        if (context instanceof OnListFragmentInteractionListener) {
    //            mListener = (OnListFragmentInteractionListener) context;
    //        } else {
    //            throw new RuntimeException(context.toString()
    //                    + " must implement OnListFragmentInteractionListener");
    //        }
    //    }

    //    @Override
    //    public void onDetach() {
    //        super.onDetach();
    //        mListener = null;
    //    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    //    public interface OnListFragmentInteractionListener {
    //        // TODO: Update argument type and name
    ////        void onListFragmentInteraction(DummyItem item);
    //    }
}
