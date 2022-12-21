package com.itworxeducation.simplenewsapp.ui.articles

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository
import com.itworxeducation.simplenewsapp.data.source.local.database.favourite.onboarding.SourceDao
import com.itworxeducation.simplenewsapp.data.source.remote.ApiGenerator
import com.itworxeducation.simplenewsapp.data.source.remote.articles.ArticlesApi
import com.itworxeducation.simplenewsapp.databinding.FragmentArticlesBinding
import com.itworxeducation.simplenewsapp.ui.BaseFragment
import com.itworxeducation.simplenewsapp.ui.ViewModelFactory
import com.itworxeducation.simplenewsapp.ui.util.search.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment: BaseFragment(R.layout.fragment_articles) {

    private val TAG = "ArticlesFragment"

    private var viewModel: ArticlesViewModel?=null


    private var _binding: FragmentArticlesBinding? =null
    private val binding: FragmentArticlesBinding get() =_binding!!

    @Inject
    lateinit var sourceDao: SourceDao



    private var listAdapter:ArticlesAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        lifecycleScope.launchWhenStarted {
            sourceDao.getFavouriteCountry().collectLatest{
                Log.d(TAG, "onCreateView: country = $it")
            }
        }

        initRecyclerView()

        val articlesApi = ApiGenerator.setupBaseApi(ArticlesApi::class.java)
        val articlesRepository = ArticlesRepository(articlesApi)
        fetchArticles(articlesRepository)

        return binding.root
    }

    private fun initRecyclerView() {
        listAdapter = context?.let { ArticlesAdapter(it) }
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
        val pendingQuery = viewModel?.searchQuery?.value

        if (pendingQuery != null && pendingQuery.isNotEmpty()) {
            searchItem.expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            viewModel?.searchQuery?.value = it

            getArticlesBySearch(it)
        }
    }

    private fun getArticlesBySearch(query: String) {
        lifecycleScope.launchWhenStarted {
            delay(2000)

            viewModel?.getArticles(country = "us", searchQuery = query)
        }
    }

    private fun fetchArticles(articlesRepository: ArticlesRepository) {

        viewModel = ViewModelProvider(this, ViewModelFactory(articlesRepository))[ArticlesViewModel::class.java]
        viewModel?.getArticles(country = "us", searchQuery = null)

    }

    private fun observeDataResult(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel?.let {
                it.articlesFlowEvent.collect{ event->
                    when(event){
                        is GetArticlesEvent.GetDataOnSuccess ->{
                            Log.i(TAG, "onViewCreated: sources size = ${event.articleList.size}")


                            updateUiListComponent(event.articleList)
                        }
                        is GetArticlesEvent.ShowMessageOnError ->
                            Log.e(TAG, "onViewCreated: fail to load sources ${event.msg}")

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}