package com.itworxeducation.simplenewsapp.ui.articles

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.itworxeducation.simplenewsapp.NewsApplication
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.model.sources.Category
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.onboarding.SourceDao
import com.itworxeducation.simplenewsapp.data.source.remote.ApiGenerator
import com.itworxeducation.simplenewsapp.data.source.remote.articles.ArticlesApi
import com.itworxeducation.simplenewsapp.databinding.FragmentArticlesBinding
import com.itworxeducation.simplenewsapp.ui.BaseFragment
import com.itworxeducation.simplenewsapp.ui.util.search.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment: BaseFragment(R.layout.fragment_articles), ISaveArticleListener {

    private val TAG = "ArticlesFragment"

    private val viewModel: ArticlesViewModel by viewModels()


    private var _binding: FragmentArticlesBinding? =null
    private val binding: FragmentArticlesBinding get() =_binding!!

    @Inject
    lateinit var sourceDao: SourceDao

//    private var favouriteCountry="eg"
//    private var defaultCategory=Category("", "general", 0, false, 0, "")
//    private var favouriteCategoryList:MutableList<Category>?= mutableListOf(defaultCategory)

    private var listAdapter:ArticlesAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

//        lifecycleScope.launchWhenStarted {
//            sourceDao.getFavouriteCountry().collectLatest{
//                Log.d(TAG, "onCreateView: country = $it")
//            }
//        }

        initRecyclerView()

getFavouriteCountryAndCategories()

        return binding.root
    }

    private fun initRecyclerView() {
        listAdapter = context?.let { ArticlesAdapter(it, this) }
        binding.articlesRecyclerview.adapter = listAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeDataResult()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_fragment_articles, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        validateSearchInput(searchItem, searchView)


    }



    private fun validateSearchInput(searchItem: MenuItem, searchView: SearchView) {
        val pendingQuery = viewModel.searchQuery.value

        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it

            getArticlesBySearch(it)
        }
    }

    private fun getArticlesBySearch(query: String) {
        lifecycleScope.launchWhenStarted {
            delay(2000)

            viewModel.getArticles(country = "us", searchQuery = query)
        }
    }

    /**
     * Get articles by the selected country with the selected favorite categories
     */
    private fun fetchArticles(
        favouriteCountry: String,
        favouriteCategoryList: List<Category>?
    ) {
        val favouriteCategoryName = favouriteCategoryList?.get(0)?.name ?:""


        val articlesApi = ApiGenerator.setupBaseApi(ArticlesApi::class.java)
        val articlesRepository = ArticlesRepository(articlesApi)
        viewModel.setRepository(articlesRepository)
        viewModel.getArticles(country = favouriteCountry, searchQuery = null,
            category = favouriteCategoryName)


        Log.i(TAG, "fetchArticles: favouriteCountry = ${favouriteCountry}")
        Log.i(TAG, "fetchArticles: favouriteCategoryName = $favouriteCategoryName")
    }



    private fun getFavouriteCountryAndCategories(){

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {


            val favouriteCountry = viewModel.getFavouriteCountry().await()


//                Log.i(TAG, "getFavouriteCountryAndCategories: favouriteCountry = $favouriteCountry")

//            viewModel.getFavouriteCategories().collect{
//            favouriteCategoryList?.clear()
            viewModel.getFavouriteCategories().collectLatest {

                fetchArticles(favouriteCountry, it)
                Log.i(TAG, "getFavouriteCountryAndCategories: favouriteCountry = $favouriteCountry," +
                        "favouriteCategoryList = ${it.size}")
            }
//            }



        }
    }

    private fun observeDataResult(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.let {
                it.articlesFlowEvent.collect{ event->
                    when(event){
                        is GetArticlesEvent.GetDataOnSuccess ->{
                            Log.i(TAG, "onViewCreated: articles size = ${event.articleList.size}")


                            updateUiListComponent(event.articleList)
                        }
                        is GetArticlesEvent.ShowMessageOnError ->
                            Log.e(TAG, "onViewCreated: fail to load articles ${event.msg}")

                        else -> Log.d(TAG, "onViewCreated: show loading")
                    }
                }
            }
        }

    }

    private fun updateUiListComponent(articleList: List<Article>) {
        binding.articlesRecyclerview.apply {
            if (adapter==null)
                adapter = listAdapter

            listAdapter?.submitList(articleList)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home->{
                activity?.let { NewsApplication.exitApp(it) }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveClick(isFavourite: Boolean, article: Article) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.let {
                if (isFavourite)
                    it.saveArticle(article)
                else
                    it.removeArticle(article)
            }

        }

    }
}