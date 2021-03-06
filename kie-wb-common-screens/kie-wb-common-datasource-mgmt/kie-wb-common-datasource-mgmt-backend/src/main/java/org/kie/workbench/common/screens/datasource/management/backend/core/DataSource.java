/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.screens.datasource.management.backend.core;

import java.sql.Connection;

import org.kie.workbench.common.screens.datasource.management.model.DataSourceStatus;

/**
 * Defines a data source managed by the data sources management system.
 */
public interface DataSource {

    Connection getConnection() throws Exception;

    DataSourceStatus getStatus();

    boolean isReferenced();

    boolean isStale();

    boolean isNew();

    /**
     * Clients of a data source may be interested in listening data source events to be aware of changes
     * in the data source configuration. When a data source becomes STALE clients should re-invoke the lookup
     * operation in order to get a safe reference to the data source.
     */
    void addDataSourceListener( DataSourceListener listener );

    void removeDataSourceListener( DataSourceListener listener );

}
