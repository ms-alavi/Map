package land.map.feature.presentation.ui.fragmentes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.InternalCoroutinesApi
import land.map.amir.R
import land.map.common.adapter.MyRecyclerViewAdapter
import land.map.common.NetworkResult
import land.map.amir.databinding.FragmentLisCarBinding
import land.map.feature.presentation.viewmodel.MapViewModel


class ListCarFragment : Fragment() {
    val mapViewModel: MapViewModel by hiltNavGraphViewModels(R.id.nav_graph_xml)

    private lateinit var mBinding: FragmentLisCarBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lis_car, container, false)
        // Inflate the layout for this fragment
        mBinding.recycler.setOnClickListener {
            //findNavController().navigate(R.id.action_homeFragment_to_mapsFragment)
        }
        return mBinding.root
    }

    @OptIn(InternalCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

        mBinding.recycler.layoutManager=linearLayoutManager
        mapViewModel.fetchCarResponse()
        mapViewModel.responseCar.observe(viewLifecycleOwner) { res ->
            when (res) {
                is NetworkResult.Success -> {
                    val adapter=MyRecyclerViewAdapter(res.data?.pointList )
                    mBinding.recycler.adapter=adapter
                }
                is NetworkResult.Error -> {
                    // show error message
                }
                is NetworkResult.Loading -> {
                    // show a progress bar
                }
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ListCarFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}