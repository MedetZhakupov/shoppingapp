package com.medetzhakupov.shoppingapp.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
import com.medetzhakupov.shoppingapp.Injector
import com.medetzhakupov.shoppingapp.R
import com.medetzhakupov.shoppingapp.data.ProductsService
import com.medetzhakupov.shoppingapp.data.model.Product
import com.medetzhakupov.shoppingapp.extensions.observe
import com.medetzhakupov.shoppingapp.extensions.viewModel
import com.medetzhakupov.shoppingapp.fragmentFactory
import com.medetzhakupov.shoppingapp.repository.ProductsRepository
import com.medetzhakupov.shoppingapp.ui.features.FragmentFeature
import com.medetzhakupov.shoppingapp.ui.features.productdetails.ProductDetailsFeature
import com.medetzhakupov.shoppingapp.ui.features.productdetails.ProductDetailsFragment
import com.medetzhakupov.shoppingapp.ui.features.products.ProductsFeature
import com.medetzhakupov.shoppingapp.ui.features.products.ProductsFragment
import com.medetzhakupov.shoppingapp.ui.features.shoppingcart.ShoppingCartFeature
import com.medetzhakupov.shoppingapp.ui.features.shoppingcart.ShoppingCartFragment
import kotlinx.android.synthetic.main.home_activity.*

private const val SAVED_STATE_PAGE = "SAVED_STATE_PAGE"

val Bundle.currentPage: Page?
    get() = getParcelable(SAVED_STATE_PAGE)

fun Bundle.putCurrentPage(page: Page) = apply {
    putParcelable(SAVED_STATE_PAGE, page)
}

class HomeActivity : AppCompatActivity() {

    private val injector: Injector by lazy { Injector(application) }
    private val viewModel: HomeViewModel by viewModel {
        HomeViewModel(
            HomeView(HomeViewState(Page.Products)), injector.provideCartsProductsRepository()
        )
    }

    private val productsFeature: ProductsFeature by lazy {
        ProductsFeature(this, ProductsRepository(ProductsService(resources))) { viewModel.onProductSelected(it) }
    }
    private val shoppingCartFeature: ShoppingCartFeature by lazy {
        ShoppingCartFeature(
            injector.provideCartsProductsRepository(), this
        )
    }

    private lateinit var productDetailsFeature: ProductDetailsFeature

    private val onTabSelectedListener = OnNavigationItemSelectedListener(::onTabSelected)
    private var currentlyShownPage: Page? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        currentlyShownPage = savedInstanceState?.currentPage
        (currentlyShownPage as? Page.ProductDetails)?.also { initAndProductDetailsFeature(it.product) }
        supportFragmentManager.fragmentFactory = fragmentFactory(::createFragment)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        with(viewModel.view) {
            observe(state, ::render)
            observe(shoppingCartEmptyEvent, ::updateShoppingCartBadge)
        }

        bottom_navigation.setOnNavigationItemSelectedListener(onTabSelectedListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        currentlyShownPage?.also { outState.putCurrentPage(it) }
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        supportFragmentManager.apply {
            when {
                supportFragmentManager.fragments.lastOrNull() is ProductsFragment -> {
                    popBackStack()
                    finish()
                }
                supportFragmentManager.fragments.lastOrNull() is ShoppingCartFragment -> {
                    currentlyShownPage = Page.Products
                    currentlyShownPage?.also { bottom_navigation.markTabSelected(it.tabId) }
                }
                else -> {
                    viewModel.onBackPressed()
                }
            }
        }

        super.onBackPressed()
    }

    private fun onTabSelected(tab: MenuItem): Boolean {
        val selectedPage = when (tab.itemId) {
            R.id.menu_bottom_navigation_home -> Page.Products
            R.id.menu_bottom_navigation_cart -> Page.ShoppingCart
            else -> throw IllegalStateException("unhandled menu item")
        }

        viewModel.onTapSelected(selectedPage)
        return true
    }

    private fun render(newState: HomeViewState) {
        val previouslyShownPage = currentlyShownPage
        currentlyShownPage = newState.page

        val tabToSelect = newState.page.tabId
        if (newState.page !is Page.ProductDetails && bottom_navigation.selectedItemId != tabToSelect) {
            bottom_navigation.markTabSelected(tabToSelect)
        }

        if (newState.page is Page.ProductDetails) {
            initAndProductDetailsFeature(newState.page.product)
        }

        if (newState.page != previouslyShownPage) {
            newState.page.feature.show()
        }
    }

    private fun updateShoppingCartBadge(isCartEmpty: Boolean) = apply {
        if (isCartEmpty) {
            bottom_navigation.removeBadge(R.id.menu_bottom_navigation_cart)
        } else {
            bottom_navigation.getOrCreateBadge(R.id.menu_bottom_navigation_cart)
        }
    }

    private val Page.feature: FragmentFeature<*>
        get() = when (this) {
            Page.Products -> productsFeature
            Page.ShoppingCart -> shoppingCartFeature
            is Page.ProductDetails -> productDetailsFeature
        }

    private fun BottomNavigationView.markTabSelected(@IdRes tabToSelect: Int) {
        setOnNavigationItemSelectedListener(null)
        selectedItemId = tabToSelect
        setOnNavigationItemSelectedListener(onTabSelectedListener)
    }

    private fun initAndProductDetailsFeature(product: Product) {
        productDetailsFeature = ProductDetailsFeature(
            product,
            injector.provideCartsProductsRepository(),
            this
        ) { onBackPressed() }
    }

    private fun createFragment(fragmentClass: Class<out Fragment>): Fragment? =
        when (fragmentClass) {
            ProductsFragment::class.java -> {
                productsFeature.createFragment()
            }
            ShoppingCartFragment::class.java -> {
                shoppingCartFeature.createFragment()
            }
            ProductDetailsFragment::class.java -> {
                productDetailsFeature.createFragment()
            }
            else -> null
        }
}

private val Page.tabId
    get() = when (this) {
        Page.Products -> R.id.menu_bottom_navigation_home
        Page.ShoppingCart -> R.id.menu_bottom_navigation_cart
        else -> 0
    }
