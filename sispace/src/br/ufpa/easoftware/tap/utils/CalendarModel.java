/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpa.easoftware.tap.utils;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author jefferson
 */
public class CalendarModel extends AbstractTableModel {
  String[] days = { "DOM", "SEG", "TER", "QUA", "QUI", "SEX", "SAB" };

  int[] numDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

  String[][] calendar = new String[7][7];

  public CalendarModel() {
    for (int i = 0; i < days.length; ++i)
      calendar[0][i] = days[i];
    for (int i = 1; i < 7; ++i)
      for (int j = 0; j < 7; ++j)
        calendar[i][j] = " ";
  }

  public int getRowCount() {
    return 7;
  }

  public int getColumnCount() {
    return 7;
  }

  public Object getValueAt(int row, int column) {
    return calendar[row][column];
  }

  public void setValueAt(Object value, int row, int column) {
    calendar[row][column] = (String) value;
  }

  public void setMonth(int year, int month) {
    for (int i = 1; i < 7; ++i)
      for (int j = 0; j < 7; ++j)
        calendar[i][j] = " ";
    java.util.GregorianCalendar cal = new java.util.GregorianCalendar();
    cal.set(year, month, 1);
    int offset = cal.get(java.util.GregorianCalendar.DAY_OF_WEEK) - 1;
    offset += 7;
    int num = daysInMonth(year, month);
    for (int i = 0; i < num; ++i) {
      calendar[offset / 7][offset % 7] = Integer.toString(i + 1);
      ++offset;
    }
  }

  public boolean isLeapYear(int year) {
    if (year % 4 == 0)
      return true;
    return false;
  }

  public int daysInMonth(int year, int month) {
    int days = numDays[month];
    if (month == 1 && isLeapYear(year))
      ++days;
    return days;
  }
}
