package com.uhi5d.spotibud.presentation.ui.home.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.databinding.AvailableDevicesItemBinding
import com.uhi5d.spotibud.domain.model.devices.Device
import com.uhi5d.spotibud.domain.model.devices.Devices
import com.uhi5d.spotibud.util.BaseViewHolder

class AvailableDeviceAdapter (
    var context: Context
) : RecyclerView.Adapter<AvailableDeviceAdapter.DeviceViewHolder>() {

    var deviceList: List<Device> = listOf()

    fun setDevices(devices: Devices){
        deviceList = devices.devices!!
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        val itemBinding = AvailableDevicesItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return DeviceViewHolder(itemBinding)
    }

    override fun onBindViewHolder(
        holder: DeviceViewHolder,
        position: Int
    ) {
       holder.bind(deviceList[position])
    }

    override fun getItemCount(): Int {
        return deviceList.size
    }

    inner class DeviceViewHolder(
        private val binding: AvailableDevicesItemBinding
    ): BaseViewHolder<Device>(binding.root) {
        override fun bind(item: Device) {
            with(binding){
                when(item.type){
                    "Computer" -> {
                        ivDevice.setImageResource(R.drawable.ic_computer)
                    }
                    "Smartphone" -> {
                        ivDevice.setImageResource(R.drawable.ic_smartphone)
                    }
                }
                tvDeviceName.text = item.name
                tvDeviceId.text = item.id

                if (item.isActive == true){
                    ivActive.setImageResource(R.drawable.ic_active)
                }else{
                    ivActive.setImageResource(R.drawable.ic_deactive)
                }
            }
        }

    }
    }


