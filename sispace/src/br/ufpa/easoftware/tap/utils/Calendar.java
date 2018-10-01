/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.utils;

/**
 *
 * @author jefferson
 */
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

public class Calendar extends JFrame {
  String[] years = { "2015", "2016", "2017","2018" };

  JComboBox comboBox = new JComboBox(years);

  String[] months = { "January", "February", "March", "April", "May", "June", "July", "August",
      "September", "October", "November", "December" };

  JList list = new JList(months);

  JScrollPane scrollPane = new JScrollPane(list);

  CalendarModel model = new CalendarModel();

  JTable table = new JTable(model);

  public Calendar() {
    super();
    
    getContentPane().setLayout(null);
    comboBox.setBounds(10, 10, 100, 30);
    comboBox.setSelectedIndex(0);
    comboBox.addItemListener(new ComboHandler());
    scrollPane.setBounds(200, 10, 150, 100);
    list.setSelectedIndex(3);
    list.addListSelectionListener(new ListHandler());
    table.setBounds(10, 150, 550, 200);
    model.setMonth(comboBox.getSelectedIndex() + 1998, list.getSelectedIndex());
    getContentPane().add(comboBox);
    getContentPane().add(scrollPane);
    table.setGridColor(Color.black);
    table.setShowGrid(true);
    getContentPane().add(table);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setSize(500, 500);
    setVisible(true);
  }

  public static void main(String[] args) {
    Calendar app = new Calendar();
  }
  public class ComboHandler implements ItemListener {
    public void itemStateChanged(ItemEvent e) {
      model.setMonth(comboBox.getSelectedIndex() + 2015, list.getSelectedIndex());
      table.repaint();
    }
  }

  public class ListHandler implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      model.setMonth(comboBox.getSelectedIndex() + 2015, list.getSelectedIndex());
      table.repaint();
    }
  }
}
