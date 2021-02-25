package com.uhi5d.spotibud.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.model.Device
import com.uhi5d.spotibud.model.Devices

class AvailableDeviceAdapter (
    var context: Context,
    var dataList: Devices,
    var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AvailableDeviceAdapter.DeviceViewHolder>() {


    class DeviceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.ivDevice)
        var tvDeviceId = itemView.findViewById<TextView>(R.id.tvDeviceId)
        var textViewName = itemView.findViewById<TextView>(R.id.tvDeviceName)
        var imageViewActive = itemView.findViewById<ImageView>(R.id.ivActive)

        fun bind(device: Device, clickListener:OnItemClickListener) {
            when(device.type){
                "Computer" -> {
                    Log.d("TAG", "bind: comp")
                    imageView.setImageResource(R.drawable.ic_computer)
                }
                "Smartphone" -> {
                    Log.d("TAG", "bind: smar")
                    imageView.setImageResource(R.drawable.ic_smartphone)
                }
            }
            textViewName.text = device.name
            tvDeviceId.text = device.id

            if (device.is_active){
                imageViewActive.setImageResource(R.drawable.ic_active)
            }else{
                imageViewActive.setImageResource(R.drawable.ic_deactive)
            }


            itemView.setOnClickListener {
                clickListener.onItemClicked(device)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceViewHolder {
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
        var view = layoutInflater.inflate(R.layout.available_devices_item, parent, false)

        return DeviceViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: DeviceViewHolder,
        position: Int
    ) {
        var device = dataList.devices[position]
        holder.bind(device, itemClickListener)
    }

    override fun getItemCount(): Int {
        return dataList.devices.size
    }

    interface OnItemClickListener {
        fun onItemClicked(device: Device)

    }}


