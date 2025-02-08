package org.cbigames;

import org.cbigames.satisfactorsheets.Generator;
import org.cbigames.satisfactorsheets.Recipe;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main implements WindowListener {
    public static void main(String[] args) {
        new Main();
    }

    JFrame frame;
    JPanel mainPanel;
    JLabel makeSelection, completion, enabledText,disabledText;
    JComboBox<Recipe> recipeSelector;

    JTextField recipeFilter, disabledFilter;

    Recipe[] selectableRecipes, disabledMaster;

    JButton generateButton, enableAlt,disableAlt;

    JList<Recipe> disabledAlts, enabledAlts;

    Main(){
        selectableRecipes = Generator.recipes;
        frame = new JFrame("SatisfactorSheets");
        frame.setSize(700,440);
        mainPanel = new JPanel();
        mainPanel.setLayout(null);
        frame.add(mainPanel);
        frame.setVisible(true);
        frame.addWindowListener(this);
        makeSelection = new JLabel("Select Recipe");
        makeSelection.setBounds(10,10,150,30);
        mainPanel.add(makeSelection);
        recipeSelector = new JComboBox<>(selectableRecipes);
        recipeSelector.setBounds(10,40,300,30);
        mainPanel.add(recipeSelector);
        recipeFilter = new JTextField();
        recipeFilter.setToolTipText("Filter the options in the drop down box");
        recipeFilter.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                String filterText = recipeFilter.getText();
                selectableRecipes = Arrays.stream(Generator.recipes).filter(
                        recipe -> recipe.getOutputItem().toLowerCase().contains(filterText.toLowerCase())
                ).toArray(Recipe[]::new);
                mainPanel.remove(recipeSelector);
                recipeSelector = new JComboBox<>(selectableRecipes);
                recipeSelector.setBounds(10,40,300,30);
                mainPanel.add(recipeSelector);
                mainPanel.repaint();
            }

            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        recipeFilter.setBounds(330,40,200,30);
        mainPanel.add(recipeFilter);

        generateButton = new JButton("Generate");
        generateButton.setBounds(10,80,100,30);

        generateButton.addActionListener( action ->{
            //generate here
            Recipe selection = (Recipe)recipeSelector.getSelectedItem();
            System.out.println(selection);
            assert selection != null;
            ArrayList<Recipe> enabled = new ArrayList<>();
            for (int i=0;i<enabledAlts.getModel().getSize();i++){
                enabled.add(enabledAlts.getModel().getElementAt(i));
            }
            doGeneration(selection,enabled.toArray(new Recipe[]{}));
            completion.setText("Generated "+selection.getOutputItem()+".xlsx");
            mainPanel.repaint();
        });

        mainPanel.add(generateButton);

        completion = new JLabel();
        completion.setBounds(10,120,400,30);
        mainPanel.add(completion);

        disabledAlts = new JList<>(Generator.altRecipes);
        disabledMaster = Generator.altRecipes;
        enabledAlts = new JList<>();

        disabledAlts.setBounds(10,190,300,200);
        enabledAlts.setBounds(350,190,300,200);

        JScrollPane sc1 = new JScrollPane(disabledAlts);
        sc1.setBounds(9,190,300,200);
        JScrollPane sc2 = new JScrollPane(enabledAlts);
        sc2.setBounds(351,190,300,200);
        mainPanel.add(sc1);
        mainPanel.add(sc2);

        enableAlt = new JButton(">");
        disableAlt = new JButton("<");

        enableAlt.setBounds(309,200,41,30);
        disableAlt.setBounds(309,260,41,30);

        enableAlt.addActionListener( action ->{
            List<Recipe> selection = disabledAlts.getSelectedValuesList();
            //add the alts to the enabled collection
            ArrayList<Recipe> enabled = new ArrayList<>(selection);
            for (int i=0;i<enabledAlts.getModel().getSize();i++){
                enabled.add(enabledAlts.getModel().getElementAt(i));
            }
            enabledAlts.setListData(enabled.toArray(new Recipe[]{}));
            //remove the alts from the disabled collection
            ArrayList<Recipe> disabled = new ArrayList<>();
            for (Recipe r : disabledMaster) {
                boolean add = true;
                for (Recipe re : selection) {
                    if (r == re) {
                        add = false;
                        break;
                    }
                }
                if (add) {
                    disabled.add(r);
                }
            }
            disabledMaster = disabled.toArray(new Recipe[]{});
            doAltFilter();

        });
        disableAlt.addActionListener( action ->{
            List<Recipe> selection = enabledAlts.getSelectedValuesList();

            ArrayList<Recipe> disabled = new ArrayList<>(selection);
            Collections.addAll(disabled, disabledMaster);
            disabledMaster = disabled.toArray(new Recipe[]{});
            doAltFilter();

            ArrayList<Recipe> enabled = new ArrayList<>();
            for (int i=0;i<enabledAlts.getModel().getSize();i++){
                Recipe r = enabledAlts.getModel().getElementAt(i);
                boolean add = true;
                for(Recipe re: selection){
                    if(r == re){
                        add = false;
                        break;
                    }
                }
                if(add){
                    enabled.add(r);
                }
            }
            enabledAlts.setListData(enabled.toArray(new Recipe[]{}));
        });

        mainPanel.add(enableAlt);
        mainPanel.add(disableAlt);

        enabledText = new JLabel("Enabled Alts");
        disabledText = new JLabel("Disabled Alts");

        disabledText.setBounds(10,165,100,30);
        enabledText.setBounds(350,165,100,30);
        mainPanel.add(disabledText);
        mainPanel.add(enabledText);

        disabledFilter = new JTextField();
        disabledFilter.setBounds(90,160,220,30);
        mainPanel.add(disabledFilter);

        disabledFilter.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                doAltFilter();
            }

            @Override
            public void keyPressed(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        mainPanel.repaint();

    }

    void doAltFilter(){
        String filterText = disabledFilter.getText().toLowerCase();
        Recipe[] filteredAlts = Arrays.stream(disabledMaster).filter( r -> r.toString().toLowerCase().contains(filterText)).toArray(Recipe[]::new);
        disabledAlts.setListData(filteredAlts);
    }


    static void doGeneration(Recipe r, Recipe[] enabledAlts){
        try {
            assert r != null;
            FileOutputStream fis = new FileOutputStream(r.getOutputItem()+".xlsx");
            Generator.generate(r,fis, enabledAlts);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {}

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowIconified(WindowEvent e) {}

    @Override
    public void windowDeiconified(WindowEvent e) {}

    @Override
    public void windowActivated(WindowEvent e) { }

    @Override
    public void windowDeactivated(WindowEvent e) { }
}