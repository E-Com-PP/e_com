package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;

import com.fci.e_com.R;
import com.fci.e_com.WebHandler;

/**
 * A simple {@link Fragment} subclass.
 */
public class gradesFragment extends Fragment {


    public gradesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View frag = inflater.inflate(R.layout.fragment_grades, container, false);

        //ArrayAdapter<String> adap = new ArrayAdapter<String>(frag.getContext(), R.layout.support_simple_spinner_dropdown_item, WebHandler.YearOptions);

        //Spinner spin = ((Spinner)frag.findViewById(R.id.spinner3));
        //spin.setAdapter(adap);
        return frag;
    }

}
