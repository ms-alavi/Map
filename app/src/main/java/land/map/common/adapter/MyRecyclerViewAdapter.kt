package land.map.common.adapter

import land.map.feature.domain.model.CarLocationDto
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import land.map.amir.R
import land.map.amir.databinding.CarDetailItemBinding


class MyRecyclerViewAdapter(private val data: List<CarLocationDto>?) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: CarDetailItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.car_detail_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val carLocation = data?.get(position)
        carLocation?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    inner class ViewHolder(var mBinding: CarDetailItemBinding) : RecyclerView.ViewHolder(
        mBinding.root
    ) {
        fun bind(carLocationDto: CarLocationDto) {
            mBinding.carType.text = carLocationDto.fleetType
            mBinding.btnLocation.setOnClickListener{
                val id=carLocationDto.id
                val bundle = bundleOf("id" to id)
                mBinding.root.findNavController().navigate(R.id.action_listCarFragment_to_showSingleCarOnMapFragment,bundle)
            }
            mBinding.button.setOnClickListener {
                Snackbar.make(mBinding.root,"The car was requested to number :"+" ${carLocationDto.id}", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
    }

}