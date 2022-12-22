package com.itworxeducation.simplenewsapp.ui.articles.favourite

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.databinding.FragmentFavouriteArticlesBinding
import com.itworxeducation.simplenewsapp.ui.BaseFragment
import com.itworxeducation.simplenewsapp.ui.articles.ArticlesAdapter
import com.itworxeducation.simplenewsapp.ui.articles.ISaveArticleListener
import com.itworxeducation.simplenewsapp.ui.util.search.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class FavouriteArticlesFragment: BaseFragment(R.layout.fragment_articles), ISaveArticleListener {

    private val TAG = "FavouriteArticlesFragment"

    private val viewModel: FavouriteArticlesViewModel by viewModels()


    private var _binding: FragmentFavouriteArticlesBinding? =null
    private val binding: FragmentFavouriteArticlesBinding get() =_binding!!


    private var listAdapter: ArticlesAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavouriteArticlesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)


        initRecyclerView()

        getFavouritesArticles(viewModel.searchQuery.value)

        return binding.root
    }

    private fun initRecyclerView() {
        listAdapter = context?.let { ArticlesAdapter(it, this) }
        binding.articlesRecyclerview.adapter = listAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

            getFavouritesArticles(it)
        }
    }


    private fun getFavouritesArticles(query: String?) {

        val searchQuery = query ?: ""

        lifecycleScope.launchWhenStarted {

            if (searchQuery.isNotEmpty()) {
                delay(2000)

                viewModel.searchArticles(searchQuery).collectLatest {
                    updateUiListComponent(it)
                }
            }else
                viewModel.getFavouriteArticles().collectLatest {
                    updateUiListComponent(it)
                    }
        }

    }


    private fun updateUiListComponent(articleList: List<Article>) {
        binding.articlesRecyclerview.apply {
            if (adapter==null)
                adapter = listAdapter

            listAdapter?.submitList(articleList)

        }

        updateSaveIcon(articleList)
    }

       /**
        * Update every save icon of article in the favourite list that identify the article is favourite
        */
        private fun updateSaveIcon(articleList: List<Article>){

           try {
               articleList.forEachIndexed { index, article ->
                   val viewHolder = binding.articlesRecyclerview.findViewHolderForAdapterPosition(index)
                           as ArticlesAdapter.ArticlesViewHolder

                   val saveIcon = viewHolder.binding.saveArticle
                   saveIcon.setImageResource(R.drawable.ic_action_bookmark)
               }
           }catch (e:Exception){
               e.printStackTrace()
           }

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