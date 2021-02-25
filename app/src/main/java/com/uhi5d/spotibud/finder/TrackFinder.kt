import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uhi5d.spotibud.R
import com.uhi5d.spotibud.finder.GenreAdapter
import com.uhi5d.spotibud.main.MainViewModel
import com.uhi5d.spotibud.model.Recommendations
import com.uhi5d.spotibud.model.TrackFinderTracks
import com.uhi5d.spotibud.util.CustomViewModelFactory
import com.squareup.picasso.Picasso
import com.uhi5d.spotibud.Functions
import com.uhi5d.spotibud.finder.TfSearchResultsAdapter
import com.uhi5d.spotibud.model.QueryResultTrackItem
import com.uhi5d.spotibud.model.QueryResults
import kotlinx.android.synthetic.main.fragment_tf_search.*
import kotlinx.android.synthetic.main.fragment_track_finder.*
import kotlinx.android.synthetic.main.track_search.*


class TrackFinder : Fragment(),
    GenreAdapter.OnItemClickListener,
    TfSearchResultsAdapter.OnItemClickListener{
    private var TAG = "TrackFinder Fragment"
    private var viewModel: MainViewModel? = null
    private var token: String? = null
    private var functions = Functions()

    private var selectedTrackId : String? = null
    private var selectedTrackName : String? = null
    private var selectedTrackImage : String? = null
    private var selectedTrackArtistId : String? = null
    private var selectedTrackArtistName : String? = null
    private var genreList : List<String>? = null
    private var filteredGenreList : MutableList<String> = mutableListOf()
    private var selectedGenre : String? = null

    private var trackMap  = HashMap<String,String>()
    private var genreMap  = HashMap<String,String>()

    private lateinit var songAdapter : AlertDialog.Builder
    private lateinit var songAlertDialog: AlertDialog
    private lateinit var genreAdapter : AlertDialog.Builder
    private lateinit var genreAlertDialog: AlertDialog

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

        //getting token
        val sharedPreferences = requireActivity().getSharedPreferences(
            "spotifystatsapp",
            Context.MODE_PRIVATE
        )
        token = sharedPreferences.getString("token", "")

        // ViewModel components
        val factory = CustomViewModelFactory(this, requireContext())
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)


    }

    override fun onStart() {
        super.onStart()
        observerInit()
        selectSongButtonInit()
        selectGenreButtonInit()
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


        helpButtonInit()
    }

    override fun onResume() {
        super.onResume()
        mapCheck()
    }
    private fun observerInit(){

    }
    private fun mapCheck(){
        if (!trackMap.isNullOrEmpty()){
            buttonSelectSong.visibility = View.GONE
            reLayoutSelectedTrack.visibility = View.VISIBLE

            Picasso.get()
                .load(trackMap["image"])
                .fit().centerCrop()
                .into(ivTfSelected)
            tvTfSelected.text = trackMap["name"]
            tvTfSelectedArtist.text = trackMap["artistName"]
        }else{
            buttonSelectSong.visibility = View.VISIBLE
            reLayoutSelectedTrack.visibility = View.GONE
        }
        if (!genreMap.isNullOrEmpty()){
            buttonSelectGenre.visibility = View.GONE
            liGenreResult.visibility = View.VISIBLE

            tvSelectedGenre.text = genreMap["genre"]
        }else{
            buttonSelectGenre.visibility = View.VISIBLE
            liGenreResult.visibility = View.GONE
        }
    }

    private fun selectSongButtonInit(){
        var q: String?

        buttonSelectSong.setOnClickListener {
            songAdapter = AlertDialog.Builder(requireContext())
                .setCancelable(false)

            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.track_search, null)
            songAdapter.setView(dialogView)

            val et: EditText = dialogView.findViewById<View>(R.id.etSearchTrack) as EditText
            val recycler: RecyclerView = dialogView.findViewById<View>(R.id.recyclerTfSearchTrack) as RecyclerView
            val buttonHideKeyboard: Button = dialogView.findViewById<View>(R.id.buttonHideKeyboard) as Button
            val buttonCancel: Button = dialogView.findViewById<View>(R.id.buttonCancel) as Button
            songAlertDialog = songAdapter.create()

            fun adaptSongs(queryResults: QueryResults){
                //recycler adapter for artists
                val resultsAdapter = TfSearchResultsAdapter(
                    requireContext(),
                    queryResults,
                    this,
                )
                val layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
                recycler.layoutManager = layoutManager
                recycler.adapter = resultsAdapter
            }

            viewModel!!.queryResults.observe(viewLifecycleOwner,
                Observer { t -> adaptSongs(t!!)})

            et.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s != null) {
                        //makes visible if only gone
                        if (recycler.visibility == View.GONE) {
                            recycler.visibility=View.VISIBLE
                        }
                        q = functions.encodeString(s.toString())
                        Log.d(TAG, "afterTextChanged: $q")
                        viewModel!!.getQueryResult(token!!, q!!)
                    } else {
                        recycler.visibility=View.GONE
                        hideKeyboard()
                    }
                }

            })
            buttonHideKeyboard.setOnClickListener {
                hideKeyboard()
            }
            buttonCancel.setOnClickListener {
                songAlertDialog.dismiss()
            }
            songAlertDialog.show()
        }
    }
    private fun selectGenreButtonInit(){

        buttonSelectGenre.setOnClickListener {
            genreAdapter = AlertDialog.Builder(requireContext())
                .setCancelable(false)

            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.genre_search, null)
            genreAdapter.setView(dialogView)

            val et: EditText = dialogView.findViewById<View>(R.id.etSearchGenre) as EditText
            val recycler: RecyclerView = dialogView.findViewById<View>(R.id.recyclerTfSearchGenre) as RecyclerView
            val buttonHideKeyboard: Button = dialogView.findViewById<View>(R.id.buttonHideKeyboardGenre) as Button
            val buttonCancel: Button = dialogView.findViewById<View>(R.id.buttonCancelGenre) as Button
            genreAlertDialog = genreAdapter.create()

            fun adaptGenres(genres: List<String>){
                val adapter = GenreAdapter(requireContext(),genres,this)
                val layoutManager = GridLayoutManager(requireContext(),3,GridLayoutManager.VERTICAL,false)
                recycler.layoutManager = layoutManager
                recycler.adapter = adapter
            }

            et.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    filteredGenreList.clear()
                    if (s != null) {
                        //makes visible if only gone
                        if (recycler.visibility == View.GONE) {
                            recycler.visibility=View.VISIBLE
                        }
                        val searchString = s.toString()
                        for (i in genreList!!){
                            if (i.contains(searchString)){
                                filteredGenreList.add(i)
                            }
                        }
                        adaptGenres(filteredGenreList)
                    } else {
                        filteredGenreList.clear()
                        recycler.visibility=View.GONE
                        hideKeyboard()
                    }
                }

            })
            buttonHideKeyboard.setOnClickListener {
                hideKeyboard()
            }
            buttonCancel.setOnClickListener {
                genreAlertDialog.dismiss()
            }
            genreAlertDialog.show()
        }
    }

    private fun helpButtonInit(){
        tfIvHelp.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            .setCancelable(false)

            val inflater = this.layoutInflater
            val dialogView: View = inflater.inflate(R.layout.help_dialog, null)
            dialogBuilder.setView(dialogView)
            val buttonGotIt: Button = dialogView.findViewById<View>(R.id.buttonGotIt) as Button
            val alertDialog = dialogBuilder.create()
            buttonGotIt.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun initButton(token: String){
        ivSelectedGenreCancel.setOnClickListener {
            genreMap.clear()
            liGenreResult.visibility=View.GONE
            buttonSelectGenre.visibility=View.VISIBLE
        }

        ivTfCancel.setOnClickListener {
            trackMap.clear()
            reLayoutSelectedTrack.visibility=View.GONE
            buttonSelectSong.visibility=View.VISIBLE
        }

        buttonFind.setOnClickListener {
            if (!trackMap.isNullOrEmpty() && !genreMap.isNullOrEmpty()){
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
                    trackMap["id"]!!,
                    genreMap["genre"]!!,
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

    private fun getTrack(recommendations: Recommendations): HashMap<String, String> {
        //getting random recommended track from list
        val random = (recommendations.tracks.indices).random()
        val map = HashMap<String,String>()
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

    private fun hideKeyboard(){
        val inputManager: InputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (requireActivity().currentFocus != null){
            inputManager.hideSoftInputFromWindow(requireActivity().currentFocus!!.windowToken, 0)
        }
    }

    //genre onclick
    override fun onItemClicked(genre: String) {
        genreMap["genre"] = genre
        genreAlertDialog.dismiss()
        mapCheck()
    }
        //search track dialog onclick
    override fun onItemClicked(queryResultTrackItem: QueryResultTrackItem) {
            trackMap["id"] = queryResultTrackItem.id
            trackMap["artistId"] = queryResultTrackItem.album.artists[0].id
            trackMap["name"] = queryResultTrackItem.name
            trackMap["image"] = queryResultTrackItem.album.images[0].url
            trackMap["artistName"] = queryResultTrackItem.album.artists[0].name
            songAlertDialog.dismiss()
            mapCheck()
    }

}