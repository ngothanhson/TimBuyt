package ngothanhson95.dev.com.timbuyt.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ngothanhson95.dev.com.timbuyt.AppConstants;
import ngothanhson95.dev.com.timbuyt.listener.RecyclerViewClickListener;
import ngothanhson95.dev.com.timbuyt.R;
import ngothanhson95.dev.com.timbuyt.adapter.RouteResultAdapter;
import ngothanhson95.dev.com.timbuyt.model.direction.Route;
import ngothanhson95.dev.com.timbuyt.model.direction.Step;
import ngothanhson95.dev.com.timbuyt.ui.MainActivity;

/**
 * Created by sonnt on 12/19/16.
 */

public class LessChangeBusFragment extends Fragment implements RecyclerViewClickListener {
    RecyclerView rvLessChangeBus;
    ArrayList<Route> routes = new ArrayList<>();
    RouteResultAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_less_change_bus, container, false);
        rvLessChangeBus = (RecyclerView) v.findViewById(R.id.rvLessChangeBus);
        Bundle b = getArguments();
        if(b!=null){
            routes =  (ArrayList<Route>) b.getSerializable(AppConstants.PARCELABLE_ROUTE_KEY);
            Collections.sort(routes, new LessChangeBusComparator());
            adapter = new RouteResultAdapter(routes);
        }
        rvLessChangeBus.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        rvLessChangeBus.setAdapter(adapter);
        this.adapter.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent i = new Intent(getActivity(), MainActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(AppConstants.ROUT_KEY, routes.get(position));
        i.putExtra(AppConstants.ROUT_KEY, b);
        startActivity(i);
    }

    class LessChangeBusComparator implements Comparator<Route> {

        @Override
        public int compare(Route route, Route t1) {
            int b1 = 0, b2 = 0;
            for(Step step: route.getLegs().get(0).getSteps()){
                if(step.travelMode.equals("TRANSIT")){
                    b1 +=1;
                }
            }
            for(Step step: t1.getLegs().get(0).getSteps()){
                if(step.travelMode.equals("TRANSIT")){
                    b2 +=1;
                }
            }
            return Integer.compare(b1,b2);
        }
    }
}
