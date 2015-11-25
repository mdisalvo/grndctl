/**
 * This file is part of grndctl.
 *
 * grndctl is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * grndctl is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with grndctl.  If not, see <http://www.gnu.org/licenses/>.
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
    private String frequencyKhz;
    @XmlElement
    private String latitudeDeg;
    @XmlElement
    private String longitudeDeg;
    @XmlElement
    private String elevationFt;
    @XmlElement
    private String isoCountry;
    @XmlElement
    private String dmeFrequencyKhz;
    @XmlElement
    private String dmeChannel;
    @XmlElement
    private String dmeLatitudeDeg;
    @XmlElement
    private String dmeLongitudeDeg;
    @XmlElement
    private String dmeElevationFt;
    @XmlElement
    private String slavedVariationDeg;
    @XmlElement
    private String magneticVariationDeg;
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

    public String getFrequencyKhz() {
        return frequencyKhz;
    }

    public void setFrequencyKhz(String frequencyKhz) {
        this.frequencyKhz = frequencyKhz;
    }

    public String getLatitudeDeg() {
        return latitudeDeg;
    }

    public void setLatitudeDeg(String latitudeDeg) {
        this.latitudeDeg = latitudeDeg;
    }

    public String getLongitudeDeg() {
        return longitudeDeg;
    }

    public void setLongitudeDeg(String longitudeDeg) {
        this.longitudeDeg = longitudeDeg;
    }

    public String getElevationFt() {
        return elevationFt;
    }

    public void setElevationFt(String elevationFt) {
        this.elevationFt = elevationFt;
    }

    public String getIsoCountry() {
        return isoCountry;
    }

    public void setIsoCountry(String isoCountry) {
        this.isoCountry = isoCountry;
    }

    public String getDmeFrequencyKhz() {
        return dmeFrequencyKhz;
    }

    public void setDmeFrequencyKhz(String dmeFrequencyKhz) {
        this.dmeFrequencyKhz = dmeFrequencyKhz;
    }

    public String getDmeChannel() {
        return dmeChannel;
    }

    public void setDmeChannel(String dmeChannel) {
        this.dmeChannel = dmeChannel;
    }

    public String getDmeLatitudeDeg() {
        return dmeLatitudeDeg;
    }

    public void setDmeLatitudeDeg(String dmeLatitudeDeg) {
        this.dmeLatitudeDeg = dmeLatitudeDeg;
    }

    public String getDmeLongitudeDeg() {
        return dmeLongitudeDeg;
    }

    public void setDmeLongitudeDeg(String dmeLongitudeDeg) {
        this.dmeLongitudeDeg = dmeLongitudeDeg;
    }

    public String getDmeElevationFt() {
        return dmeElevationFt;
    }

    public void setDmeElevationFt(String dmeElevationFt) {
        this.dmeElevationFt = dmeElevationFt;
    }

    public String getSlavedVariationDeg() {
        return slavedVariationDeg;
    }

    public void setSlavedVariationDeg(String slavedVariationDeg) {
        this.slavedVariationDeg = slavedVariationDeg;
    }

    public String getMagneticVariationDeg() {
        return magneticVariationDeg;
    }

    public void setMagneticVariationDeg(String magneticVariationDeg) {
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

        if (id != null ? !id.equals(navaid.id) : navaid.id != null) return false;
        if (filename != null ? !filename.equals(navaid.filename) : navaid.filename != null) return false;
        if (ident != null ? !ident.equals(navaid.ident) : navaid.ident != null) return false;
        if (name != null ? !name.equals(navaid.name) : navaid.name != null) return false;
        if (type != null ? !type.equals(navaid.type) : navaid.type != null) return false;
        if (frequencyKhz != null ? !frequencyKhz.equals(navaid.frequencyKhz) : navaid.frequencyKhz != null)
            return false;
        if (latitudeDeg != null ? !latitudeDeg.equals(navaid.latitudeDeg) : navaid.latitudeDeg != null) return false;
        if (longitudeDeg != null ? !longitudeDeg.equals(navaid.longitudeDeg) : navaid.longitudeDeg != null)
            return false;
        if (elevationFt != null ? !elevationFt.equals(navaid.elevationFt) : navaid.elevationFt != null) return false;
        if (isoCountry != null ? !isoCountry.equals(navaid.isoCountry) : navaid.isoCountry != null) return false;
        if (dmeFrequencyKhz != null ? !dmeFrequencyKhz.equals(navaid.dmeFrequencyKhz) : navaid.dmeFrequencyKhz != null)
            return false;
        if (dmeChannel != null ? !dmeChannel.equals(navaid.dmeChannel) : navaid.dmeChannel != null) return false;
        if (dmeLatitudeDeg != null ? !dmeLatitudeDeg.equals(navaid.dmeLatitudeDeg) : navaid.dmeLatitudeDeg != null)
            return false;
        if (dmeLongitudeDeg != null ? !dmeLongitudeDeg.equals(navaid.dmeLongitudeDeg) : navaid.dmeLongitudeDeg != null)
            return false;
        if (dmeElevationFt != null ? !dmeElevationFt.equals(navaid.dmeElevationFt) : navaid.dmeElevationFt != null)
            return false;
        if (slavedVariationDeg != null ? !slavedVariationDeg.equals(navaid.slavedVariationDeg) : navaid.slavedVariationDeg != null)
            return false;
        if (magneticVariationDeg != null ? !magneticVariationDeg.equals(navaid.magneticVariationDeg) : navaid.magneticVariationDeg != null)
            return false;
        if (usageType != null ? !usageType.equals(navaid.usageType) : navaid.usageType != null) return false;
        if (power != null ? !power.equals(navaid.power) : navaid.power != null) return false;
        return !(associatedAirport != null ? !associatedAirport.equals(navaid.associatedAirport) : navaid.associatedAirport != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (filename != null ? filename.hashCode() : 0);
        result = 31 * result + (ident != null ? ident.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (frequencyKhz != null ? frequencyKhz.hashCode() : 0);
        result = 31 * result + (latitudeDeg != null ? latitudeDeg.hashCode() : 0);
        result = 31 * result + (longitudeDeg != null ? longitudeDeg.hashCode() : 0);
        result = 31 * result + (elevationFt != null ? elevationFt.hashCode() : 0);
        result = 31 * result + (isoCountry != null ? isoCountry.hashCode() : 0);
        result = 31 * result + (dmeFrequencyKhz != null ? dmeFrequencyKhz.hashCode() : 0);
        result = 31 * result + (dmeChannel != null ? dmeChannel.hashCode() : 0);
        result = 31 * result + (dmeLatitudeDeg != null ? dmeLatitudeDeg.hashCode() : 0);
        result = 31 * result + (dmeLongitudeDeg != null ? dmeLongitudeDeg.hashCode() : 0);
        result = 31 * result + (dmeElevationFt != null ? dmeElevationFt.hashCode() : 0);
        result = 31 * result + (slavedVariationDeg != null ? slavedVariationDeg.hashCode() : 0);
        result = 31 * result + (magneticVariationDeg != null ? magneticVariationDeg.hashCode() : 0);
        result = 31 * result + (usageType != null ? usageType.hashCode() : 0);
        result = 31 * result + (power != null ? power.hashCode() : 0);
        result = 31 * result + (associatedAirport != null ? associatedAirport.hashCode() : 0);
        return result;
    }
}
