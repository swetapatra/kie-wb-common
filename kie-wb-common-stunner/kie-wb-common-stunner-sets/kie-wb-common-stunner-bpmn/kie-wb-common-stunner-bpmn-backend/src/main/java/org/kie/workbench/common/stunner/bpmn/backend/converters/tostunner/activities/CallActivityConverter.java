/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.backend.converters.tostunner.activities;

import org.eclipse.bpmn2.CallActivity;
import org.kie.workbench.common.stunner.bpmn.backend.converters.TypedFactoryManager;
import org.kie.workbench.common.stunner.bpmn.backend.converters.tostunner.properties.CallActivityPropertyReader;
import org.kie.workbench.common.stunner.bpmn.backend.converters.tostunner.properties.PropertyReaderFactory;
import org.kie.workbench.common.stunner.bpmn.definition.ReusableSubprocess;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.CalledElement;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.Independent;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.IsAsync;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.IsMultipleInstance;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.MultipleInstanceCollectionInput;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.MultipleInstanceCollectionOutput;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.MultipleInstanceCompletionCondition;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.MultipleInstanceDataInput;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.MultipleInstanceDataOutput;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.OnEntryAction;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.OnExitAction;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.ReusableSubprocessTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.WaitForCompletion;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.view.View;

import static org.kie.workbench.common.stunner.core.util.StringUtils.hasNonEmpty;

public class CallActivityConverter extends BaseCallActivityConverter<ReusableSubprocess, ReusableSubprocessTaskExecutionSet> {

    public CallActivityConverter(TypedFactoryManager factoryManager,
                                 PropertyReaderFactory propertyReaderFactory) {
        super(factoryManager, propertyReaderFactory);
    }

    @Override
    protected Node<View<ReusableSubprocess>, Edge> createNode(CallActivity activity, CallActivityPropertyReader p) {
        return factoryManager.newNode(activity.getId(), ReusableSubprocess.class);
    }

    @Override
    protected ReusableSubprocessTaskExecutionSet createReusableSubprocessTaskExecutionSet(CallActivity activity,
                                                                                          CallActivityPropertyReader p) {
        ReusableSubprocessTaskExecutionSet executionSet = new ReusableSubprocessTaskExecutionSet(new CalledElement(activity.getCalledElement()),
                                                                                                 new Independent(p.isIndependent()),
                                                                                                 new WaitForCompletion(p.isWaitForCompletion()),
                                                                                                 new IsAsync(p.isAsync()),
                                                                                                 new IsMultipleInstance(),
                                                                                                 new MultipleInstanceCollectionInput(p.getCollectionInput()),
                                                                                                 new MultipleInstanceDataInput(p.getDataInput()),
                                                                                                 new MultipleInstanceCollectionOutput(p.getCollectionOutput()),
                                                                                                 new MultipleInstanceDataOutput(p.getDataOutput()),
                                                                                                 new MultipleInstanceCompletionCondition(p.getCompletionCondition()),
                                                                                                 new OnEntryAction(p.getOnEntryAction()),
                                                                                                 new OnExitAction(p.getOnExitAction()));

        boolean multipleInstance = hasNonEmpty(executionSet.getMultipleInstanceCollectionInput().getValue(),
                                               executionSet.getMultipleInstanceDataInput().getValue(),
                                               executionSet.getMultipleInstanceCollectionOutput().getValue(),
                                               executionSet.getMultipleInstanceDataOutput().getValue(),
                                               executionSet.getMultipleInstanceCompletionCondition().getValue());
        executionSet.setIsMultipleInstance(new IsMultipleInstance(multipleInstance));
        return executionSet;
    }
}
