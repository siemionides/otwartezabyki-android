package pl.siemionczyk.otwartezabytki.rest;

import java.io.Serializable;
import java.util.ArrayList;

import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

public class RelicJsonWrapper implements Serializable {


    /** Does not come from server, it's manually injected in order
     * to repeat the same query when refreshing the relics list by
     * adding new relics by pull-to-refresh*/
    public DetailsOfSearchQuery searchQueryDetails;

    public MetaRelicJsonResponse meta;

	public ArrayList<RelicJson> relics;




    public RelicJsonWrapper( ArrayList<RelicJson> relics){
        this.relics = relics;
    }

    public RelicJsonWrapper( ){
    }


    public static class MetaRelicJsonResponse implements Serializable {

        public int total_pages;
        public int current_page;
        public int relics_count; //per page
        public int total_count;
    }


    /** THis class keeps details of serach query*/
    public static class DetailsOfSearchQuery implements Serializable {

        public String relicName;
        public String relicPlace;
        public String dateFrom;
        public String dateTo;
        public boolean onlyWithPhotos;
    }
}
