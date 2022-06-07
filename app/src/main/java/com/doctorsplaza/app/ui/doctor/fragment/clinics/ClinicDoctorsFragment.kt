package com.doctorsplaza.app.ui.doctor.fragment.clinics

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.doctorsplaza.app.R
import com.doctorsplaza.app.databinding.FragmentClinicBinding
import com.doctorsplaza.app.databinding.FragmentDoctoHomeBinding
import com.doctorsplaza.app.ui.doctor.fragment.clinics.adapter.DoctorClinicsAdapter
import com.doctorsplaza.app.ui.patient.fragments.appointments.AppointmentViewModel
import com.doctorsplaza.app.ui.patient.fragments.bookAppointment.adapter.BookTimeAdapter
import com.doctorsplaza.app.ui.patient.fragments.clinicDoctors.adapter.ClinicDoctorsAdapter
import com.doctorsplaza.app.ui.patient.fragments.home.HomeViewModel
import com.doctorsplaza.app.utils.DoctorPlazaLoader
import com.doctorsplaza.app.utils.Resource
import com.doctorsplaza.app.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ClinicDoctorsFragment : Fragment(R.layout.fragment_clinic) , View.OnClickListener {
    private lateinit var binding: FragmentClinicBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private val appointmentViewModel: AppointmentViewModel by viewModels()

    @Inject
    lateinit var session: SessionManager

    private var currentView: View? = null

    private lateinit var doctorPlazaLoader: DoctorPlazaLoader

    @Inject
    lateinit var bookTimeAdapter: BookTimeAdapter

    @Inject
    lateinit var clinicDoctorsAdapter: DoctorClinicsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (currentView == null) {
            currentView = inflater.inflate(R.layout.fragment_clinic, container, false)
            binding = FragmentClinicBinding.bind(currentView!!)
            init()
            setObserver()
            setOnClickListener()
        }
        return currentView!!
    }


    private fun init() {
        doctorPlazaLoader = DoctorPlazaLoader(requireContext())
        setClinicsData()

    }

    private fun setObserver() {
        homeViewModel.patientBanner.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    if (response.data?.status == 200) {
                        setClinicsData()
                    }
                }
                is Resource.Loading -> {
                }
                is Resource.Error -> {
                }
            }
        }

    }

    private fun setClinicsData() {

        binding.clinicRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = clinicDoctorsAdapter
        }
    }

    private fun setOnClickListener() {
        with(binding) {

        }

        clinicDoctorsAdapter.setOnViewAppointments {
            findNavController().navigate(R.id.doctorAppointmentFragment)
        }

        clinicDoctorsAdapter.setOnViewClinicDetails {
            findNavController().navigate(R.id.clinicDetailsFragment)
        }
    }


    override fun onClick(v: View?) {
        when (v?.id) {

        }
    }
}
