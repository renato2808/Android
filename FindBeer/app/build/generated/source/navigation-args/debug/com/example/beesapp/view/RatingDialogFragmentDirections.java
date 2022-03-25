package com.example.beesapp.view;

import androidx.annotation.NonNull;
import com.example.beesapp.NavHostDirections;

public class RatingDialogFragmentDirections {
  private RatingDialogFragmentDirections() {
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
