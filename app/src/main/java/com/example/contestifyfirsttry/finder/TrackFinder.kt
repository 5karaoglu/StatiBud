import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.model.Recommendations
import com.example.contestifyfirsttry.model.SearchHistory
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_tf_search.*
import kotlinx.android.synthetic.main.fragment_track_finder.*


class TrackFinder : Fragment() {
    private var TAG = "TrackFinder Fragment"
    private var viewModel: MainViewModel? = null
    private var selectedTrackId : String? = null
    private var selectedTrackName : String? = null
    private var selectedTrackImage : String? = null
    private var selectedTrackArtistId : String? = null
    private var selectedTrackArtistName : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_finder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initExit()


        if (arguments != null) {
            val bundle = arguments
            selectedTrackInit(bundle!!)
        }else{
            tvSelectSong.visibility = View.VISIBLE
            tvTfWarn.visibility = View.VISIBLE
            reLayoutSelectedTrack.visibility = View.GONE
        }
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences(
            "spotifystatsapp",
            Context.MODE_PRIVATE
        )
        val token = sharedPreferences.getString("token", "")

        // ViewModel components
        var factory = CustomViewModelFactory(this, requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel!!.recommendations.observe(viewLifecycleOwner,
            { t -> showTrack(t!!) })
        initButton(token!!)
    }
    private fun initButton(token: String){
        ivTfCancel.setOnClickListener {
            findNavController().navigate(R.id.action_trackFinder_to_tfSearch)
        }
        tvSelectSong.setOnClickListener {
            findNavController().navigate(R.id.action_trackFinder_to_tfSearch)
        }
        buttonFind.setOnClickListener {
            if (arguments != null){
                val acoustic = (tfSbAcoustic.progress/100).toString()
                val dance = (tfSbDance.progress/100).toString()
                val energy = (tfSbEnergy.progress/100).toString()
                val instrumental = (tfSbInstrumental.progress/100).toString()
                val live = (tfSbLive.progress/100).toString()
                val valence = (tfSbValence.progress/100).toString()

                viewModel!!.getRecommendedTrack(
                    token,
                    selectedTrackId!!,
                    acoustic,
                    dance,
                    energy,
                    instrumental,
                    live,
                    valence
                )
            }else{
                Toast.makeText(
                    requireContext(),
                    getString(R.string.button_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun selectedTrackInit(bundle: Bundle){
        tvTfWarn.visibility = View.GONE
        tvSelectSong.visibility = View.GONE
        reLayoutSelectedTrack.visibility = View.VISIBLE

        selectedTrackId = bundle.getString("id")
        selectedTrackName = bundle.getString("name")
        selectedTrackImage = bundle.getString("image")
        selectedTrackArtistId = bundle.getString("artistId")
        selectedTrackArtistName = bundle.getString("artistName")


        Picasso.get()
            .load(selectedTrackImage)
            .fit().centerCrop()
            .into(ivTfSelected)
        tvTfSelected.text = selectedTrackName
        tvTfSelectedArtist.text = selectedTrackArtistName
    }
    private fun showTrack(recommendations: Recommendations){
        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ft_go_to_song)){dialog, which ->
                val bundle = Bundle()
                bundle.putString("id", recommendations.tracks[0].id)
                bundle.putString("artistId", recommendations.tracks[0].album.artists[0].id)
                bundle.putString("name", recommendations.tracks[0].name)
                bundle.putString("image", recommendations.tracks[0].album.images[0].url)
                findNavController().navigate(R.id.action_trackFinder_to_detailedTrackFragment, bundle)
            }.setNegativeButton(getString(R.string.ft_cancel),null)
        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.tf_alert_view_success, null)
        dialogBuilder.setView(dialogView)
        val iv: ImageView = dialogView.findViewById<View>(R.id.ivFt) as ImageView
        val tv: TextView = dialogView.findViewById<View>(R.id.tvFt) as TextView
        val tvArtist: TextView = dialogView.findViewById<View>(R.id.tvFtArtist) as TextView

        Picasso.get()
            .load(recommendations.tracks[0].album.images[0].url)
            .fit().centerCrop()
            .into(iv)
        tv.text = recommendations.tracks[0].name
        tvArtist.text = recommendations.tracks[0].album.artists[0].name
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
    private fun initExit(){
        val callback = requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner){
            AlertDialog.Builder(requireContext())
                .setMessage(R.string.dialog_text)
                .setCancelable(false)
                .setPositiveButton(
                    R.string.dialog_accept
                ) { dialog, which -> requireActivity().finish() }
                .setNegativeButton(R.string.dialog_deny, null)
                .show()
        }
        callback.isEnabled = true
    }

}