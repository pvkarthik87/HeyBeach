package com.karcompany.heybeach.mvputils;

/**
 * Created by pvkarthik on 2017-02-20.
 *
 *Interface representing a Presenter in a model view presenter (MVP) pattern.
 */
public interface Presenter {
  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onResume() method.
   */
  void onResume();

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onPause() method.
   */
  void onPause();

  void onStart();

  void onStop();

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onDestroy() method.
   */
  void onDestroy();
}
