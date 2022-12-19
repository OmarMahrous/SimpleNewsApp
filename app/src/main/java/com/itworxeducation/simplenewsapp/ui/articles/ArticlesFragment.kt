package com.itworxeducation.simplenewsapp.ui.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.itworxeducation.simplenewsapp.R
import com.itworxeducation.simplenewsapp.data.model.Article
import com.itworxeducation.simplenewsapp.data.repository.ArticlesRepository
import com.itworxeducation.simplenewsapp.data.source.remote.ApiGenerator
import com.itworxeducation.simplenewsapp.data.source.remote.articles.ArticlesApi
import com.itworxeducation.simplenewsapp.databinding.FragmentArticlesBinding
import com.itworxeducation.simplenewsapp.ui.BaseFragment
import com.itworxeducation.simplenewsapp.ui.ViewModelFactory
import com.itworxeducation.simplenewsapp.ui.onboarding_sources.categories.CategoriesAdapter

class ArticlesFragment: BaseFragment(R.layout.fragment_articles) {

    private val TAG = "ArticlesFragment"

    private var viewModel: ArticlesViewModel?=null


    private var _binding: FragmentArticlesBinding? =null
    private val binding: FragmentArticlesBinding get() =_binding!!

    private var listAdapter:ArticlesAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArticlesBinding.inflate(inflater, container, false)

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

    private fun fetchArticles(articlesRepository: ArticlesRepository) {
        viewModel = ViewModelProvider(this, ViewModelFactory(articlesRepository))[ArticlesViewModel::class.java]
        viewModel?.getArticles(country = "us")
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