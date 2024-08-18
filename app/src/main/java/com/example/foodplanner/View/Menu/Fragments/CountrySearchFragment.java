package com.example.foodplanner.View.Menu.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodplanner.R;

public class CountrySearchFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireView()).navigate(R.id.action_countrySearchFragment_to_randomMeal);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setCountryClickListeners(view, R.id.imageViewAmerican, "American");
        setCountryClickListeners(view, R.id.imageViewBritish, "British");
        setCountryClickListeners(view, R.id.imageViewCanadian, "Canadian");
        setCountryClickListeners(view, R.id.imageViewChinese, "Chinese");
        setCountryClickListeners(view, R.id.imageViewCroatian, "Croatian");
        setCountryClickListeners(view, R.id.imageViewDutch, "Dutch");
        setCountryClickListeners(view, R.id.imageViewEgyptian, "Egyptian");
        setCountryClickListeners(view, R.id.imageViewFilipino, "Filipino");
        setCountryClickListeners(view, R.id.imageViewFrench, "French");
        setCountryClickListeners(view, R.id.imageViewGreek, "Greek");
        setCountryClickListeners(view, R.id.imageViewIndian, "Indian");
        setCountryClickListeners(view, R.id.imageViewIrish, "Irish");
        setCountryClickListeners(view, R.id.imageViewItalian, "Italian");
        setCountryClickListeners(view, R.id.imageViewJamaican, "Jamaican");
        setCountryClickListeners(view, R.id.imageViewJapanese, "Japanese");
        setCountryClickListeners(view, R.id.imageViewKenyan, "Kenyan");
        setCountryClickListeners(view, R.id.imageViewMalaysian, "Malaysian");
        setCountryClickListeners(view, R.id.imageViewMexican, "Mexican");
        setCountryClickListeners(view, R.id.imageViewMoroccan, "Moroccan");
        setCountryClickListeners(view, R.id.imageViewPolish, "Polish");
        setCountryClickListeners(view, R.id.imageViewPortuguese, "Portuguese");
        setCountryClickListeners(view, R.id.imageViewRussian, "Russian");
        setCountryClickListeners(view, R.id.imageViewSpanish, "Spanish");
        setCountryClickListeners(view, R.id.imageViewThai, "Thai");
        setCountryClickListeners(view, R.id.imageViewTunisian, "Tunisian");
        setCountryClickListeners(view, R.id.imageViewTurkish, "Turkish");
        setCountryClickListeners(view, R.id.imageViewUkrainian, "Ukrainian");
        setCountryClickListeners(view, R.id.imageViewVietnamese, "Vietnamese");
    }

    private void setCountryClickListeners(View view, int imageViewId, String countryName) {
        ImageView imageView = view.findViewById(imageViewId);

        imageView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", countryName);
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageView.setOnLongClickListener(v -> {
            Toast.makeText(getContext(), countryName, Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}
