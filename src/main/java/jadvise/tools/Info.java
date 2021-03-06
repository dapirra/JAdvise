package jadvise.tools;

import java.util.Random;

/**
 * @author David Pirraglia
 */
public final class Info {

	/**
	 * A list of 100 most popular boy and girl names for the last 100 years.
	 *
	 * @see <a href="https://www.ssa.gov/OACT/babynames/decades/century.html">
	 *     https://www.ssa.gov/OACT/babynames/decades/century.html<a/>
	 */
	public static final String[] FIRST_NAMES = {
			// Boy Girl
			"James", "Mary",
			"John", "Patricia",
			"Robert", "Jennifer",
			"Michael", "Linda",
			"William", "Elizabeth",
			"David", "Barbara",
			"Richard", "Susan",
			"Joseph", "Jessica",
			"Thomas", "Sarah",
			"Charles", "Karen",
			"Christopher", "Nancy",
			"Daniel", "Lisa",
			"Matthew", "Margaret",
			"Anthony", "Betty",
			"Donald", "Sandra",
			"Mark", "Ashley",
			"Paul", "Dorothy",
			"Steven", "Kimberly",
			"Andrew", "Emily",
			"Kenneth", "Donna",
			"Joshua", "Michelle",
			"Kevin", "Carol",
			"Brian", "Amanda",
			"George", "Melissa",
			"Edward", "Deborah",
			"Ronald", "Stephanie",
			"Timothy", "Rebecca",
			"Jason", "Laura",
			"Jeffrey", "Sharon",
			"Ryan", "Cynthia",
			"Jacob", "Kathleen",
			"Gary", "Amy",
			"Nicholas", "Shirley",
			"Eric", "Angela",
			"Jonathan", "Helen",
			"Stephen", "Anna",
			"Larry", "Brenda",
			"Justin", "Pamela",
			"Scott", "Nicole",
			"Brandon", "Samantha",
			"Benjamin", "Katherine",
			"Samuel", "Emma",
			"Frank", "Ruth",
			"Gregory", "Christine",
			"Raymond", "Catherine",
			"Alexander", "Debra",
			"Patrick", "Rachel",
			"Jack", "Carolyn",
			"Dennis", "Janet",
			"Jerry", "Virginia",
			"Tyler", "Maria",
			"Aaron", "Heather",
			"Jose", "Diane",
			"Henry", "Julie",
			"Adam", "Joyce",
			"Douglas", "Victoria",
			"Nathan", "Kelly",
			"Peter", "Christina",
			"Zachary", "Lauren",
			"Kyle", "Joan",
			"Walter", "Evelyn",
			"Harold", "Olivia",
			"Jeremy", "Judith",
			"Ethan", "Megan",
			"Carl", "Cheryl",
			"Keith", "Martha",
			"Roger", "Andrea",
			"Gerald", "Frances",
			"Christian", "Hannah",
			"Terry", "Jacqueline",
			"Sean", "Ann",
			"Arthur", "Gloria",
			"Austin", "Jean",
			"Noah", "Kathryn",
			"Lawrence", "Alice",
			"Jesse", "Teresa",
			"Joe", "Sara",
			"Bryan", "Janice",
			"Billy", "Doris",
			"Jordan", "Madison",
			"Albert", "Julia",
			"Dylan", "Grace",
			"Bruce", "Judy",
			"Willie", "Abigail",
			"Gabriel", "Marie",
			"Alan", "Denise",
			"Juan", "Beverly",
			"Logan", "Amber",
			"Wayne", "Theresa",
			"Ralph", "Marilyn",
			"Roy", "Danielle",
			"Eugene", "Diana",
			"Randy", "Brittany",
			"Vincent", "Natalie",
			"Russell", "Sophia",
			"Louis", "Rose",
			"Philip", "Isabella",
			"Bobby", "Alexis",
			"Johnny", "Kayla",
			"Bradley", "Charlotte"
	};

	/**
	 * A list of the most common last names in the United States.
	 *
	 * @see <a href="https://en.wikipedia.org/wiki/List_of_most_common_surnames_in_North_America#United_States_(American)">
	 *     https://en.wikipedia.org/wiki/List_of_most_common_surnames_in_North_America#United_States_(American)</a>
	 */
	public static final String[] LAST_NAMES = {
			"Smith",
			"Johnson",
			"Williams",
			"Brown",
			"Jones",
			"Miller",
			"Davis",
			"Garcia",
			"Rodriguez",
			"Wilson",
			"Martinez",
			"Anderson",
			"Taylor",
			"Thomas",
			"Hernandez",
			"Moore",
			"Martin",
			"Jackson",
			"Thompson",
			"White",
			"Lopez",
			"Lee",
			"Gonzalez",
			"Harris",
			"Clark",
			"Lewis",
			"Robinson",
			"Walker",
			"Perez",
			"Hall",
			"Young",
			"Allen",
			"Sanchez",
			"Wright",
			"King",
			"Scott",
			"Green",
			"Baker",
			"Adams",
			"Nelson",
			"Hill",
			"Ramirez",
			"Campbell",
			"Mitchell",
			"Roberts",
			"Carter",
			"Phillips",
			"Evans",
			"Turner",
			"Torres",
			"Parker",
			"Collins",
			"Edwards",
			"Stewart",
			"Flores",
			"Morris",
			"Nguyen",
			"Murphy",
			"Rivera",
			"Cook",
			"Rogers",
			"Morgan",
			"Peterson",
			"Cooper",
			"Reed",
			"Bailey",
			"Bell",
			"Gomez",
			"Kelly",
			"Howard",
			"Ward",
			"Cox",
			"Diaz",
			"Richardson",
			"Wood",
			"Watson",
			"Brooks",
			"Bennett",
			"Gray",
			"James",
			"Reyes",
			"Cruz",
			"Hughes",
			"Price",
			"Myers",
			"Long",
			"Foster",
			"Sanders",
			"Ross",
			"Morales",
			"Powell",
			"Sullivan",
			"Russell",
			"Ortiz",
			"Jenkins",
			"Gutierrez",
			"Perry",
			"Butler",
			"Barnes",
			"Fisher"
	};

	/**
	 * A list of acceptable GPA values.
	 */
	public static final String[] GPA_VALUES = new String[]{
			"0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9",
			"1.0", "1.1", "1.2", "1.3", "1.4", "1.5", "1.6", "1.7", "1.8", "1.9",
			"2.0", "2.1", "2.2", "2.3", "2.4", "2.5", "2.6", "2.7", "2.8", "2.9",
			"3.0", "3.1", "3.2", "3.3", "3.4", "3.5", "3.6", "3.7", "3.8", "3.9",
			"4.0"
	};

	/**
	 * A list of all 50 states abbreviated.
	 */
	public static final String[] STATES = new String[]{
			"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
			"HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
			"MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
			"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
			"SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"
	};

	/**
	 * A list of all the towns in Suffolk County.
	 *
	 * @see <a href="https://geographic.org/streetview/usa/ny/suffolk/index.html">
	 *     https://geographic.org/streetview/usa/ny/suffolk/index.html</a>
	 */
	@SuppressWarnings("SpellCheckingInspection")
	public static final String[] TOWNS = new String[]{
			"Amagansett",
			"Amityville",
			"Aquebogue",
			"Babylon",
			"Bay Shore",
			"Bayport",
			"Bellport",
			"Blue Point",
			"Bohemia",
			"Brentwood",
			"Bridgehampton",
			"Brightwaters",
			"Brookhaven",
			"Calverton",
			"Center Moriches",
			"Centereach",
			"Centerport",
			"Central Islip",
			"Cold Spring Harbor",
			"Commack",
			"Copiague",
			"Coram",
			"Cutchogue",
			"Deer Park",
			"East Hampton",
			"East Islip",
			"East Marion",
			"East Moriches",
			"East Northport",
			"East Quogue",
			"East Setauket",
			"Eastport",
			"Farmingville",
			"Fishers Island",
			"Great River",
			"Greenlawn",
			"Greenport",
			"Hampton Bays",
			"Hauppauge",
			"Holbrook",
			"Holtsville",
			"Huntington",
			"Huntington Station",
			"Islandia",
			"Islip",
			"Islip Terrace",
			"Jamesport",
			"Kings Park",
			"Lake Grove",
			"Laurel",
			"Lindenhurst",
			"Manorville",
			"Mastic",
			"Mastic Beach",
			"Mattituck",
			"Medford",
			"Melville",
			"Middle Island",
			"Miller Place",
			"Montauk",
			"Moriches",
			"Mount Sinai",
			"Nesconset",
			"New Suffolk",
			"North Babylon",
			"Northport",
			"Oakdale",
			"Ocean Beach",
			"Orient",
			"Patchogue",
			"Peconic",
			"Port Jefferson",
			"Port Jefferson Station",
			"Quogue",
			"Remsenburg",
			"Ridge",
			"Riverhead",
			"Rocky Point",
			"Ronkonkoma",
			"Sag Harbor",
			"Sagaponack",
			"Saint James",
			"Sayville",
			"Selden",
			"Shelter Island",
			"Shelter Island Heights",
			"Shirley",
			"Shoreham",
			"Smithtown",
			"Sound Beach",
			"South Jamesport",
			"Southampton",
			"Southold",
			"Speonk",
			"Stony Brook",
			"Upton",
			"Wading River",
			"Wainscott",
			"Water Mill",
			"West Babylon",
			"West Islip",
			"West Sayville",
			"Westhampton",
			"Westhampton Beach",
			"Wyandanch",
			"Yaphank"
	};

	/**
	 * A list of the most common street names in the United States.
	 *
	 * @see <a href="https://www.nlc.org/resource/most-common-u-s-street-names/">
	 *     https://www.nlc.org/resource/most-common-u-s-street-names/</a>
	 */
	public static final String[] STREET_NAMES = new String[]{
			"Second",
			"Third",
			"First",
			"Fourth",
			"Park",
			"Fifth",
			"Main",
			"Sixth",
			"Oak",
			"Seventh",
			"Pine",
			"Maple",
			"Cedar",
			"Eighth",
			"Elm",
			"View",
			"Washington",
			"Ninth",
			"Lake",
			"Hill"
	};

	/**
	 * Used by {@link jadvise.objects.Student} to generate a random phone number.
	 *
	 * @param random A {@link Random} object.
	 * @return A randomly generated phone number.
	 */
	public static String generateRandomPhoneNumber(Random random) {
		return String.format(
				"(%03d) %03d-%04d",
				random.nextInt(999),
				random.nextInt(999),
				random.nextInt(9999)
		);
	}

	/**
	 * Used by {@link jadvise.objects.Student} to generate a random list of courses.
	 *
	 * @param random A {@link Random} object.
	 * @return A randomly generated list of courses.
	 */
	public static String generateRandomCourseList(Random random) {
		return String.join(",",
				String.format("CST%03d", random.nextInt(999)),
				String.format("CST%03d", random.nextInt(999)),
				String.format("CST%03d", random.nextInt(999))
		);
	}
}
