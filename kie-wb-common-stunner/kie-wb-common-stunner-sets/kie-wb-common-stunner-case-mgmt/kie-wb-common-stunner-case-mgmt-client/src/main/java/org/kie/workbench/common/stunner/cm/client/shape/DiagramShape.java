/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kie.workbench.common.stunner.cm.client.shape;

import org.kie.workbench.common.stunner.cm.client.shape.view.DiagramView;
import org.kie.workbench.common.stunner.core.client.shape.MutationContext;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;
import org.kie.workbench.common.stunner.shapes.client.BasicContainerShape;
import org.kie.workbench.common.stunner.shapes.def.RectangleShapeDef;

public class DiagramShape<W> extends BasicContainerShape<W, RectangleShapeDef<W>, DiagramView> {

    public DiagramShape(final RectangleShapeDef<W> shapeDef,
                        final DiagramView view) {
        super(shapeDef,
              view);
    }

    @Override
    public void applyProperties(final Node<View<W>, Edge> element,
                                final MutationContext mutationContext) {
        super.applyProperties(element,
                              mutationContext);
        // Apply diagram attribute values for width and height.
        final Double width = getShapeDefinition().getWidth(getDefinition(element));
        final Double height = getShapeDefinition().getHeight(getDefinition(element));
        getDefViewHandler().getViewHandler().applySize(width,
                                                       height,
                                                       mutationContext);
    }
}