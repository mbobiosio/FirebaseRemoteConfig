package com.mbobiosio.firebaseremoteconfig.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mbobiosio.firebaseremoteconfig.data.model.RemoteConfigs
import com.mbobiosio.firebaseremoteconfig.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val viewModel by viewModels<ConfigViewModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe viewModel live data
        viewModel.remoteConfigLiveData.observe(viewLifecycleOwner) {
            updateConfigs(it)
        }
    }

    /**
     * Present details on UI if forceUpdate is true
     * */
    private fun updateConfigs(remoteConfigs: RemoteConfigs) {
        Timber.d("$remoteConfigs")
        when {
            remoteConfigs.forceUpdate -> {
                Timber.d("App requires update : ${remoteConfigs.message}")
            }
            else -> {
                Timber.d("Everything remains normal")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
