/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * JFlex 1.5                                                               *
 * Copyright (C) 1998-2009  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package jflex.gui;

/**
 * Constants used for GridLayout
 *
 * @author Gerwin Klein
 * @version JFlex 1.5, $Revision: 586 $, $Date: 2010-03-07 19:59:36 +1100 (Sun, 07 Mar 2010) $
 */
public interface Handles {

  int FILL = 0;

  int TOP  = 1;
  int TOP_LEFT = TOP;
  int TOP_CENTER = 2; 
  int TOP_RIGHT = 3;

  int CENTER_LEFT = 4;
  int CENTER = 5;
  int CENTER_CENTER = CENTER;
  int CENTER_RIGHT = 6;

  int BOTTOM = 7;
  int BOTTOM_LEFT = BOTTOM;
  int BOTTOM_CENTER = 8;
  int BOTTOM_RIGHT = 9;       

}

