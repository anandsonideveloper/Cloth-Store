package com.theclothingstore.mystore.fragments.productcatalogue;

import android.app.FragmentTransaction;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.theclothingstore.mystore.ContentActivity;
import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * @author Anand Soni
 */
public class ProductCatalogueFragmentTest {

    @Rule
    public ActivityTestRule<ContentActivity> activityTestRule = new ActivityTestRule<>(ContentActivity.class);

    private ProductCatalogueFragment fragment;

    @Before
    public void setUp() {
        fragment = ProductCatalogueFragment.newInstance();
        ContentActivity activity = activityTestRule.getActivity();
        FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
        transaction.replace(ContentActivity.CONTAINER_ID, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
    }

    @Test
    public void testNewInstance() {
        assertNotNull(fragment);
    }

    @Test
    public void testOnCreateView() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                fragment.onCreate(null);
                LayoutInflater layoutInflater = LayoutInflater.from(InstrumentationRegistry.getTargetContext());
                fragment.onCreateView(layoutInflater, null, null);
            }
        });
        assertNotNull(fragment.getProductList());
        assertNotNull(fragment.getAdapter());
        assertEquals(fragment.getAdapter(), fragment.getProductList().getAdapter());
    }

    @Test
    public void testToolbar() {
        onView(withId(R.id.toolbar)).check(matches(isDisplayed()));
        onView(withText(R.string.app_name)).check(matches(withParent(withId(R.id.toolbar))));
        onView(withId(R.id.shopping_cart)).check(matches(isDisplayed()));
        onView(withId(R.id.wish_list)).check(matches(isDisplayed()));
    }

    @Test
    public void testUserInterface() {
        onView(withId(R.id.product_list)).check(matches(isDisplayed()));
        //testing the RecyclerView item at position 0
        onView(withId(R.id.product_list)).perform(RecyclerViewActions.scrollToPosition(0)).check(new ViewAssertion() {
            @Override
            public void check(View view, NoMatchingViewException e) {
                Product product = mockProduct();
                RecyclerView recyclerView = (RecyclerView) view;
                RecyclerView.ViewHolder viewHolderForPosition = recyclerView.findViewHolderForLayoutPosition(0);
                View viewAtPosition = viewHolderForPosition.itemView;
                matches(hasDescendant(withText(product.getProductName()))).check(viewAtPosition, e);
                matches(hasDescendant(withText(product.getProductCategory()))).check(viewAtPosition, e);
                matches(hasDescendant(withText(String.valueOf(product.getProductStock())))).check(viewAtPosition, e);
                matches(hasDescendant(withId(R.id.button_add_cart))).check(viewAtPosition, e);
            }
        });
    }

    private Product mockProduct() {
        Product product = new Product();
        product.setProductName("Almond Toe Court Shoes, Patent Black");
        product.setProductCategory("Women's Footwear");
        product.setProductPrice(99);
        product.setProductOldPrice(null);
        product.setProductStock(5);
        return product;
    }

    private List<Product> mockProductList() {
        List<Product> products = new ArrayList<>();
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        return products;
    }
}