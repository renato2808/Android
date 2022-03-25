package com.example.beesapp.view;

import androidx.annotation.NonNull;
import com.example.beesapp.NavHostDirections;

public class HomeFragmentDirections {
  private HomeFragmentDirections() {
  }

  @NonNull
  public static NavHostDirections.HomeToDetails homeToDetails() {
    return NavHostDirections.homeToDetails();
  }

  @NonNull
  public static NavHostDirections.DetailsToRating detailsToRating() {
    return NavHostDirections.detailsToRating();
  }
}
