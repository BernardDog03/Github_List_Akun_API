package com.example.submissionfundamentalretrofit.ui.activity.detail

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.example.submissionfundamentalretrofit.R
import com.example.submissionfundamentalretrofit.adapter.PagerAdapter
import com.example.submissionfundamentalretrofit.data.local.UserEntity
import com.example.submissionfundamentalretrofit.databinding.ActivityDetailBinding
import com.example.submissionfundamentalretrofit.ui.FactoryViewModel
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModels: DetailViewModel by viewModels { factory }
    private lateinit var factory: FactoryViewModel

    private lateinit var avatarUrl: String
    private lateinit var type: String
    private lateinit var idFav: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USER).toString()
        val bundle = Bundle()
        bundle.putString(EXTRA_USER, username)

        factory = FactoryViewModel.getInstance(this)

        viewModels.isLoading.observe(this) {
            showLoading(it)
        }

        viewModels.detailsData(username)
        viewModels.toastMsg.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        viewModels.detailList.observe(this) {
            if (it != null) {
                binding.apply {
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .into(imgUser)
                    tvDetailUsername.text = it.login
                    tvDetailName.text = it.name
                    tvDetailFollowing.text = it.followers.toString()
                    tvDetailFollowers.text = it.following.toString()
                    tvDetailRepositories.text = it.publicRepos.toString()
                    tvDetailType.text = it.type
                    idHide.text = it.id
                }
            }
            this.avatarUrl = it.avatarUrl
            this.type = it.type
            this.idFav = it.id
        }

        viewModels.toastMsg.observe(this) { message: String ->
            showToast(message)
        }

        viewModels.getFavUser().observe(this) { list ->
            val isFavorite = list.any {
                it.username == username
            }

            binding.fabFavorite.setOnClickListener {
                val mNotificationManagerDelete =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val mNotificationManagerSave =
                    getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val entity = UserEntity(idFav, username, type, avatarUrl, false)
                try {
                    viewModels.isFavUser(entity, list.any {
                        it.username == username
                    })
                } catch (e: Exception) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show()
                }

                if (isFavorite) {
                    Toast.makeText(this, "$username remove from favorite", Toast.LENGTH_SHORT)
                        .show()
                    binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    val mBuilderDelete = NotificationCompat.Builder(this, CHANNEL_ID_DELETE)
                        .setSmallIcon(R.drawable.ic_github_mark)
                        .setLargeIcon(
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.ic_github_mark
                            )
                        )
                        .setContentTitle(resources.getString(R.string.favorite))
                        .setContentText("$username remove to favorite")
                    val notification = mBuilderDelete.build()
                    mNotificationManagerDelete.notify(NOTIFICATION_ID_DELETE, notification)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            CHANNEL_ID_DELETE,
                            CHANNEL_NAME_DELETE,
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                        channel.description = CHANNEL_NAME_DELETE
                        mBuilderDelete.setChannelId(CHANNEL_ID_DELETE)
                        mNotificationManagerDelete.createNotificationChannel(channel)
                    }
                    val notifDelete = mBuilderDelete.build()
                    mNotificationManagerDelete.notify(NOTIFICATION_ID_DELETE, notifDelete)
                } else {
                    Toast.makeText(this, "$username save to favorite", Toast.LENGTH_SHORT).show()
                    binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                    val mBuilderSave = NotificationCompat.Builder(this, CHANNEL_ID_SAVE)
                        .setSmallIcon(R.drawable.ic_github_mark)
                        .setLargeIcon(
                            BitmapFactory.decodeResource(
                                resources,
                                R.drawable.ic_github_mark
                            )
                        )
                        .setContentTitle(resources.getString(R.string.favorite))
                        .setContentText("$username save to favorite")
                    val notification = mBuilderSave.build()
                    mNotificationManagerSave.notify(NOTIFICATION_ID_SAVE, notification)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val channel = NotificationChannel(
                            CHANNEL_ID_SAVE,
                            CHANNEL_NAME_SAVE,
                            NotificationManager.IMPORTANCE_DEFAULT
                        )
                        channel.description = CHANNEL_NAME_SAVE
                        mBuilderSave.setChannelId(CHANNEL_ID_SAVE)
                        mNotificationManagerSave.createNotificationChannel(channel)
                    }
                    val notifSave = mBuilderSave.build()
                    mNotificationManagerSave.notify(NOTIFICATION_ID_SAVE, notifSave)
                }
            }
        }

        val pagerAdapter = PagerAdapter(this)
        pagerAdapter.userName = username
        binding.apply {
            viewPager.adapter = pagerAdapter
            TabLayoutMediator(tabsDetail, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
            supportActionBar?.elevation = 0f
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressCircular.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )

        private const val NOTIFICATION_ID_DELETE = 1
        private const val NOTIFICATION_ID_SAVE = 2
        private const val CHANNEL_ID_DELETE = "channel_01"
        private const val CHANNEL_ID_SAVE = "channel_02"
        private const val CHANNEL_NAME_SAVE = "Save_Channel"
        private const val CHANNEL_NAME_DELETE = "Delete_Channel"
    }
}
