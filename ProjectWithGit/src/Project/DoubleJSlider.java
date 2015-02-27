package Project;

import javax.swing.*;

/**
 * <b>Programm:</b> WaveJNI<br>
 * <b>Copyright:</b> 2002 Andreas Gohr, Frank Schubert, Milan Altenburg<br>
 * <b>License:</b> GPL2 or higher<br>
 * <br>
 * <b>Info:</b> This JSlider uses doubles for its values
 */
public class DoubleJSlider extends JSlider{
  
  static final double double_MINIMUM = 0.0;
  static final double double_MAXIMUM = 100.0;
  static final double double_MIDDLE = 50.0;
  static final double PRECISION_MULTIPLIER  = 100;
  /**
   * Constructor - initializes with 0.0,100.0,50.0
   */
  public DoubleJSlider(){
    super();
    setdoubleMinimum(double_MINIMUM);
    setdoubleMaximum(double_MAXIMUM);
    setdoubleValue(double_MIDDLE);
  }

  /**
   * Constructor
   */
  public DoubleJSlider(double min, double max, double val){
    super();
    setdoubleMinimum(min);
    setdoubleMaximum(max);
    setdoubleValue(val);
  }

  /**
   * returns Maximum in double precision
   */
  public double getdoubleMaximum() {
    return( getMaximum()/double_MAXIMUM );
  }

  /**
   * returns Minimum in double precision
   */
  public double getdoubleMinimum() {
    return( getMinimum()/double_MAXIMUM );
  }

  /**
   * returns Value in double precision
   */
  public double getdoubleValue() {
    return( getValue()/double_MAXIMUM );
  }

  /**
   * sets Maximum in double precision
   */
  public void setdoubleMaximum(double max) {
    setMaximum((int)(max*PRECISION_MULTIPLIER));
  }

  /**
   * sets Minimum in double precision
   */
  public void setdoubleMinimum(double min) {
    setMinimum((int) (min*PRECISION_MULTIPLIER));
  }

  /**
   * sets Value in double precision
   */
  public void setdoubleValue(double val) {
    setValue((int)(val*PRECISION_MULTIPLIER));
    setToolTipText(Double.toString(val));
  }

}
