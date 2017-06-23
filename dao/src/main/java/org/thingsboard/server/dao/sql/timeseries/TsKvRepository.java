/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thingsboard.server.dao.sql.timeseries;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.thingsboard.server.common.data.EntityType;
import org.thingsboard.server.dao.annotation.SqlDao;
import org.thingsboard.server.dao.model.sql.TsKvCompositeKey;
import org.thingsboard.server.dao.model.sql.TsKvEntity;

import java.util.List;
import java.util.UUID;

@SqlDao
public interface TsKvRepository extends CrudRepository<TsKvEntity, TsKvCompositeKey> {

    @Query("SELECT tskv FROM TsKvEntity tskv WHERE tskv.entityId = :entityId " +
            "AND tskv.entityType = :entityType AND tskv.key = :entityKey " +
            "AND tskv.ts > :startTs AND tskv.ts < :endTs ORDER BY tskv.ts DESC")
    List<TsKvEntity> findAllWithLimit(@Param("entityId") UUID entityId,
                                      @Param("entityType") EntityType entityType,
                                      @Param("entityKey") String key,
                                      @Param("startTs") long startTs,
                                      @Param("endTs") long endTs,
                                      Pageable pageable);

    @Query("SELECT new TsKvEntity(MAX(tskv.strValue), MAX(tskv.longValue), MAX(tskv.doubleValue)) FROM TsKvEntity tskv " +
            "WHERE tskv.entityId = :entityId AND tskv.entityType = :entityType " +
            "AND tskv.key = :entityKey AND tskv.ts > :startTs AND tskv.ts < :endTs")
    TsKvEntity findMax(@Param("entityId") UUID entityId,
                       @Param("entityType") EntityType entityType,
                       @Param("entityKey") String entityKey,
                       @Param("startTs") long startTs,
                       @Param("endTs") long endTs);

    @Query("SELECT new TsKvEntity(MIN(tskv.strValue), MIN(tskv.longValue), MIN(tskv.doubleValue)) FROM TsKvEntity tskv " +
            "WHERE tskv.entityId = :entityId AND tskv.entityType = :entityType " +
            "AND tskv.key = :entityKey AND tskv.ts > :startTs AND tskv.ts < :endTs")
    TsKvEntity findMin(@Param("entityId") UUID entityId,
                       @Param("entityType") EntityType entityType,
                       @Param("entityKey") String entityKey,
                       @Param("startTs") long startTs,
                       @Param("endTs") long endTs);


    @Query("SELECT new TsKvEntity(COUNT(tskv.booleanValue), COUNT(tskv.strValue), COUNT(tskv.longValue), COUNT(tskv.doubleValue)) FROM TsKvEntity tskv " +
            "WHERE tskv.entityId = :entityId AND tskv.entityType = :entityType " +
            "AND tskv.key = :entityKey AND tskv.ts > :startTs AND tskv.ts < :endTs")
    TsKvEntity findCount(@Param("entityId") UUID entityId,
                         @Param("entityType") EntityType entityType,
                         @Param("entityKey") String entityKey,
                         @Param("startTs") long startTs,
                         @Param("endTs") long endTs);


    @Query("SELECT new TsKvEntity(AVG(tskv.longValue), AVG(tskv.doubleValue)) FROM TsKvEntity tskv " +
            "WHERE tskv.entityId = :entityId AND tskv.entityType = :entityType " +
            "AND tskv.key = :entityKey AND tskv.ts > :startTs AND tskv.ts < :endTs")
    TsKvEntity findAvg(@Param("entityId") UUID entityId,
                       @Param("entityType") EntityType entityType,
                       @Param("entityKey") String entityKey,
                       @Param("startTs") long startTs,
                       @Param("endTs") long endTs);


    @Query("SELECT new TsKvEntity(SUM(tskv.longValue), SUM(tskv.doubleValue)) FROM TsKvEntity tskv " +
            "WHERE tskv.entityId = :entityId AND tskv.entityType = :entityType " +
            "AND tskv.key = :entityKey AND tskv.ts > :startTs AND tskv.ts < :endTs")
    TsKvEntity findSum(@Param("entityId") UUID entityId,
                       @Param("entityType") EntityType entityType,
                       @Param("entityKey") String entityKey,
                       @Param("startTs") long startTs,
                       @Param("endTs") long endTs);
}
