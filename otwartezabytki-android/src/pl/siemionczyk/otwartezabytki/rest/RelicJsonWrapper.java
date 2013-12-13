package pl.siemionczyk.otwartezabytki.rest;

import java.io.Serializable;
import java.util.ArrayList;

import pl.siemionczyk.otwartezabytki.rest.relicjson.RelicJson;

public class RelicJsonWrapper implements Serializable {
	
	public ArrayList<RelicJson> relics;
}
