/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import net.webservicex.BarcodeOption;
import net.webservicex.BarcodeType;
import net.webservicex.CheckSumMethod;
import net.webservicex.ImageFormats;
import net.webservicex.ShowTextPosition;

/**
 *
 * @author Osito
 */

public class TMP {
    protected int height;
    protected int width;
    protected int angle;
    protected int ratio;
    protected int module;
    protected int left;
    protected int top;
    protected boolean checkSum;
    protected String fontName;
    protected String barColor;
    protected String bgColor;
    protected float fontSize;
    protected BarcodeOption barcodeOption;
    protected BarcodeType barcodeType;
    protected CheckSumMethod checkSumMethod;
    protected ShowTextPosition showTextPosition;
    protected ImageFormats barCodeImageFormat;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getModule() {
        return module;
    }

    public void setModule(int module) {
        this.module = module;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public boolean isCheckSum() {
        return checkSum;
    }

    public void setCheckSum(boolean checkSum) {
        this.checkSum = checkSum;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getBarColor() {
        return barColor;
    }

    public void setBarColor(String barColor) {
        this.barColor = barColor;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public BarcodeOption getBarcodeOption() {
        return barcodeOption;
    }

    public void setBarcodeOption(BarcodeOption barcodeOption) {
        this.barcodeOption = barcodeOption;
    }

    public BarcodeType getBarcodeType() {
        return barcodeType;
    }

    public void setBarcodeType(BarcodeType barcodeType) {
        this.barcodeType = barcodeType;
    }

    public CheckSumMethod getCheckSumMethod() {
        return checkSumMethod;
    }

    public void setCheckSumMethod(CheckSumMethod checkSumMethod) {
        this.checkSumMethod = checkSumMethod;
    }

    public ShowTextPosition getShowTextPosition() {
        return showTextPosition;
    }

    public void setShowTextPosition(ShowTextPosition showTextPosition) {
        this.showTextPosition = showTextPosition;
    }

    public ImageFormats getBarCodeImageFormat() {
        return barCodeImageFormat;
    }

    public void setBarCodeImageFormat(ImageFormats barCodeImageFormat) {
        this.barCodeImageFormat = barCodeImageFormat;
    }
    
    
    
}
