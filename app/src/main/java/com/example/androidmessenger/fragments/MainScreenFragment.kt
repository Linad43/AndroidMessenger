package com.example.androidmessenger.fragments

import android.Manifest
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidmessenger.R
import com.example.androidmessenger.databinding.FragmentMainScreenBinding
import com.example.androidmessenger.saveLog.MyPreference
import com.example.androidmessenger.service.MainScreenPager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging

class MainScreenFragment : Fragment() {

    private val fragments = arrayListOf(
        "Чаты" to ViewPagerChatsFragment(),
        "Пользователи" to ViewPagerContactsFragment()
    )
    private lateinit var adapter: MainScreenPager
    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var sharedPref = MyPreference(requireContext())
        val permission = Manifest.permission.POST_NOTIFICATIONS
        requestPermissionLauncher.launch(permission)

        adapter = MainScreenPager(this, fragments)
        binding.viewPager.adapter = adapter
        binding.toolbar.setTitle(sharedPref.getLogin())
        binding.toolbar.inflateMenu(R.menu.main_menu)

        FirebaseMessaging
            .getInstance()
            .token
            .addOnCompleteListener(
                OnCompleteListener
                { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }
//                    Toast.makeText(
//                        requireContext(),
//                        "Complete token",
//                        Toast.LENGTH_SHORT
//                    ).show()
                })
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.exit -> requireActivity().finishAffinity()
                R.id.editProfile -> findNavController().navigate(R.id.action_menuFragment_to_editProfileFragment)
                R.id.exitFromAcc -> {
                    sharedPref.delLogin()
                    sharedPref.delPassword()
                    FirebaseAuth.getInstance().signOut()
                    findNavController().navigate(R.id.action_menuFragment_to_logInFragment)
                }
            }
            true
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = fragments[position].first
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
//            Toast.makeText(
//                requireContext(),
//                "Уведомление разрешены",
//                Toast.LENGTH_SHORT
//            ).show()
        } else {
            Toast.makeText(
                requireContext(),
                "В разрешении отказано",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
//    private fun askNotificationPermission() {
//        // This is only necessary for API level >= 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(
//                    requireContext(),
//                    Manifest.permission.POST_NOTIFICATIONS
//                ) ==
//                PackageManager.PERMISSION_GRANTED
//            ) {
//                // FCM SDK (and your app) can post notifications.
//            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
//                // TODO: display an educational UI explaining to the user the features that will be enabled
//                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
//                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
//                //       If the user selects "No thanks," allow the user to continue without notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//            }
//        }
//    }
}