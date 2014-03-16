/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * JFlex 1.5                                                               *
 * Copyright (C) 1998-2009  Gerwin Klein <lsf@jflex.de>                    *
 * All rights reserved.                                                    *
 *                                                                         *
 * License: BSD                                                            *
 *                                                                         *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package jflex;

/**
 * Stores an interval of characters together with the character class
 *
 * A character belongs to an interval, if its Unicode value is greater than or equal
 * to the Unicode value of <CODE>start</code> and smaller than or euqal to the Unicode
 * value of <CODE>end</code>.
 *
 * All characters of the interval must belong to the same character class.
 *
 * @author Gerwin Klein
 * @version JFlex 1.5, $Revision: 586 $, $Date: 2010-03-07 19:59:36 +1100 (Sun, 07 Mar 2010) $
 */
public class CharClassInterval {

  /**
   * The first character of the interval
   */
  int start;

  /**
   * The last character of the interval
   */
  int end;

  /**
   * The code of the class all characters of this interval belong to.
   */
  int charClass;
  

  /**
   * Creates a new CharClassInterval from <CODE>start</code> to <CODE>end</code>
   * that belongs to character class <CODE>charClass</code>.
   *
   * @param start         The first character of the interval
   * @param end           The last character of the interval  
   * @param charClass     The code of the class all characters of this interval belong to.
   */
  public CharClassInterval(int start, int end, int charClass) {
    this.start = start;
    this.end = end;
    this.charClass = charClass;
  }

  /**
   * returns string representation of this class interval
   */
  public String toString() {
    return "["+start+"-"+end+"="+charClass+"]";
  }
}
