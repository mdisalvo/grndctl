/**
 * MIT License
 *
 * Copyright (c) 2017 grndctl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.grndctl.model.flightplan;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 *
 */
public class Navaid {

    private String id;
    @XmlElement
    private String filename;
    @XmlElement
    private String ident;
    @XmlElement
    private String name;
    @XmlElement
    private String type;
    @XmlElement
    private int frequencyKhz;
    @XmlElement
    private double latitudeDeg;
    @XmlElement
    private double longitudeDeg;
    @XmlElement
    private int elevationFt;
    @XmlElement
    private String isoCountry;
    @XmlElement
    private int dmeFrequencyKhz;
    @XmlElement
    private String dmeChannel;
    @XmlElement
    private double dmeLatitudeDeg;
    @XmlElement
    private double dmeLongitudeDeg;
    @XmlElement
    private int dmeElevationFt;
    @XmlElement
    private double slavedVariationDeg;
    @XmlElement
    private double magneticVariationDeg;
    @XmlElement
    private String usageType;
    @XmlElement
    private String power;
    @XmlElement
    private String associatedAirport;

    public Navaid() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getIdent() {
        return ident;
    }

    public void setIdent(String ident) {
        this.ident = ident;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFrequencyKhz() {
        return frequencyKhz;
    }

    public void setFrequencyKhz(int frequencyKhz) {
        this.frequencyKhz = frequencyKhz;
    }

    public double getLatitudeDeg() {
        return latitudeDeg;
    }

    public void setLatitudeDeg(double latitudeDeg) {
        this.latitudeDeg = latitudeDeg;
    }

    public double getLongitudeDeg() {
        return longitudeDeg;
    }

    public void setLongitudeDeg(double longitudeDeg) {
        this.longitudeDeg = longitudeDeg;
    }

    public int getElevationFt() {
        return elevationFt;
    }

    public void setElevationFt(int elevationFt) {
        this.elevationFt = elevationFt;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public int getDmeFrequencyKhz() {
        return dmeFrequencyKhz;
    }

    public void setDmeFrequencyKhz(int dmeFrequencyKhz) {
        this.dmeFrequencyKhz = dmeFrequencyKhz;
    }

    public String getDmeChannel() {
        return dmeChannel;
    }

    public void setDmeChannel(String dmeChannel) {
        this.dmeChannel = dmeChannel;
    }

    public double getDmeLatitudeDeg() {
        return dmeLatitudeDeg;
    }

    public void setDmeLatitudeDeg(double dmeLatitudeDeg) {
        this.dmeLatitudeDeg = dmeLatitudeDeg;
    }

    public double getDmeLongitudeDeg() {
        return dmeLongitudeDeg;
    }

    public void setDmeLongitudeDeg(double dmeLongitudeDeg) {
        this.dmeLongitudeDeg = dmeLongitudeDeg;
    }

    public int getDmeElevationFt() {
        return dmeElevationFt;
    }

    public void setDmeElevationFt(int dmeElevationFt) {
        this.dmeElevationFt = dmeElevationFt;
    }

    public double getSlavedVariationDeg() {
        return slavedVariationDeg;
    }

    public void setSlavedVariationDeg(double slavedVariationDeg) {
        this.slavedVariationDeg = slavedVariationDeg;
    }

    public double getMagneticVariationDeg() {
        return magneticVariationDeg;
    }

    public void setMagneticVariationDeg(double magneticVariationDeg) {
        this.magneticVariationDeg = magneticVariationDeg;
    }

    public String getUsageType() {
        return usageType;
    }

    public void setUsageType(String usageType) {
        this.usageType = usageType;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getAssociatedAirport() {
        return associatedAirport;
    }

    public void setAssociatedAirport(String associatedAirport) {
        this.associatedAirport = associatedAirport;
    }

    @Override
    public String toString() {
        return "Navaid[" +
                "name='" + name + '\'' +
                ", ident='" + ident + '\'' +
                ", type='" + type + '\'' +
                ", filename='" + filename + '\'' +
                ", frequencyKhz=" + frequencyKhz +
                ", latitudeDeg=" + latitudeDeg +
                ", longitudeDeg=" + longitudeDeg +
                ", elevationFt=" + elevationFt +
                ", isoCountry='" + isoCountry + '\'' +
                ", dmeFrequencyKhz=" + dmeFrequencyKhz +
                ", dmeChannel='" + dmeChannel + '\'' +
                ", dmeLatitudeDeg=" + dmeLatitudeDeg +
                ", dmeLongitudeDeg=" + dmeLongitudeDeg +
                ", dmeElevationFt=" + dmeElevationFt +
                ", slavedVariationDeg=" + slavedVariationDeg +
                ", magneticVariationDeg=" + magneticVariationDeg +
                ", usageType='" + usageType + '\'' +
                ", power='" + power + '\'' +
                ", associatedAirport='" + associatedAirport + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Navaid navaid = (Navaid) o;

        if (frequencyKhz != navaid.frequencyKhz) return false;
        if (Double.compare(navaid.latitudeDeg, latitudeDeg) != 0) return false;
        if (Double.compare(navaid.longitudeDeg, longitudeDeg) != 0) return false;
        if (elevationFt != navaid.elevationFt) return false;
        if (dmeFrequencyKhz != navaid.dmeFrequencyKhz) return false;
        if (Double.compare(navaid.dmeLatitudeDeg, dmeLatitudeDeg) != 0) return false;
        if (Double.compare(navaid.dmeLongitudeDeg, dmeLongitudeDeg) != 0) return false;
        if (dmeElevationFt != navaid.dmeElevationFt) return false;
        if (Double.compare(navaid.slavedVariationDeg, slavedVariationDeg) != 0) return false;
        if (Double.compare(navaid.magneticVariationDeg, magneticVariationDeg) != 0) return false;
        if (id != null ? !id.equals(navaid.id) : navaid.id != null) return false;
        if (filename != null ? !filename.equals(navaid.filename) : navaid.filename != null) return false;
        if (ident != null ? !ident.equals(navaid.ident) : navaid.ident != null) return false;
        if (name != null ? !name.equals(navaid.name) : navaid.name != null) return false;
        if (type != null ? !type.equals(navaid.type) : navaid.type != null) return false;
        if (isoCountry != null ? !isoCountry.equals(navaid.isoCountry) : navaid.isoCountry != null) return false;
        if (dmeChannel != null ? !dmeChannel.equals(navaid.dmeChannel) : navaid.dmeChannel != null) return false;
        if (usageType != null ? !usageType.equals(navaid.usageType) : navaid.usageType != null) return false;
        if (power != null ? !power.equals(navaid.power) : navaid.power != null) return false;
        return !(associatedAirport != null ? !associatedAirport.equals(navaid.associatedAirport) : navaid.associatedAirport != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (ident != null ? ident.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + frequencyKhz;
        temp = Double.doubleToLongBits(latitudeDeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitudeDeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + elevationFt;
        result = 31 * result + (isoCountry != null ? isoCountry.hashCode() : 0);
        result = 31 * result + dmeFrequencyKhz;
        result = 31 * result + (dmeChannel != null ? dmeChannel.hashCode() : 0);
        temp = Double.doubleToLongBits(dmeLatitudeDeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dmeLongitudeDeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + dmeElevationFt;
        temp = Double.doubleToLongBits(slavedVariationDeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(magneticVariationDeg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (usageType != null ? usageType.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (associatedAirport != null ? associatedAirport.hashCode() : 0);
        return result;
    }
}
