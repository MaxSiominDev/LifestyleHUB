package dev.maxsiomin.authlib.data.mappers

import dev.maxsiomin.authlib.domain.UserInfo
import dev.maxsiomin.authlib.data.room.UserEntity

internal class UserEntityToUserInfoMapper : (UserEntity) -> UserInfo {

    override fun invoke(entity: UserEntity): UserInfo {
        return UserInfo(
            username = entity.username,
            passwordHash = entity.passwordHash,
            avatarUrl = entity.avatarUrl,
            fullName = entity.fullName,
        )
    }

}