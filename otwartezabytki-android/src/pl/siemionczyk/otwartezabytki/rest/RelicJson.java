package pl.siemionczyk.otwartezabytki.rest;

import java.util.ArrayList;

public class RelicJson {

	public int id;
	public String dating_of_obj;
	public String identification;
	public String description;
	public String place_name;
	
	public ArrayList<PhotoJson> photos;
	
	@Override
	public String toString() {
		return "dating: " + dating_of_obj + ", name: " + identification + " place:" + place_name + " description:" + description;
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