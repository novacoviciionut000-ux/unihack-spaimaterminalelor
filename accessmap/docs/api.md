//Structura JSON pentru Geocoding
[
  {
    "name": "Piața Unirii, Timișoara",
    "lat": 45.7579,
    "lng": 21.2289,
    "type": "square"
  },
  {
    "name": "Piața Unirii (Tram Station)",
    "lat": 45.7585,
    "lng": 21.2295,
    "type": "transit"
  }
]

//STRUCTURA JSON PT ROUTING
//Request
{
  "start": {
    "lat": 45.7550,
    "lng": 21.2250
  },
  "end": {
    "lat": 45.7579,
    "lng": 21.2289
  },
  "profile": "wheelchair" 
}
//Structura pt response
{
  "distanceMeters": 1250,
  "estimatedMinutes": 15,
  "routeGeometry": [
    {"lat": 45.7550, "lng": 21.2250},
    {"lat": 45.7555, "lng": 21.2252},
    {"lat": 45.7579, "lng": 21.2289}
  ],
  "warnings": [
    "Steep incline detected on Strada Vasile Alecsandri."
  ]
}
//HAZARD REPORTS
//REQUESTS
{
  "type": "BLOCKED_SIDEWALK",
  "location": {
    "lat": 45.7562,
    "lng": 21.2275
  },
  "description": "Construction blocking the curb ramp, no bypass available."
}
//RESPONSE
[
  {
    "id": "rep-98765",
    "type": "BLOCKED_SIDEWALK",
    "location": {"lat": 45.7562, "lng": 21.2275},
    "description": "Construction blocking the curb ramp...",
    "reportedAt": "2026-11-13T15:45:00Z",
    "status": "ACTIVE"
  },
  {
    "id": "rep-12345",
    "type": "BROKEN_ELEVATOR",
    "location": {"lat": 45.7590, "lng": 21.2300},
    "description": "Underground passage elevator out of order.",
    "reportedAt": "2026-11-12T09:10:00Z",
    "status": "ACTIVE"
  }
]
