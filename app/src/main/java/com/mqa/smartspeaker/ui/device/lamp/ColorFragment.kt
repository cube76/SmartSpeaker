package com.mqa.smartspeaker.ui.device.lamp

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.Fragment
import com.gcssloop.widget.ArcSeekBar
import com.gcssloop.widget.ArcSeekBar.OnProgressChangeListener
import com.mqa.smartspeaker.R
import com.mqa.smartspeaker.databinding.FragmentColorBinding


class ColorFragment : Fragment() {

    private var _binding: FragmentColorBinding? = null
    private val binding get() = _binding!!
    private val mColorSeeds = intArrayOf(
        Color.parseColor("#000000"), Color.parseColor("#FF5252"), Color.parseColor("#FFEB3B"), Color.parseColor("#00C853"), Color.parseColor("#00B0FF"), Color.parseColor("#D500F9"), Color.parseColor("#8D6E63"),0xFF000000.toInt()
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentColorBinding.inflate(inflater, container, false)
        val mArcSeekBar = binding.SBWarm
        mArcSeekBar.setArcColors(mColorSeeds)
        mArcSeekBar.setOnProgressChangeListener(object : OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {
                val backgroundGradient = binding.view5.background as GradientDrawable
                backgroundGradient.setColor(seekBar.color)
            }
            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {}
            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {}
        })

        binding.SBBrightness.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.TVPersentageBrightness.text = "$progress%"
            }
        })

        binding.SBContras.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                binding.TVPersentageContras.text = "$progress%"
            }
        })

        binding.IVWarm.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container_lamp, WarmFragment())
            transaction?.commit()
        }
        return binding.root
    }

}