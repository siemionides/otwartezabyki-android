package pl.siemionczyk.otwartezabytki.rest.relicjson;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;

import pl.siemionczyk.otwartezabytki.helper.MyLog;

public class RelicJson implements Serializable {

    private static final String TAG = "RelicJson";
    public int id;
	public String dating_of_obj;
	public String identification;
	public String description;
	public String place_name;

    public String register_number;
    public double latitude;
    public double longitude;

    public ArrayList<String> categories;

    public ArrayList<String> tags;

    /** entries stands for trivie / interesting facts*/
    public ArrayList <EntryJson> entries;

    /** For events : dates + names*/
    public ArrayList <EventJson> events;

    public ArrayList<PhotoJson> photos;

    /** Keeps main photo in there! */
    public PhotoJson main_photo;


    public RelicJson ( String identification ){
        this.identification = identification;
    }

	@Override
	public String toString() {
		return "dating: " + dating_of_obj + ", name: " + identification + " place:" + place_name + " description:" + description;
//        String returnStr = (identification);
//        MyLog.i( TAG,  returnStr);


//        return returnStr;
	}




    public String[] getUrlsToMaxiPhotos(){
        String[] urls = new String[ photos.size()];

        int i = 0;
        for ( PhotoJson pJ : photos){
            urls[i++] = pJ.file.full.url;
        }

        return urls;
    }


    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ż}]", "");
    }


    public static class EntryJson implements Serializable {
        public String title;
        public String body;
    }


    /** For events : dates + names*/
    public static class EventJson implements Serializable {
        public String date;
        public String name;

    }

	
}

//"id": 42548,
//"nid_id": "633145",
//"identification": "Ratusz, ob. muzeum",
//"common_name": "",
//"description": "",
//"categories": [
//  "uzytecznosci_publicznej",
//  "sportowy_kulturalny_edukacyjny"
//],
//"state": "unchecked",
//"register_number": "161 (167) z 18.11.1959",
//"dating_of_obj": "1745-1761",
//"street": "Rynek Ko��ciuszki 10",
//"latitude": 53.1323752,
//"longitude": 23.1586182,
//"tags": [],
//"country_code": "PL",
//"fprovince": null,
//"fplace": null,
//"documents_info": null,
//"links_info": null,
	//"main_photo": {
	//  "id": null,
	//  "relic_id": null,
	//  "author": null,
	//  "date_taken": null,
	//  "alternate_text": null,
	//  "file": {
	//    "url": "fallback/photo_default.png",
	//    "icon": {
	//      "url": "fallback/photo_icon_default.png"
	//    },
	//    "mini": {
	//      "url": "fallback/photo_mini_default.png"
	//    },
	//    "midi": {
	//      "url": "fallback/photo_midi_default.png"
	//    },
	//    "maxi": {
	//      "url": "fallback/photo_maxi_default.png"
	//    },
	//    "full": {
	//      "url": "fallback/photo_full_default.png"
	//    }
	//  },
	//  "file_full_width": null
	//},
//"events": [],
//"entries": [],
//"links": [],
//"documents": [],
//"alerts": [],
//"descendants": [],
//"photos": [],
//"place_id": 89547,
//"place_name": "Bia��ystok",
//"commune_name": "Bia��ystok",
//"district_name": "Bia��ystok",
//"voivodeship_name": "podlaskie"