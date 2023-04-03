package com.example.cinemalab.presentation.bottomnavscreens.compilationscreen

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.cinemalab.R
import com.example.cinemalab.databinding.FragmentCompilationBinding
import com.example.cinemalab.domain.model.MovieModel
import com.yuyakaido.android.cardstackview.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompilationFragment : Fragment(), CardStackListener {

    private lateinit var binding: FragmentCompilationBinding
    private val viewModel: CompilationViewModel by viewModels()

    private val cardManager by lazy {
        CardStackLayoutManager(context, this)
    }
    private val cardAdapter by lazy {
        CardStackAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_compilation, container, false)
        binding = FragmentCompilationBinding.bind(view)

        initCardStackView()

        val stateObserver = Observer<CompilationViewModel.CompilationState> { newState ->
            when(newState) {
                CompilationViewModel.CompilationState.Initial -> {
                    showPlug()
                }
                CompilationViewModel.CompilationState.Loading ->
                    showLoading()
                is CompilationViewModel.CompilationState.Failure -> {
                    hideLoading()
                    showErrorDialog(newState.errorMessage)
                }
                is CompilationViewModel.CompilationState.Success -> {
                    hideLoading()
                    setupCardStackView(newState.compilation)
                }
            }

        }
        viewModel.state.observe(viewLifecycleOwner, stateObserver)

        return binding.root
    }

    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)

        builder.setTitle(getString(R.string.input_incorrect))
        builder.setMessage(message)
        builder.show()
    }

    private fun showLoading() {
        binding.grContent.isGone = true
        binding.grCompilationEnd.isGone = true
        binding.loading.show()
    }

    private fun hideLoading() {
        binding.grContent.isGone = false
        binding.loading.hide()
    }

    private fun showPlug() {
        binding.grContent.isGone = true
        binding.grCompilationEnd.isGone = false
        binding.loading.hide()
    }

    private fun initCardStackView() {
        cardManager.setStackFrom(StackFrom.None)
        cardManager.setVisibleCount(2)
        cardManager.setSwipeThreshold(0.3f)
        cardManager.setMaxDegree(-90.0f)
        cardManager.setDirections(Direction.HORIZONTAL)
        cardManager.setCanScrollHorizontal(true)
        cardManager.setCanScrollVertical(false)
        cardManager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        cardManager.setOverlayInterpolator(LinearInterpolator())

        binding.csvCompilation.layoutManager = cardManager
        //binding.csvCompilation.adapter = cardAdapter

        binding.csvCompilation.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun setupCardStackView(movies: List<MovieModel>) {
        cardAdapter.setMovies(movies)
        binding.csvCompilation.adapter = cardAdapter
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

    }

    override fun onCardRewound() {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {
        val currMovies = cardAdapter.getMovies()
        binding.tvMovieName.text = currMovies[position].name
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        val currMovies = cardAdapter.getMovies()
        if (position == currMovies.lastIndex) {
            showPlug()
        }
    }

}