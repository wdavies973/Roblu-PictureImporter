package com.cpjd2.models;

import com.cpjd2.models.metrics.RMetric;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * Welcome to the belly of the beast! (Not really)
 * This class models what data a team should store.
 *
 * @version 2
 * @since 3.0.0
 * @author Will Davies
 */
@SuppressWarnings("unused")
public class RTeam implements Serializable {

    /**
     * Changing this versionUID will render this class incompatible with older versions.
     */
    public static final long serialVersionUID = 1L;

    /**
     * Unique identifier for this team. No duplicate IDs allowed. Completely arbitrary.
     */
    private int ID;

    /**
     * Name of this team, can be really anything. Usually will be pulled from the Blue Alliance.
     */
    private String name;

    /**
     * Number of this team
     */
    private int number;

    /**
     * Time stamp of last edit made to this team. Also used for resolving merge conflicts
     */
    private long lastEdit;

    /**
     * Stores the scouting data. See RTab for more info.
     * tabs.get(0) is always the PIT tab
     * tabs.get(1) is always the Predictions tab
     */
    private ArrayList<RTab> tabs;

    private byte[] image;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    /**
     * tabs.get(page) is the page that the user looked at last
     */
    private int page;

    /**
     * In order to make the user base happier by downloading less data,
     * TBA data is only downloaded once. Roblu can infer whether the download
     * happened by checking the below data.
     *
     * These aren't used by RobluScouter
     */
    private String fullName, location, motto, website;
    private int rookieYear;

    /**
     * The empty constructor is required for de-serialization
     */
    @SuppressWarnings("unused")
    public RTeam() {}

    /**
     * Creates a new RTeam with default values
     * @param name the team's name
     * @param number the team's number
     * @param ID the arbitrary, unique identifier for this team
     */
    public RTeam(String name, int number, int ID) {
        this.name = name;
        this.number = number;
        this.ID = ID;
        this.page = 1; // set default page to PIT

        lastEdit = 0;
    }

    /**
     * verify() makes sure that the form and team are synchronized. Here's what it does:
     * <p>
     * PIT:
     * -If the user modified the form and ADDED elements, then we'll make sure to add them to this team's form copy
     * -If the user modified the form and REMOVED elements, then we'll make sure to remove them from this team's form copy
     * -If the user changed any item titles, change them right away
     * -If the user changed any default values, reset all the values on all elements that have NOT been modified
     * -If the user changed the order of any elements, change the order
     * <p>
     * MATCH:
     * -If the user modified the match form and ADDED elements, then we'll add those to EVERY match profile
     * -If the user modified the match form and REMOVED elements, then we'll remove those from EVERY match profile
     * -If the user changed any item titles, change them on ALL match profiles
     * -If the user changed any default values, reset all the values of EVERY match that have NOT been modified
     * -If the user changed the order of any elements, change the order
     * <p>
     * PREMISE:
     * -PIT and MATCH form arrays may NOT be null, only empty
     * <p>
     * NULLS to check for:
     * -If the team has never been opened before, set the PIT values, matches don't need to be set until creation.
     */
    public void verify(RForm form) {

    }

    /**
     * Adds the tab to the team
     * @param tab the new RTab to add
     * @return index of sorted, newly added tab
     */
    int addTab(RTab tab) {
        return 1;
    }
    /**
     * Deletes the tab from the RTabs array
     * @param position the index or position of the tab to delete
     */
    public void removeTab(int position) {
        tabs.remove(position);
    }

    /**
     * Shortcut method to get the elements from the specified tab
     * @param page the tab index
     * @return the form elements from that index
     */
    public ArrayList<RMetric> getMetrics(int page) {
        return tabs.get(page).getMetrics();
    }
    /**
     * Removes all the tab except the PIT and PREDICTIONS tabs
     */
    public void removeAllTabsButPIT() {
        if(tabs == null || tabs.size() == 0) return;

        RTab pit = tabs.get(0);
        RTab predictions = tabs.get(1);
        tabs.clear();
        tabs.add(pit);
        tabs.add(predictions);
    }
    /**
     * Returns the number of matches this team is in
     * @return returns the number of matches this team contains
     */
    public int getNumMatches() {
        if(tabs == null) return 0;
        else return tabs.size() - 2;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public long getLastEdit() {
        return lastEdit;
    }

    public void setLastEdit(long lastEdit) {
        this.lastEdit = lastEdit;
    }

    public ArrayList<RTab> getTabs() {
        return tabs;
    }

    public void setTabs(ArrayList<RTab> tabs) {
        this.tabs = tabs;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getRookieYear() {
        return rookieYear;
    }

    public void setRookieYear(int rookieYear) {
        this.rookieYear = rookieYear;
    }
}
