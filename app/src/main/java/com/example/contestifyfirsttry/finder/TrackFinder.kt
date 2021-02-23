import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contestifyfirsttry.R
import com.example.contestifyfirsttry.finder.GenreAdapter
import com.example.contestifyfirsttry.main.MainViewModel
import com.example.contestifyfirsttry.model.Genres
import com.example.contestifyfirsttry.model.Recommendations
import com.example.contestifyfirsttry.model.SearchHistory
import com.example.contestifyfirsttry.model.TrackFinderTracks
import com.example.contestifyfirsttry.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_tf_search.*
import kotlinx.android.synthetic.main.fragment_track_finder.*


class TrackFinder : Fragment(),
    GenreAdapter.OnItemClickListener{
    private var TAG = "TrackFinder Fragment"
    private var viewModel: MainViewModel? = null
    private var selectedTrackId : String? = null
    private var selectedTrackName : String? = null
    private var selectedTrackImage : String? = null
    private var selectedTrackArtistId : String? = null
    private var selectedTrackArtistName : String? = null
    private var genreList : List<String>? = null
    private var filteredGenreList : MutableList<String> = mutableListOf()
    private var selectedGenre : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track_finder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeInvisible()
        initExit()
        tvSelectSong.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        if (arguments != null) {
            val bundle = arguments
            selectedTrackInit(bundle!!)
        }else{
            tvSelectSong.visibility = View.VISIBLE
            tvTfWarn.visibility = View.VISIBLE
            reLayoutSelectedTrack.visibility = View.GONE
        }
        selectedGenreInit()
        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences(
            "spotifystatsapp",
            Context.MODE_PRIVATE
        )
        val token = sharedPreferences.getString("token", "")

        // ViewModel components
        var factory = CustomViewModelFactory(this, requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel!!.genres.observe(viewLifecycleOwner,
            { t ->
                genreList = t.genres
                Log.d(TAG, "onViewCreated: done")
            })
        viewModel!!.getGenres(token!!)

        viewModel!!.recommendations.observe(viewLifecycleOwner,
            { t ->
                buttonProgress.visibility = View.GONE
                buttonFind.isClickable = true
                if (t.tracks.isNotEmpty()) {
                    showTrack(t!!)
                } else {
                    Toast.makeText(requireContext(), getString(R.string.song_not_found), Toast.LENGTH_SHORT).show()
                }
            })
        initButton(token!!)
    }

    override fun onResume() {
        super.onResume()
        selectedGenreInit()
    }
    private fun selectedGenreInit(){
        if (!selectedGenre.isNullOrEmpty()){
            recyclerTfGenre.visibility = View.GONE
            tvTfWarnGenre.visibility = View.GONE
            liGenreResult.visibility = View.VISIBLE

            tvSelectedGenre.text = selectedGenre
        }else{
            recyclerTfGenre.visibility = View.GONE
            tvTfWarnGenre.visibility = View.VISIBLE
            liGenreResult.visibility = View.GONE
        }
    }
    private fun genreInit(genres: List<String>){
        var adapter = GenreAdapter(requireContext(),genres,this)
        var layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        recyclerTfGenre.layoutManager = layoutManager
        recyclerTfGenre.adapter = adapter
    }
    private fun initButton(token: String){
        etGenre.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                filteredGenreList.clear()
                if (!s.isNullOrEmpty()){
                tvTfWarnGenre.visibility=View.GONE
                liGenreResult.visibility=View.GONE
                recyclerTfGenre.visibility=View.VISIBLE

                val searchString = s.toString()
                for (i in genreList!!){
                    if (i.contains(searchString)){
                        filteredGenreList.add(i)
                    }
                }
                genreInit(filteredGenreList)

            }else{
                    filteredGenreList.clear()
                    tvTfWarnGenre.visibility=View.VISIBLE
                    recyclerTfGenre.visibility=View.GONE
                }
            }

        })
        ivSelectedGenreCancel.setOnClickListener {
            liGenreResult.visibility = View.GONE
            tvTfWarnGenre.visibility = View.VISIBLE
            selectedGenre = ""
        }

        ivTfCancel.setOnClickListener {
            findNavController().navigate(R.id.action_trackFinder_to_tfSearch)
        }
        tvSelectSong.setOnClickListener {
            findNavController().navigate(R.id.action_trackFinder_to_tfSearch)
        }
        buttonFind.setOnClickListener {
            if (arguments != null && !selectedGenre.isNullOrEmpty()){
                val acoustic = (((tfSbAcoustic.progress/100).toFloat())/10).toString()
                val dance = (((tfSbDance.progress/100).toFloat())/10).toString()
                val energy = (((tfSbEnergy.progress/100).toFloat())/10).toString()
                val instrumental = (((tfSbInstrumental.progress/100).toFloat())/10).toString()
                val live = (((tfSbLive.progress/100).toFloat())/10).toString()
                val valence = (((tfSbValence.progress/100).toFloat())/10).toString()

                //button progressbar visibility
                buttonProgress.visibility = View.VISIBLE
                buttonFind.isClickable = false

                viewModel!!.getRecommendedTrack(
                    token,
                    selectedTrackId!!,
                    selectedGenre!!,
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
    private fun getTrack(recommendations: Recommendations): HashMap<String, String> {
        //getting random recommended track from list
        val random = (recommendations.tracks.indices).random()
        var map = HashMap<String,String>()
        map["id"] = recommendations.tracks[random].id
        map["name"] = recommendations.tracks[random].name
        map["image"] = recommendations.tracks[random].album.images[0].url
        map["artistId"] = recommendations.tracks[random].album.artists[0].id
        map["artistName"] = recommendations.tracks[random].album.artists[0].name
        map["albumImage"] = recommendations.tracks[random].album.images[0].url

        return map
    }
    private fun showTrack(recommendations: Recommendations){
        var map = getTrack(recommendations)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setCancelable(false)

        val inflater = this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.tf_alert_view_success, null)
        dialogBuilder.setView(dialogView)

        val iv: ImageView = dialogView.findViewById<View>(R.id.ivFt) as ImageView
        val tv: TextView = dialogView.findViewById<View>(R.id.tvFt) as TextView
        val tvArtist: TextView = dialogView.findViewById<View>(R.id.tvFtArtist) as TextView
        val buttonPositive: Button = dialogView.findViewById<View>(R.id.buttonPositive) as Button
        val buttonNegative: Button = dialogView.findViewById<View>(R.id.buttonNegative) as Button
        val buttonRefresh: Button = dialogView.findViewById<View>(R.id.buttonRefresh) as Button
        val alertDialog = dialogBuilder.create()
        buttonPositive.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", map["id"])
            bundle.putString("artistId", map["artistId"])
            bundle.putString("name", map["name"])
            bundle.putString("image", map["image"])
            //when clicked adds current track to database
            Thread{
                viewModel!!.trackFinderInsert(TrackFinderTracks(null,map["id"],map["name"],map["artistId"],map["artistName"],map["albumImage"]))
            }.start()
            alertDialog.dismiss()
            findNavController().navigate(R.id.action_trackFinder_to_detailedTrackFragment, bundle)
        }
        buttonNegative.setOnClickListener {
            alertDialog.dismiss()
        }
        buttonRefresh.setOnClickListener {
            map = getTrack(recommendations)
            insertIntoView(map["image"]!!,map["name"]!!,map["artistName"]!!,iv,tv,tvArtist)
        }
        insertIntoView(map["image"]!!,map["name"]!!,map["artistName"]!!,iv,tv,tvArtist)
        alertDialog.show()

    }
    private fun insertIntoView(image:String,name: String,artistName: String,iv:ImageView,tv:TextView,tvArtist:TextView){
        Picasso.get()
            .load(image)
            .fit().centerCrop()
            .into(iv)
        tv.text = name
        tvArtist.text = artistName
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
    //genre onclick
    override fun onItemClicked(genre: String) {
        hideKeyboard()
        recyclerTfGenre.visibility = View.GONE
        tvTfWarnGenre.visibility = View.GONE
        liGenreResult.visibility = View.VISIBLE
        selectedGenre = genre
        tvSelectedGenre.text = selectedGenre

    }
    private fun hideKeyboard(){
        val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (requireActivity().currentFocus != null){
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
        }
        etGenre.text.clear()
    }
    private fun makeInvisible(){
        recyclerTfGenre.visibility=View.GONE
        liGenreResult.visibility=View.GONE
        tvTfWarnGenre.visibility=View.VISIBLE
    }

}