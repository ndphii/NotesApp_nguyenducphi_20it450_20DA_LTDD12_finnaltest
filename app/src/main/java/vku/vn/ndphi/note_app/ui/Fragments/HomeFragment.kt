package vku.vn.ndphi.note_app.ui.Fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import vku.vn.ndphi.note_app.Model.Notes
import vku.vn.ndphi.note_app.R
import vku.vn.ndphi.note_app.ViewModel.NotesViewModel
import vku.vn.ndphi.note_app.databinding.FragmentHomeBinding
import vku.vn.ndphi.note_app.ui.Adapter.NotesAdapter

class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    private val viewModel:NotesViewModel by viewModels()
    private var oldMyNotes = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)

        setHasOptionsMenu(true)

        //get all notes
        viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
            val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
            adapter = NotesAdapter(requireContext(), notesList)
            oldMyNotes = notesList as ArrayList<Notes>
            binding.rcvAllNotes.adapter = adapter
        }

        //filter all notes
        binding.allNotes.setOnClickListener{
            viewModel.getNotes().observe(viewLifecycleOwner) { notesList ->
                val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                adapter = NotesAdapter(requireContext(), notesList)
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.adapter = adapter
            }
        }
        //filter high notes
        binding.filterHigh.setOnClickListener{
            viewModel.getHighNotes().observe(viewLifecycleOwner) { notesList ->
                val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                adapter = NotesAdapter(requireContext(), notesList)
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.adapter = adapter
            }
        }

        //filter medium notes
        binding.filterMedium.setOnClickListener{
            viewModel.getMediumNotes().observe(viewLifecycleOwner) { notesList ->
                val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                adapter = NotesAdapter(requireContext(), notesList)
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.adapter = adapter
            }
        }
        //filter low notes
        binding.filterLow.setOnClickListener{
            viewModel.getLowNotes().observe(viewLifecycleOwner) { notesList ->
                val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
                binding.rcvAllNotes.layoutManager = staggeredGridLayoutManager
                adapter = NotesAdapter(requireContext(), notesList)
                oldMyNotes = notesList as ArrayList<Notes>
                binding.rcvAllNotes.adapter = adapter
            }
        }

        binding.btnAddNotes.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_createNotesFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu,menu)

        val item =menu.findItem(R.id.app_bar_search)
        val searchView= item.actionView as SearchView
        searchView.queryHint="Nhập từ khóa ..."
        searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                NotesFilteting(p0.toString())
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun NotesFilteting(p0:String){
        val newFilteredList= arrayListOf<Notes>()
        for (i in oldMyNotes){
            if (i.title.contains(p0!!) || i.subTitle.contains(p0!!)){
                newFilteredList.add(i)
            }
        }
        adapter.filtering(newFilteredList)
    }
}

