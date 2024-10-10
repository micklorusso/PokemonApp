package com.example.pokemonapp.features.auth.data.service

import com.example.pokemonapp.features.auth.data.model.Favourite
import com.example.pokemonapp.features.auth.data.model.User
import com.example.pokemonapp.features.auth.data.model.UserData
import com.example.pokemonapp.features.auth.domain.AccountService
import com.example.pokemonapp.features.auth.domain.FavouriteService
import com.example.pokemonapp.features.auth.domain.FirebaseService
import com.example.pokemonapp.features.auth.domain.StorageService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FirebaseServiceImpl @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService,
    private val favouriteService: FavouriteService
): FirebaseService {

    private val _user = MutableStateFlow(User())
    override val user = _user.asStateFlow()

    private val _userData = MutableStateFlow(UserData())
    override val userData = _userData.asStateFlow()

    private val _favourite = MutableStateFlow(emptyList<Favourite>())
    override val favourite = _favourite.asStateFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            accountService.currentUser.collectLatest { currentUser ->
                _user.value = currentUser
                if(!currentUser.isAnonymous) {
                    launch {
                        storageService.watchUserData(currentUser.id).collectLatest { currentUserData ->
                            _userData.value = currentUserData ?: UserData()
                        }
                    }
                  launch {
                      favouriteService.watchFavourite(currentUser.id).collectLatest { favourite ->
                          _favourite.value = favourite ?: emptyList()
                      }
                  }
                }
                else{
                    _user.value = User()
                    _userData.value = UserData()
                    _favourite.value = emptyList()
                }
            }
        }
    }
}