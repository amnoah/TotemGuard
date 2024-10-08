/*
 * This file is part of TotemGuard - https://github.com/Bram1903/TotemGuard
 * Copyright (C) 2024 Bram and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.deathmotion.totemguard.manager;

import com.deathmotion.totemguard.TotemGuard;
import com.github.retrooper.packetevents.protocol.entity.type.EntityType;
import lombok.Getter;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class EntityCacheManager {
    private final ConcurrentHashMap<UUID, ConcurrentHashMap<Integer, EntityType>> cache;

    private final TotemGuard plugin;

    public EntityCacheManager(TotemGuard plugin) {
        this.cache = new ConcurrentHashMap<>();

        this.plugin = plugin;
    }

    public Map<Integer, EntityType> getUserCache(@NonNull UUID uuid) {
        return this.cache.computeIfAbsent(uuid, u -> new ConcurrentHashMap<>());
    }

    public void removeUserCache(@NonNull UUID uuid) {
        this.cache.remove(uuid);
    }

    public Optional<EntityType> getCachedEntity(@NonNull UUID uuid, int entityId) {
        return Optional.ofNullable(getUserCache(uuid).get(entityId));
    }

    public void addLivingEntity(@NonNull UUID uuid, int entityId, @NonNull EntityType cachedEntity) {
        getUserCache(uuid).put(entityId, cachedEntity);
    }

    public void removeEntity(@NonNull UUID uuid, int entityId) {
        getUserCache(uuid).remove(entityId);
    }

    public void resetUserCache(@NonNull UUID uuid) {
        getUserCache(uuid).clear();
    }
}
