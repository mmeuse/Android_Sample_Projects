/* Matthew Meuse
 * matt.meuse@gmail.com
 * */
package net.cs76.projects.GunksExperiment90857932;

import android.database.sqlite.SQLiteDatabase;

public class ClimbDictionary 
{
	//Populate the SQL Lite Database with Climbs. Ideally I would have parsed an XML file to populate the database
	//In the future, I would like to use web services, to retrieve the climbs from a server during app initialization
	public static void PopulateClimbs(SQLiteDatabase db) 
	{
		ClimbingRouteDBAdapter.createClimb(db,"High Exposure","5.6","260 vertical feet",
				"This is the most classic 5.6 in new England. Climb up the first pitch to the GT Ledge. Setup a belay and bring up your second. At the start of the second pitch, pull the bulge and diagonal right and up for 40ft until you reach the top. Setup a belay and bring up your second.",
				"2 pitches","No","No","highexposure.JPG");
		
		ClimbingRouteDBAdapter.createClimb(db,"Ari Aria","5.8","200 vertical feet",
				"The first pitch is the hardest so muster up your crack climbing skills and go for it. Setup a belay off the chain anchors. For the second pitch diagonal up and right. It is airy and the pro is scarce. Setup of an optional hanging belay at the end of the traverse or proceed upwards for 40 feet to top out and set up a belay.",
				"3 pitches","No","No","");
		
		ClimbingRouteDBAdapter.createClimb(db,"Cascading Crystal Kaleidoscope","5.7","350 vertical feet",
				"With solid moves work your way up pitch one and bring up your second. Pitch 2 has an optional 5.8 variation so be sure to conserve energy. Pitch 3, the 'money pitch' is a harrowing traverse right for 30 feet into the exposed blue yonder. Setup an optional belay for you second here and top out on one of the best 7's around.",
				"3 pitches","No","No","cck.JPG");
		
		ClimbingRouteDBAdapter.createClimb(db,"Thin Slabs Direct","5.7","300 vertical feet",
				"As Dick William's would say ', just nut up and go for it'. With these words, climb the two easy pitches. The third and final pitch is a scary, pumpy hand traverse with some manky pins that demand clipping! So bring your rosary beads and a few Hail Mary’s and go for it. Double ropes not recommended!",
				"2 pitches","No","No","");
		
		ClimbingRouteDBAdapter.createClimb(db,"Bunny","5.4","100 vertical feet",
				"This is the guide book author's first Gunks climb (tears streaming) so enjoy it! Climb over several small bulges to a tree belay and admire the view of the valley which seems to plunge for a 1000 ft. down from the top of this wonderful climb.",
				"1 pitches","No","No","bunny.JPG");
		
		ClimbingRouteDBAdapter.createClimb(db,"Frogs Head","5.6","250 vertical feet",
				"Climb the 3 excellent pitches of this Gunks climb. Can you find the frog's head? I haven't been able too.",
				"2 pitches","No","No","frogs.JPG");
		
		ClimbingRouteDBAdapter.createClimb(db,"Maria","5.6","230 vertical feet",
				"Yet another Gunk classic. Climb the easy first pitch and set up a belay. Traverse 50 ft. on the second pitch and finish it up strong following up the left facing corner.",
				"2 pitches","No","No","maria.JPG");
		
		ClimbingRouteDBAdapter.createClimb(db,"Jackie","5.5","200 vertical feet",
				"Three Pitches of moderate clean quartzite granite! There is nothing more to be said. Just climb and enjoy!",
				"2 pitches","No","No","jackie.JPG");
		
		ClimbingRouteDBAdapter.createClimb(db,"Betty","5.3","220 vertical feet",
				"Climb up, climb right, climb up, climb left, climb up...Got it?",
				"2 pitches","No","No","betty.JPG");
		
		ClimbingRouteDBAdapter.createClimb(db,"Bonnie's Roof","5.9","200 vertical feet",
				"Climb the burly 5.9 pitch until you get up to the bottom of the overhang. Then channel your inner monkey and pull yourself to the top! Good job, you deserve a banana.",
				"2 pitches","No","No","bonnies.JPG");

	}
}
