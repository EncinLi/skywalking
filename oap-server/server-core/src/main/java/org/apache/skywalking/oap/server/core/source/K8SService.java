/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.apache.skywalking.oap.server.core.source;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.skywalking.oap.server.core.analysis.IDManager;
import org.apache.skywalking.oap.server.core.analysis.ISourceDecorator;
import org.apache.skywalking.oap.server.core.analysis.Layer;
import org.apache.skywalking.oap.server.core.analysis.SourceDecoratorManager;

import static org.apache.skywalking.oap.server.core.source.DefaultScopeDefine.K8S_SERVICE;
import static org.apache.skywalking.oap.server.core.source.DefaultScopeDefine.SERVICE_CATALOG_NAME;

@Data
@ScopeDeclaration(id = K8S_SERVICE, name = "K8SService", catalog = SERVICE_CATALOG_NAME)
@ScopeDefaultColumn.VirtualColumnDefinition(fieldName = "entityId", columnName = "entity_id", isID = true, type = String.class)
public class K8SService extends K8SMetrics {

    private volatile String entityId;

    @ScopeDefaultColumn.DefinedByField(columnName = "name", requireDynamicActive = true)
    private String name;
    public Layer layer;

    private DetectPoint detectPoint;

    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "attr0", isAttribute = true)
    private String attr0;
    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "attr1", isAttribute = true)
    private String attr1;
    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "attr2", isAttribute = true)
    private String attr2;
    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "attr3", isAttribute = true)
    private String attr3;
    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "attr4", isAttribute = true)
    private String attr4;
    @Getter
    @Setter
    @ScopeDefaultColumn.DefinedByField(columnName = "attr5", isAttribute = true)
    private String attr5;

    @Override
    public int scope() {
        return K8S_SERVICE;
    }

    @Override
    public void prepare() {
        entityId = IDManager.ServiceID.buildId(name, layer.isNormal());
    }

    /**
     * Get the decorator through given name and invoke.
     * @param decorator The decorator class simpleName.
     */
    public void decorate(String decorator) {
        ISourceDecorator<ISource> sourceDecorator = SourceDecoratorManager.DECORATOR_MAP.get(decorator);
        sourceDecorator.decorate(this);
    }
}
