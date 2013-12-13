package pl.siemionczyk.otwartezabytki.rest.relicjson;

import java.io.Serializable;

public class PhotoJson implements Serializable {

	
	public PhotoFileJson file;
	
	
	public static class PhotoFileJson implements Serializable {
		public int relic_id;
		public PhotoUrlJson icon;
		public PhotoUrlJson mini;
		public PhotoUrlJson midi;
		public PhotoUrlJson maxi;
		public PhotoUrlJson full;
	}
	
	
	
}

/*"id": 1010,
"relic_id": 8501,
"author": "Dominik Porada, http://instagr.am/p/KsDP5dL9Iv/",
"date_taken": "16 maj 2012",
"alternate_text": null,
"file": {
  "url": "/system/uploads/photo/file/1010/IMG_0627.JPG",
  "icon": {
    "url": "/system/uploads/photo/file/1010/icon_IMG_0627.JPG"
  },
  "mini": {
    "url": "/system/uploads/photo/file/1010/mini_IMG_0627.JPG"
  },
  "midi": {
    "url": "/system/uploads/photo/file/1010/midi_IMG_0627.JPG"
  },
  "maxi": {
    "url": "/system/uploads/photo/file/1010/maxi_IMG_0627.JPG"
  },
  "full": {
    "url": "/system/uploads/photo/file/1010/full_IMG_0627.JPG"
  }
},
"file_full_width": 600*/