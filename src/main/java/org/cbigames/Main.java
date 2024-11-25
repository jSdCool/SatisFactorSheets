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
import java.util.Arrays;

public class Main implements WindowListener {
    public static void main(String[] args) {
        new Main();
    }

    JFrame frame;
    JPanel mainPanel;
    JLabel makeSelection, completion;
    JComboBox<Recipe> recipeSelector;

    JTextField recipeFilter;

    Recipe[] selectableRecipes;

    JButton generateButton, manageAltsButton;

    Main(){
        selectableRecipes = Generator.recipes;
        frame = new JFrame("SatisfactorSheets");
        frame.setSize(550,250);
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
            doGeneration(selection);
            completion.setText("Generated "+selection.getOutputItem()+".xlsx");
            mainPanel.repaint();
        });

        mainPanel.add(generateButton);

        manageAltsButton = new JButton("Manage Alt Recipes");
        manageAltsButton.setBounds(330,80,200,30);
        manageAltsButton.setEnabled(false);
        manageAltsButton.setToolTipText("Coming Soon TM");
        mainPanel.add(manageAltsButton);

        completion = new JLabel();
        completion.setBounds(10,120,400,30);
        mainPanel.add(completion);

        mainPanel.repaint();
    }

    static void doGeneration(Recipe r){
        try {
            assert r != null;
            FileOutputStream fis = new FileOutputStream(r.getOutputItem()+".xlsx");
            Generator.generate(r,fis);
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