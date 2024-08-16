package com.example.foodplanner.View.Menu;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.foodplanner.R;

public class CountrySearchFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requireActivity().getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Replace with the action to navigate to another fragment
                Navigation.findNavController(requireView()).navigate(R.id.action_countrySearchFragment_to_randomMeal);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewAmerican = view.findViewById(R.id.imageViewAmerican);
        ImageView imageViewBritish = view.findViewById(R.id.imageViewBritish);
        ImageView imageViewCanadian = view.findViewById(R.id.imageViewCanadian);
        ImageView imageViewChinese = view.findViewById(R.id.imageViewChinese);
        ImageView imageViewCroatian = view.findViewById(R.id.imageViewCroatian);
        ImageView imageViewDutch = view.findViewById(R.id.imageViewDutch);
        ImageView imageViewEgyptian = view.findViewById(R.id.imageViewEgyptian);
        ImageView imageViewFilipino = view.findViewById(R.id.imageViewFilipino);
        ImageView imageViewFrench = view.findViewById(R.id.imageViewFrench);
        ImageView imageViewGreek = view.findViewById(R.id.imageViewGreek);
        ImageView imageViewIndian = view.findViewById(R.id.imageViewIndian);
        ImageView imageViewIrish = view.findViewById(R.id.imageViewIrish);
        ImageView imageViewItalian = view.findViewById(R.id.imageViewItalian);
        ImageView imageViewJamaican = view.findViewById(R.id.imageViewJamaican);
        ImageView imageViewJapanese = view.findViewById(R.id.imageViewJapanese);
        ImageView imageViewKenyan = view.findViewById(R.id.imageViewKenyan);
        ImageView imageViewMalaysian = view.findViewById(R.id.imageViewMalaysian);
        ImageView imageViewMexican = view.findViewById(R.id.imageViewMexican);
        ImageView imageViewMoroccan = view.findViewById(R.id.imageViewMoroccan);
        ImageView imageViewPolish = view.findViewById(R.id.imageViewPolish);
        ImageView imageViewPortuguese = view.findViewById(R.id.imageViewPortuguese);
        ImageView imageViewRussian = view.findViewById(R.id.imageViewRussian);
        ImageView imageViewSpanish = view.findViewById(R.id.imageViewSpanish);
        ImageView imageViewThai = view.findViewById(R.id.imageViewThai);
        ImageView imageViewTunisian = view.findViewById(R.id.imageViewTunisian);
        ImageView imageViewTurkish = view.findViewById(R.id.imageViewTurkish);
        ImageView imageViewUkrainian = view.findViewById(R.id.imageViewUkrainian);
        ImageView imageViewVietnamese = view.findViewById(R.id.imageViewVietnamese);

        imageViewAmerican.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "American");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewBritish.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "British");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewCanadian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Canadian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewChinese.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Chinese");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewCroatian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Croatian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewDutch.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Dutch");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewEgyptian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Egyptian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewFilipino.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Filipino");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewFrench.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "French");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewGreek.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Greek");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewIndian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Indian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewIrish.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Irish");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewItalian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Italian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewJamaican.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Jamaican");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewJapanese.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Japanese");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewKenyan.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Kenyan");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewMalaysian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Malaysian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewMexican.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Mexican");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewMoroccan.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Moroccan");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewPolish.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Polish");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewPortuguese.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Portuguese");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewRussian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Russian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewSpanish.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Spanish");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewThai.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Thai");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewTunisian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Tunisian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewTurkish.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Turkish");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewUkrainian.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Ukrainian");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });

        imageViewVietnamese.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("meal_name", "Vietnamese");
            Navigation.findNavController(v).navigate(R.id.action_countrySearchFragment_to_mealsFragment, bundle);
        });


    }
}