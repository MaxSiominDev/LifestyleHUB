package dev.maxsiomin.authlib.data.mappers

import dev.maxsiomin.authlib.data.room.UserEntity
import dev.maxsiomin.authlib.domain.UserInfo

internal class UserInfoToUserEntityMapper : (UserInfo) -> UserEntity {

    override fun invoke(userInfo: UserInfo): UserEntity {
        return UserEntity(
            username = userInfo.username,
            passwordHash = userInfo.passwordHash,
            fullName = userInfo.fullName,
            avatarUrl = userInfo.avatarUrl,
        )
    }

}