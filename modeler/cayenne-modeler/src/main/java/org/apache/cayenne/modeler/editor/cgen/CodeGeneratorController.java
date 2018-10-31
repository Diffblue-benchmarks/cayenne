/*****************************************************************
 *   Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 ****************************************************************/

package org.apache.cayenne.modeler.editor.cgen;

import org.apache.cayenne.gen.ClassGenerationAction;
import org.apache.cayenne.map.DataMap;
import org.apache.cayenne.modeler.ProjectController;
import org.apache.cayenne.modeler.dialog.ErrorDebugDialog;
import org.apache.cayenne.modeler.util.CayenneController;
import org.apache.cayenne.swing.BindingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.function.Predicate;

/**
 * A controller for the class generator dialog.
 */
public class CodeGeneratorController extends CodeGeneratorControllerBase {
    /**
     * Logger to print stack traces
     */
    private static Logger logObj = LoggerFactory.getLogger(ErrorDebugDialog.class);

    protected CodeGeneratorPane view;

    protected ClassesTabController classesSelector;
    protected GeneratorTabController generatorSelector;

    public CodeGeneratorController(CayenneController parent, ProjectController projectController) {
        super(parent, projectController);
        this.classesSelector = new ClassesTabController(this);
        this.generatorSelector = new GeneratorTabController(this);
        view = new CodeGeneratorPane(generatorSelector.getView(), classesSelector.getView());
        initBindings();
        initListeners();
    }

    public void startup(DataMap dataMap) {
        super.startup(dataMap);
        classesSelectedAction();
        GeneratorController modeController = generatorSelector.getGeneratorController();
        ClassGenerationAction classGenerationAction = modeController.createGenerator();
        modeController.initForm(classGenerationAction);
        classesSelector.startup();
    }

    private void initListeners(){
        projectController.addObjEntityDisplayListener(e -> super.addToSelectedEntities(e.getEntity().getDataMap(), Collections.singleton(e.getEntity().getName())));
        projectController.addEmbeddableDisplayListener(e -> super.addToSelectedEmbeddables(e.getEmbeddable().getDataMap(), Collections.singleton(e.getEmbeddable().getClassName())));
    }

    @Override
    public Component getView() {
        return view;
    }

    protected void initBindings() {
        BindingBuilder builder = new BindingBuilder(
                getApplication().getBindingFactory(),
                this);

        builder.bindToAction(((GeneratorTabPanel)generatorSelector.getView()).getGenerateButton(), "generateAction()");
        builder.bindToAction(this, "classesSelectedAction()", SELECTED_PROPERTY);
        builder.bindToAction(generatorSelector, "generatorSelectedAction()",
                GeneratorTabController.GENERATOR_PROPERTY);

        generatorSelectedAction();
    }

    public void generatorSelectedAction() {
        GeneratorController controller = generatorSelector.getGeneratorController();
        validate(controller);

        Predicate<Object> predicate = controller != null
                ? controller.getDefaultClassFilter()
                : o -> false;

        updateSelection(predicate);
        classesSelector.classSelectedAction();
    }

    public void classesSelectedAction() {
        int size = getSelectedEntitiesSize();
        String label;

        if (size == 0) {
            label = "No entities selected";
        }
        else if (size == 1) {
            label = "One entity selected";
        }
        else {
            label = size + " entities selected";
        }

        label = label.concat("; ");
        
        int sizeEmb = getSelectedEmbeddablesSize();

        if (sizeEmb == 0) {
            label = label + "No embeddables selected";
        }
        else if (sizeEmb == 1) {
            label = label + "One embeddable selected";
        }
        else {
            label = label + sizeEmb + " embeddables selected";
        }

        label = label.concat("; ");

        if(isDataMapSelected()) {
            label = label + "DataMap selected";
        } else {
            label = label + "No dataMap selected";
        }

        ((GeneratorTabPanel)generatorSelector.getView()).getClassesCount().setText(label);
        projectController.setDirty(true);
    }

    public void generateAction() {
        ClassGenerationAction generator = generatorSelector.getGenerator();

        if (generator != null) {
            try {
                generator.prepareArtifacts();
                generator.execute();
                JOptionPane.showMessageDialog(
                        this.getView(),
                        "Class generation finished");
            } catch (Exception e) {
                logObj.error("Error generating classes", e);
                JOptionPane.showMessageDialog(
                        this.getView(),
                        "Error generating classes - " + e.getMessage());
            }
        }
    }
}
