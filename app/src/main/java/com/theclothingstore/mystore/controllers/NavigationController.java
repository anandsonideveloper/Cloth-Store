package com.theclothingstore.mystore.controllers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.theclothingstore.mystore.ContentActivity;
import com.theclothingstore.mystore.fragments.BaseFragment;
import com.theclothingstore.mystore.fragments.productcatalogue.ProductCatalogueFragment;
import com.theclothingstore.mystore.fragments.shopingcart.ShoppingCartFragment;

/**
 * This class is responsible for all the fragment transaction which will take place
 * inside {@link ContentActivity}
 *
 * @author Anand Soni
 */

public class NavigationController {

    @NonNull
    private ContentActivity contentActivity;

    /**
     * Constructor for  {@link NavigationController}
     *
     * @param contentActivity, main activity for which navigation needs to take place
     */
    public NavigationController(@NonNull ContentActivity contentActivity) {
        this.contentActivity = contentActivity;
    }

    @NonNull
    private ContentActivity getContentActivity() {
        return contentActivity;
    }

    /**
     * This method is responsible for opening product catalogue screen
     */
    public void openProductCatalogueScreen() {

        ProductCatalogueFragment fragment = ProductCatalogueFragment.newInstance();
        doFragmentTransaction(fragment);
    }

    /**
     * This method is responsible for opening product catalogue screen
     */
    public void openShoppingCartScreen() {
        ShoppingCartFragment fragment = ShoppingCartFragment.newInstance();
        doFragmentTransaction(fragment, true);
    }

    /**
     * This method does the actual fragment transaction
     * By default this method doest not add fragment to back stack
     *
     * @param baseFragment, fragment you want to replace with existing content
     */
    private void doFragmentTransaction(@NonNull BaseFragment baseFragment) {
        doFragmentTransaction(baseFragment, false);
    }

    /**
     * This method does the actual fragment transaction
     *
     * @param baseFragment,   fragment you want to replace with existing content
     * @param addToBackStack, whether you want to add fragment to back stack or not
     */
    private void doFragmentTransaction(@NonNull BaseFragment baseFragment, boolean addToBackStack) {
        FragmentManager manager = getContentActivity().getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(ContentActivity.CONTAINER_ID, baseFragment, getFragmentName(baseFragment.getClass()));
        if (addToBackStack) {
            transaction.addToBackStack(getFragmentName(baseFragment.getClass()));
        }
        transaction.commit();
    }

    /**
     * Get the Fragment class name
     *
     * @param fragmentClass, class for which you want the name to be returned
     * @return String, fragment class name
     */
    @NonNull
    @VisibleForTesting
    public String getFragmentName(@NonNull Class fragmentClass) {
        return fragmentClass.getSimpleName();
    }
}
